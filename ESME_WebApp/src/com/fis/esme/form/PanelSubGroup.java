package com.fis.esme.form;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fis.esme.admin.SessionData;
import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.classes.PanelTreeProvider;
import com.fis.esme.component.PanelActionProvider;
import com.fis.esme.component.TabChangeProvider;
import com.fis.esme.persistence.EsmeServices;
import com.fis.esme.persistence.Groups;
import com.fis.esme.persistence.Subscriber;
import com.fis.esme.subscriberdt.Exception_Exception;
import com.fis.esme.util.FileDownloadResource;
import com.fis.esme.util.FormUtil;
import com.fis.esme.util.LogUtil;
import com.fis.esme.util.MessageAlerter;
import com.fis.esme.util.SearchObj;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.VerticalLayout;

import eu.livotov.tpt.i18n.TM;

public class PanelSubGroup extends VerticalLayout implements Upload.SucceededListener, Upload.FailedListener, Upload.Receiver, TabChangeProvider, PanelTreeProvider, PanelActionProvider,
        Button.ClickListener {

	private boolean isLoaded = false;
	private FormSubscriber parent;
	private PanelSubscriber subscriber = new PanelSubscriber(parent);

	private List<Groups> childNodes = new ArrayList<Groups>();
	private static Groups treeService = null;

	private final TextField txtFileName = new TextField(TM.get("cdr.field_filename.caption"));

	private HorizontalLayout hLayoutA;
	private HorizontalLayout hLayoutC;
	private Upload upload;
	private RichTextArea richText;
	private Button btnImport;
	private String absolutePath;
	private String exportedDir;
	private File file;

	private int totalRecord = 0;
	private int totalRecordSuccess = 0;
	private int totalRecordFail = 0;
	private int totalRecordExisted = 0;
	private int totalRecordInvalid = 0;
	private StringBuilder builder = new StringBuilder();
	private Form form;
	private String strFileName = "";
	private RandomAccessFile outputStream;
	public static ArrayList<Output> cacheOutputRG = new ArrayList<Output>();
	private long idFileUploadOld = 0;
	private String localFilePat = null;

	private Button btnCancel;
	private UploadfileThread t;

	public PanelSubGroup(String title, FormSubscriber parent) {

		this.parent = parent;
		this.setCaption(title);
		this.setSizeFull();
		// initLayout();
	}

	public PanelSubGroup(FormSubscriber smscDetail) {

		this(TM.get(PanelSubGroup.class.getName()), smscDetail);
		LogUtil.logAccess(PanelSubGroup.class.getName());
	}

	private void initForm() {

		form = new Form();
		form.setImmediate(true);
		form.setValidationVisible(false);
		form.addField("filename", txtFileName);
		form.focus();
	}

	public boolean isValid() {

		boolean valid = true;
		for (final Iterator<?> i = form.getItemPropertyIds().iterator(); i.hasNext();) {
			Field field = form.getField(i.next());

			if (field instanceof Table) {
				return true;
			}

			if (!field.isValid()) {
				field.focus();
				if (field instanceof AbstractTextField) {
					((AbstractTextField) field).selectAll();
				}
				form.setValidationVisible(true);
				return false;
			}
		}
		return valid;
	}

	private void initUpload() {

		upload = new Upload("", this);
		// upload.setDescription("Import CDR");
		upload.setImmediate(true);
		upload.setButtonCaption(TM.get("importpb.btn.upload.caption"));
		upload.addListener((Upload.SucceededListener) this);
		upload.addListener((Upload.FailedListener) this);
		upload.setStyleName("btnImportPromTemp");
	}

	private void initRichText() {

		richText = new RichTextArea();
		richText.setSizeFull();
		richText.setImmediate(true);
	}

	private void initData() {

		txtFileName.setWidth(TM.get("common.form.field.fixedwidth"));
		txtFileName.setReadOnly(true);
		txtFileName.setNullRepresentation("");

	}

	private void initLayout() {

		initData();
		initForm();
		initUpload();
		initRichText();
		setValueRichText(TM.get("cdr.textarea.init.value"));
		setValueRichText(TM.get("subs.upload.file.record.format"));
		btnImport = new Button(TM.get("cdr.button_import_file.caption"));
		btnImport.setEnabled(false);
		btnImport.setImmediate(true);
		btnImport.addStyleName("btnImportPromTemp");

		btnCancel = new Button(TM.get("cdr.button_cancelupload_file.caption"));
		btnCancel.setEnabled(false);
		btnCancel.setImmediate(true);
		btnCancel.addStyleName("btnImportPromTemp");

		btnImport.addListener(this);
		btnCancel.addListener(this);

		hLayoutA = new HorizontalLayout();
		hLayoutA.setSizeFull();
		hLayoutA.setSpacing(true);

		hLayoutC = new HorizontalLayout();
		hLayoutC.setSizeFull();
		hLayoutC.setSpacing(true);

		Panel formPl = new Panel();
		formPl.setSizeFull();

		VerticalLayout verMain = new VerticalLayout();

		HorizontalLayout hobtn = new HorizontalLayout();
		hobtn.addComponent(upload);
		hobtn.addComponent(btnImport);
		hobtn.addComponent(btnCancel);
		hobtn.setComponentAlignment(upload, Alignment.BOTTOM_CENTER);
		hobtn.setComponentAlignment(btnImport, Alignment.BOTTOM_CENTER);
		hobtn.setComponentAlignment(btnCancel, Alignment.BOTTOM_CENTER);

		HorizontalLayout hofrm = new HorizontalLayout();
		hofrm.addComponent(form);
		hofrm.setComponentAlignment(form, Alignment.BOTTOM_CENTER);

		verMain.addComponent(hobtn);
		verMain.addComponent(hofrm);
		// verMain.addComponent(hobtn);
		verMain.setComponentAlignment(hobtn, Alignment.BOTTOM_CENTER);
		verMain.setComponentAlignment(hofrm, Alignment.BOTTOM_CENTER);
		// verMain.setComponentAlignment(hobtn, Alignment.BOTTOM_CENTER);
		formPl.setContent(verMain);

		hLayoutA.addComponent(formPl);
		hLayoutA.setComponentAlignment(formPl, Alignment.MIDDLE_CENTER);
		hLayoutA.setExpandRatio(formPl, 1.0f);
		hLayoutC.addComponent(richText);

		hLayoutC.setSizeFull();
		hLayoutC.setHeight("300px");
		this.addComponent(hLayoutA);
		this.addComponent(hLayoutC);
		this.setExpandRatio(hLayoutA, 1.0f);
		// this.setExpandRatio(hLayoutC, 0.2f);
		this.setSizeFull();
		this.setMargin(false);
		this.setSpacing(true);
	}

	private void clearRecordCount() {

		totalRecord = 0;
		totalRecordSuccess = 0;
		totalRecordFail = 0;
		totalRecordExisted = 0;
		totalRecordInvalid = 0;
	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {

		if (!filename.endsWith(".txt")) {
			upload.interruptUpload();
		}
		strFileName = "";

		FileOutputStream fos = null;

		absolutePath = this.getApplication().getContext().getBaseDirectory().getAbsolutePath();
		exportedDir = absolutePath + File.separator + "UploadFile" + File.separator + "SmsMT";
		File exDir = new File(exportedDir);
		if (!exDir.exists()) {
			exDir = new File(absolutePath + File.separator + "UploadFile");
			exDir.mkdir();

			exDir = new File(absolutePath + File.separator + "UploadFile" + File.separator + "SmsMT");
			if (!exDir.exists())
				exDir.mkdir();
		}
		String filePath = exportedDir + File.separator + filename;
		localFilePat = filePath;
		System.out.println("filePath=" + filePath);

		file = new File(filePath);
		try {
			fos = new FileOutputStream(file);
		} catch (final java.io.FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return fos;
	}

	@Override
	public void uploadFailed(FailedEvent event) {

		if (!event.getFilename().endsWith(".txt")) {
			MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("common.msg.uploadfile.filetypeinvalid"));
		} else
			MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("importpb.msg.uploadfile.fail", event.getFilename()));
	}

	@Override
	public void uploadSucceeded(SucceededEvent event) {

		System.out.println("event.getFilename()=" + event.getFilename());
		strFileName = event.getFilename();
		System.out.println("strFileName=" + strFileName);
		if (strFileName != null) {
			txtFileName.setReadOnly(false);
			txtFileName.setValue(strFileName);
			txtFileName.setReadOnly(true);
		} else {
			txtFileName.setReadOnly(false);
			txtFileName.setValue("");
			txtFileName.setReadOnly(true);
		}

		MessageAlerter.showMessageI18n(getWindow(), TM.get("importpb.msg.uploadfile.success", event.getFilename()));
		clearRecordCount();
		btnImport.setEnabled(true);
		btnCancel.setEnabled(true);
	}

	@Override
	public String getPermission() {

		return SessionData.getAppClient().getPermission(this.getClass().getName());
	}

	@Override
	public void filterTree(Object obj) {

	}

	@Override
	public void treeValueChanged(Object obj) {

		childNodes.clear();
		if (obj instanceof EsmeServices && !parent.isTreeNodeRoot(obj)) {

			Object smscDetailNode = parent.getParentTreeNode(obj);
			Collection<?> collection = parent.getChildrenTreeNode(smscDetailNode);
			if (collection != null) {
				childNodes.addAll((Collection<? extends Groups>) collection);
			}
		}
		loadDataFromDatabase(obj);
	}

	private void loadDataFromDatabase(Object obj) {

		try {
			if (obj != null && (obj instanceof EsmeServices) && !parent.isTreeNodeRoot(obj)) {
				treeService = (Groups) obj;
				// EsmeSmsRouting routing = new EsmeSmsRouting();
				// routing.setEsmeServices(treeService);

			} else if (parent.isTreeNodeRoot(obj)) {
				// data.removeAllItems();
				// data.addAll(smscParamService.findAllWithoutParameter());
			}
		} catch (Exception e) {
			FormUtil.showException(getWindow(), e);
			e.printStackTrace();
		}
	}

	@Override
	public void loadButtonPanel() {

	}

	@Override
	public void loadForm() {

		if (!isLoaded) {
			initLayout();
			isLoaded = true;
		}
	}

	@Override
	public void delete(Object object) {

	}

	@Override
	public void searchOrAddNew(String key) {

	}

	@Override
	public void search() {

	}

	@Override
	public void fieldSearch(SearchObj searchObj) {

	}

	@Override
	public void export() {

	}

	@Override
	public void accept() {

		if (file == null) {

			MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("form.uploadfile.null"));

		} else if (parent.isTreeNodeRoot(parent.getCurrentTreeNode())) {

			MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("subs.uploadfile.groups.error"));

		} else {

			int len = builder.length();
			if (len > 0) {
				builder.delete(0, len);
			}
			insertDataFromFile();
		}
	}

	// private int getSize(ArrayList<String> list)
	// {
	// int size = list.size();
	// float x = (float) size / 10;
	// double d = Math.ceil(x);
	// String str = String.valueOf(d);
	// int index = str.indexOf(".");
	// size = Integer.parseInt(str.substring(0, index));
	// System.out.println("size = " + size);
	// return size;
	//
	// }

	private void insertDataFromFile() {

		cacheOutputRG.clear();
		BufferedReader reader = null;
		File fileReport = null;
		File fileError = null;
		ArrayList<String> list = new ArrayList<String>();
		int totalSize = 0;
		try {

			fileReport = new File(exportedDir + File.separator + "FileUpload" + strFileName);
			if (fileReport.exists()) {

				fileReport.delete();
				fileReport = new File(exportedDir + File.separator + "FileUpload" + strFileName);

			}

			fileError = new File(exportedDir + File.separator + strFileName.substring(0, strFileName.lastIndexOf(".")) + "_ERROR" + ".txt");
			if (fileError.exists()) {

				fileError.delete();
				fileError = new File(exportedDir + File.separator + strFileName.substring(0, strFileName.lastIndexOf(".")) + "_ERROR" + ".txt");
			}

			outputStream = new RandomAccessFile(fileReport, "rw");
			RandomAccessFile outputStreamError = new RandomAccessFile(fileError, "rw");

			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
			String strSubs = "";
			String strIsdn = "";
			String strEmail = "";
			String strAddress = "";
			String strBirthday = "";
			String strSex = "";
			richText.setValue("");

			long begin = System.currentTimeMillis();
			setValueRichText(TM.get("subs.upload.file.name") + ": " + strFileName);

			while ((strSubs = reader.readLine()) != null) {

				if (strSubs.split(",").length == 5) {

					strIsdn = strSubs.split(",")[0].trim();
					strEmail = strSubs.split(",")[1].trim();
					strAddress = strSubs.split(",")[2].trim();
					strBirthday = strSubs.split(",")[3].trim();
					strSex = strSubs.split(",")[4].trim();

				} else if (strSubs.split("\\|").length == 5) {

					strIsdn = strSubs.split("\\|")[0].trim();
					strEmail = strSubs.split("\\|")[1].trim();
					strAddress = strSubs.split("\\|")[2].trim();
					strBirthday = strSubs.split("\\|")[3].trim();
					strSex = strSubs.split("\\|")[4].trim();

				} else if (strSubs.split("\t").length == 5) {

					strIsdn = strSubs.split("\t")[0].trim();
					strEmail = strSubs.split("\t")[1].trim();
					strAddress = strSubs.split("\t")[2].trim();
					strBirthday = strSubs.split("\t")[3].trim();
					strSex = strSubs.split("\t")[4].trim();

				} else {

					strIsdn = strSubs.trim();
				}

				boolean isIsdnExisted = false;
				List<Subscriber> listSubs = parent.getPnSmscParam().getListSubscriber();
				for (Subscriber sub : listSubs) {

					if (sub.getMsisdn().equals(strIsdn.trim())) {

						isIsdnExisted = true;
						break;
					}
				}

				if (strIsdn.trim().length() > 0) {

					if (!isIsdnExisted && FormUtil.msisdnValidateUpload(FormUtil.cutMSISDN(strIsdn.trim())) && (strEmail.equals("") || isEmailValid(strEmail))
					        && (strBirthday.equals("") || isDateValid(strBirthday, "dd/MM/yyyy")) && (strSex.equals("") || isSexValid(strSex))) {

						list.add(FormUtil.cutMSISDN(strIsdn.trim()));

						Output output = new Output();
						output.setDate(getCurrentDate());
						output.setIsdn(strIsdn.trim());
						output.setStatus("1");
						cacheOutputRG.add(output);

						Subscriber sub = new Subscriber();
						sub.setMsisdn(strIsdn.trim());
						sub.setCreateDate(new Date());

						try {
							if (!strBirthday.equals("")) {

								sub.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(strBirthday));

							} else {

								sub.setBirthDate(new Date());
							}

						} catch (ParseException e1) {
							e1.printStackTrace();
						}

						sub.setEmail(strEmail);
						sub.setSex(strSex.equalsIgnoreCase("Nữ") ? "0" : "1");
						sub.setStatus("1");
						sub.setAddress(strAddress);

						PanelSubscriber pnSmscParam = parent.getPnSmscParam();
						Groups group = (Groups) parent.getCurrentTreeNode();
						try {

							Long id = pnSmscParam.getSmscParamService().add(sub, group.getGroupId());

							if (id > 0) {
								sub.setSubId(id);
							}
							listSubs.add(sub);
						} catch (Exception_Exception e) {
							e.printStackTrace();
						}

					} else if (isIsdnExisted) {

						Output output = new Output();
						output.setDate(getCurrentDate());
						output.setIsdn(strIsdn.trim());
						output.setStatus("2");
						cacheOutputRG.add(output);

						writeRecordToFileError(strSubs + ", " + TM.get("subs.upload.file.record.existed"), fileError, outputStreamError);

					} else {

						if (!"".equals(strSubs)) {

							Output output = new Output();
							output.setDate(getCurrentDate());
							output.setIsdn(strIsdn.trim());
							output.setStatus("3");
							cacheOutputRG.add(output);

							writeRecordToFileError(strSubs + ", " + TM.get("subs.upload.file.record.invalid"), fileError, outputStreamError);
						}
					}

					// totalRecord++;
					totalSize++;
				}
			}
			if (totalSize > 100000) {

				return;
			}

			// insert dữ liệu
			if (cacheOutputRG.size() > 0) {

				writeFile();
			} else {
				cacheOutputRG.clear();
				MessageAlerter.showErrorMessage(getWindow(), TM.get("form.uploadfile.null.format.error"));
			}

			setValueRichText(TM.get("subs.upload.file.total.record") + ": " + totalRecord);
			setValueRichText(TM.get("subs.upload.file.total.record.success") + ": " + totalRecordSuccess);
			setValueRichText(TM.get("subs.upload.file.total.record.existed") + ": " + totalRecordExisted);
			setValueRichText(TM.get("subs.upload.file.total.record.invalid") + ": " + totalRecordInvalid);

			long end = System.currentTimeMillis() - begin;
			System.out.println("Total time : " + end);

			if (outputStreamError.length() > 0) {

				Desktop.getDesktop().open(fileError);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// try {
			// if (cacheOutputRG.size() > 0) {
			// writeFile();
			// // cacheOutputRG.clear();
			// }
			// if (reader != null) {
			// reader.close();
			// }
			// outputStream.close();
			// if (totalRecord > 0) {
			// // MessageAlerter.showMessage(getWindow(),
			// // TM.get("formupload.insert.success"));
			//
			// if (idFileUploadOld > 0) {
			// EsmeFileUpload upload = new EsmeFileUpload();
			// upload.setFileUploadId(idFileUploadOld);
			// upload.setTotalRecord((long) totalRecord);
			// upload.setTotalSucess((long) totalRecordSuccess);
			// upload.setTotalFail((long) totalRecordFail);
			// upload.setCreateDate(Calendar.getInstance().getTime());
			// upload.setEsmeServices(treeService);
			// upload.setStatus("1");
			// upload.setFileName(strFileName);
			// upload.setUrl(localFilePat);
			//
			// try {
			// CacheServiceClient.fileUploadService.update(upload);
			// } catch (Exception e) {
			//
			// e.printStackTrace();
			// }
			// }
			// // returnFileDownloadResource(fileReport);
			//
			// } else {
			if (totalSize > 100000) {

				setValueRichText(TM.get("total.error", TM.get("totalRG.size.max")));
			} else {

				// setValueRichText(TM.get("total.error",
				// TM.get("main.upload.empty")));
			}
			// }

			btnImport.setEnabled(false);
			txtFileName.setReadOnly(false);
			txtFileName.setValue("");
			txtFileName.setReadOnly(true);
			form.setValidationVisible(false);

			// } catch (IOException e) {
			// e.printStackTrace();
			// }
		}
	}

	public void setValueRichText(String message) {

		builder.append(message + "<br/>");
		richText.setValue(builder.toString());

	}

	private void returnFileDownloadResource(File fileResource) {

		System.out.println("fileResource = " + fileResource);
		FileDownloadResource fileDownloadResource = new FileDownloadResource(fileResource, getApplication());
		System.out.println("fileDownloadResource = " + fileDownloadResource.getFilename());
		getApplication().getMainWindow().open(fileDownloadResource);

	}

	private String getCurrentDate() {

		String dtCurrent = FormUtil.simpleDateFormat.format(Calendar.getInstance().getTime());
		return dtCurrent;
	}

	private void writeFile() {

		for (int i = 0; i < cacheOutputRG.size(); i++) {
			Output output = cacheOutputRG.get(i);
			if ("1".equals(output.getStatus())) {
				totalRecordSuccess++;
				totalRecord++;
			}
			if ("2".equals(output.getStatus())) {
				totalRecordExisted++;
				totalRecord++;
			}
			if ("0".equals(output.getStatus())) {
				totalRecordFail++;
				totalRecord++;
			}
			if ("3".equals(output.getStatus())) {
				totalRecordInvalid++;
				totalRecord++;
			}

			String str = output.getIsdn() + "\t" + output.getDate() + "\t\t" + output.getStatus() + "\r\n";
			try {
				outputStream.seek(outputStream.length());
				outputStream.write(str.getBytes());

			} catch (IOException e) {
				e.printStackTrace();

			}
		}

	}

	@Override
	public void showDialog(Object object) {

	}

	public void showMessage() {

		MessageAlerter.showMessage(getWindow(), TM.get("formupload.insert.success"));
	}

	@Override
	public void buttonClick(ClickEvent event) {

		Button source = event.getButton();
		if (source == btnImport) {

			if (isValid()) {
				accept();
			}

		} else if (source == btnCancel) {

			txtFileName.setReadOnly(false);
			txtFileName.setValue("");
			txtFileName.setReadOnly(true);
			btnImport.setEnabled(false);
			btnCancel.setEnabled(false);

			try {

				CacheServiceClient.smsMtService.stopUpload();
				// if (idFileUploadOld > 0) {
				// // System.out.println("file upload id????" + idFileUploadOld);
				// CacheServiceClient.smsMtService
				// .deleteByFileUploadId(idFileUploadOld);
				// }

			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

	public boolean isDateValid(String dateToValidate, String dateFromat) {

		if (dateToValidate == null) {
			return false;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);

		try {

			// if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
			System.out.println(date);

		} catch (ParseException e) {

			return false;
		}

		return true;
	}

	public boolean isEmailValid(String email) {

		String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(emailPattern);
		Matcher matcher = pattern.matcher(email);

		return matcher.matches();

	}

	public boolean isSexValid(String sex) {

		String sexPattern = "Nam|Nữ";
		Pattern pattern = Pattern.compile(sexPattern, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(sex);

		return matcher.matches();

	}

	public void writeRecordToFileError(String record, File file, RandomAccessFile outputStream) {

		String str = record + "\r\n";

		try {
			outputStream.seek(outputStream.length());
			outputStream.write(str.getBytes());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
