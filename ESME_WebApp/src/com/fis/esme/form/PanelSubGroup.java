package com.fis.esme.form;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.fis.esme.admin.SessionData;
import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.classes.PanelTreeProvider;
import com.fis.esme.component.PanelActionProvider;
import com.fis.esme.component.TabChangeProvider;
import com.fis.esme.message.Exception_Exception;
import com.fis.esme.persistence.EsmeCp;
import com.fis.esme.persistence.EsmeFileUpload;
import com.fis.esme.persistence.EsmeLanguage;
import com.fis.esme.persistence.EsmeMessage;
import com.fis.esme.persistence.EsmeMessageContent;
import com.fis.esme.persistence.EsmeServices;
import com.fis.esme.persistence.EsmeShortCode;
import com.fis.esme.persistence.EsmeSmsCommand;
import com.fis.esme.persistence.EsmeSmsMt;
import com.fis.esme.persistence.EsmeSmsRouting;
import com.fis.esme.util.FileDownloadResource;
import com.fis.esme.util.FormUtil;
import com.fis.esme.util.LogUtil;
import com.fis.esme.util.MessageAlerter;
import com.fis.esme.util.SearchObj;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
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

public class PanelSubGroup extends VerticalLayout implements
		Upload.SucceededListener, Upload.FailedListener, Upload.Receiver,
		TabChangeProvider, PanelTreeProvider, PanelActionProvider,
		Button.ClickListener {
	private boolean isLoaded = false;
	private FormSubscriber parent;
	private List<EsmeServices> childNodes = new ArrayList<EsmeServices>();
	private static EsmeServices treeService = null;

	private ComboBox cbbCP = new ComboBox(TM.get("cdr.field_cp.caption"));
	private ComboBox cbbShortCode = new ComboBox(
			TM.get("cdr.field_shortcode.caption"));
	private ComboBox cbbCommand = new ComboBox(
			TM.get("cdr.field_command.caption"));
	private ComboBox cbbMessage = new ComboBox(
			TM.get("cdr.field_message.caption"));
	private ComboBox cbbLanguage = new ComboBox(
			TM.get("cdr.field_language.caption"));
	private final TextField txtFileName = new TextField(
			TM.get("cdr.field_filename.caption"));

	private BeanItemContainer<EsmeLanguage> languageData = new BeanItemContainer<EsmeLanguage>(
			EsmeLanguage.class);
	private BeanItemContainer<EsmeMessage> messageData = new BeanItemContainer<EsmeMessage>(
			EsmeMessage.class);

	private ArrayList<EsmeSmsMt> arrMt = new ArrayList<EsmeSmsMt>();

	private ArrayList<EsmeCp> listCp = new ArrayList<EsmeCp>();
	private ArrayList<EsmeShortCode> listShortCode = new ArrayList<EsmeShortCode>();
	private ArrayList<EsmeSmsCommand> listCommand = new ArrayList<EsmeSmsCommand>();
	private ArrayList<EsmeSmsRouting> listRouting = new ArrayList<EsmeSmsRouting>();

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
		form.addField("cp", cbbCP);
		form.addField("shortcode", cbbShortCode);
		form.addField("smscommand", cbbCommand);
		form.addField("message", cbbMessage);
		form.addField("language", cbbLanguage);
		form.focus();
	}

	public boolean isValid() {
		boolean valid = true;
		for (final Iterator<?> i = form.getItemPropertyIds().iterator(); i
				.hasNext();) {
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

		if (languageData.size() <= 0) {
			try {
				languageData.addAll(CacheServiceClient.serviceLanguage
						.findAll());
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		if (messageData.size() <= 0) {
			try {
				messageData.addAll(CacheServiceClient.serviceMessage
						.findAllWithoutParameter());
			} catch (Exception_Exception e) {

				e.printStackTrace();
			}
		}

		// try {
		// listCp.addAll(CacheServiceClient.serviceCp.findAllWithoutParameter());
		// } catch (com.fis.esme.cp.Exception_Exception e) {
		//
		// e.printStackTrace();
		// }

		txtFileName.setWidth(TM.get("common.form.field.fixedwidth"));
		txtFileName.setReadOnly(true);
		txtFileName.setNullRepresentation("");

		cbbCP.setWidth(TM.get("common.form.field.fixedwidth"));
		cbbCP.setImmediate(true);
		cbbCP.setNullSelectionAllowed(false);
		cbbCP.setRequired(true);
		cbbCP.setRequiredError(TM.get("common.field.msg.validator_nulloremty",
				cbbCP.getCaption()));
		cbbCP.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		cbbCP.addListener(new Property.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {

				if (cbbCP.getValue() != null && treeService != null) {
					EsmeSmsRouting routing = new EsmeSmsRouting();
					routing.setEsmeCp((EsmeCp) cbbCP.getValue());
					routing.setEsmeServices(treeService);
					listRouting.clear();
					listShortCode.clear();
					cbbShortCode.removeAllItems();
					listCommand.clear();
					cbbCommand.removeAllItems();
					try {
						listRouting.addAll(CacheServiceClient.smsMtService
								.findBySmsRouting(routing));
					} catch (com.fis.esme.smsmt.Exception_Exception e1) {

						e1.printStackTrace();
					}
					if (listRouting.size() > 0) {
						String id = null;
						for (EsmeSmsRouting msv : listRouting) {
							if (id == null) {
								id = String.valueOf(msv.getEsmeShortCode()
										.getShortCodeId());
							} else {
								id += ","
										+ String.valueOf(msv.getEsmeShortCode()
												.getShortCodeId());
							}
						}
						try {
							listShortCode
									.addAll(CacheServiceClient.smsMtService
											.findByShortCode(id));
							for (EsmeShortCode shortcode : listShortCode) {
								cbbShortCode.addItem(shortcode);
							}
						} catch (com.fis.esme.smsmt.Exception_Exception e) {

							e.printStackTrace();
						}
						String idcommand = null;
						for (EsmeSmsRouting msv : listRouting) {
							if (idcommand == null) {
								idcommand = String.valueOf(msv
										.getEsmeSmsCommand().getCommandId());
							} else {
								idcommand += ","
										+ String.valueOf(msv
												.getEsmeSmsCommand()
												.getCommandId());
							}
						}

						try {

							listCommand.addAll(CacheServiceClient.smsMtService
									.findByCommand(idcommand));
							for (EsmeSmsCommand command : listCommand) {
								cbbCommand.addItem(command);
								cbbCommand.setItemCaption(command,
										command.getCode());
							}
						} catch (com.fis.esme.smsmt.Exception_Exception e) {

							e.printStackTrace();
						}
					}

				} else {
					// MessageAlerter.showErrorMessage(getWindow(),
					// "Choose service");
					return;
				}
			}
		});

		cbbShortCode.setWidth(TM.get("common.form.field.fixedwidth"));
		cbbShortCode.setImmediate(true);
		cbbShortCode.setNullSelectionAllowed(false);
		cbbShortCode.setRequired(true);
		cbbShortCode.setRequiredError(TM.get(
				"common.field.msg.validator_nulloremty",
				cbbShortCode.getCaption()));
		cbbShortCode.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		cbbShortCode.addListener(new Property.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {

			}
		});

		cbbCommand.setWidth(TM.get("common.form.field.fixedwidth"));
		cbbCommand.setImmediate(true);
		cbbCommand.setNullSelectionAllowed(false);
		cbbCommand.setRequired(true);
		cbbCommand.setRequiredError(TM.get(
				"common.field.msg.validator_nulloremty",
				cbbCommand.getCaption()));
		cbbCommand.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		cbbCommand.addListener(new Property.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {

			}
		});

		cbbMessage.setWidth(TM.get("common.form.field.fixedwidth"));
		cbbMessage.setImmediate(true);
		cbbMessage.setContainerDataSource(messageData);
		cbbMessage.setNullSelectionAllowed(false);
		cbbMessage.setRequired(true);
		cbbMessage.setRequiredError(TM.get(
				"common.field.msg.validator_nulloremty",
				cbbCommand.getCaption()));
		cbbMessage.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		cbbMessage.addListener(new Property.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {

				if (cbbMessage.getValue() != null
						&& cbbLanguage.getValue() != null) {
					try {
						EsmeMessageContent msgContent = CacheServiceClient.serviceMessageContent
								.findByMessageIdAndLanguageId(
										((EsmeMessage) cbbMessage.getValue())
												.getMessageId(),
										((EsmeLanguage) cbbLanguage.getValue())
												.getLanguageId());

					} catch (com.fis.esme.messagecontent.Exception_Exception e) {

						e.printStackTrace();
					}
				} else {
					// MessageAlerter.showErrorMessage(getWindow(),
					// "Choose message and language");
					return;
				}
			}
		});

		cbbLanguage.setWidth(TM.get("common.form.field.fixedwidth"));
		cbbLanguage.setImmediate(true);
		cbbLanguage.setContainerDataSource(languageData);
		cbbLanguage.setNullSelectionAllowed(false);
		cbbLanguage.setRequired(true);
		cbbLanguage.setRequiredError(TM.get(
				"common.field.msg.validator_nulloremty",
				cbbLanguage.getCaption()));
		cbbLanguage.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		cbbLanguage.addListener(new Property.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {

				if (cbbMessage.getValue() != null
						&& cbbLanguage.getValue() != null) {
					try {
						EsmeMessageContent msgContent = CacheServiceClient.serviceMessageContent
								.findByMessageIdAndLanguageId(
										((EsmeMessage) cbbMessage.getValue())
												.getMessageId(),
										((EsmeLanguage) cbbLanguage.getValue())
												.getLanguageId());
					} catch (com.fis.esme.messagecontent.Exception_Exception e) {

						e.printStackTrace();
					}
				} else {
					// MessageAlerter.showErrorMessage(getWindow(),
					// "Choose message and language");
					return;
				}
			}
		});
	}

	private void initLayout() {
		initData();
		initForm();
		initUpload();
		initRichText();
		setValueRichText("File format simple: MSISDN");
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
		hLayoutC.setHeight("150px");
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

		absolutePath = this.getApplication().getContext().getBaseDirectory()
				.getAbsolutePath();
		exportedDir = absolutePath + File.separator + "UploadFile"
				+ File.separator + "SmsMT";
		File exDir = new File(exportedDir);
		if (!exDir.exists()) {
			exDir = new File(absolutePath + File.separator + "UploadFile");
			exDir.mkdir();

			exDir = new File(absolutePath + File.separator + "UploadFile"
					+ File.separator + "SmsMT");
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
			MessageAlerter.showErrorMessageI18n(getWindow(),
					TM.get("common.msg.uploadfile.filetypeinvalid"));
		} else
			MessageAlerter
					.showErrorMessageI18n(
							getWindow(),
							TM.get("importpb.msg.uploadfile.fail",
									event.getFilename()));
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

		MessageAlerter.showMessageI18n(getWindow(),
				TM.get("importpb.msg.uploadfile.success", event.getFilename()));
		clearRecordCount();
		btnImport.setEnabled(true);
		btnCancel.setEnabled(true);
	}

	@Override
	public String getPermission() {
		return SessionData.getAppClient().getPermission(
				this.getClass().getName());
	}

	@Override
	public void filterTree(Object obj) {

	}

	@Override
	public void treeValueChanged(Object obj) {
		childNodes.clear();
		if (obj instanceof EsmeServices && !parent.isTreeNodeRoot(obj)) {

			Object smscDetailNode = parent.getParentTreeNode(obj);
			Collection<?> collection = parent
					.getChildrenTreeNode(smscDetailNode);
			if (collection != null) {
				childNodes
						.addAll((Collection<? extends EsmeServices>) collection);
			}
		}
		loadDataFromDatabase(obj);
	}

	private void loadDataFromDatabase(Object obj) {

		try {
			if (obj != null && (obj instanceof EsmeServices)
					&& !parent.isTreeNodeRoot(obj)) {
				treeService = (EsmeServices) obj;
				EsmeSmsRouting routing = new EsmeSmsRouting();
				routing.setEsmeServices(treeService);
				listRouting.clear();
				listCp.clear();
				cbbCP.removeAllItems();
				cbbShortCode.removeAllItems();
				cbbCommand.removeAllItems();
				try {
					listRouting.addAll(CacheServiceClient.smsMtService
							.findBySmsRouting(routing));
				} catch (com.fis.esme.smsmt.Exception_Exception e1) {

					e1.printStackTrace();
				}
				if (listRouting.size() > 0) {
					String id = null;
					for (EsmeSmsRouting msv : listRouting) {
						if (id == null) {
							id = String.valueOf(msv.getEsmeCp().getCpId());
						} else {
							id += ","
									+ String.valueOf(msv.getEsmeCp().getCpId());
						}
					}

					listCp.addAll(CacheServiceClient.smsMtService.findByCP(id));
					for (EsmeCp cp : listCp) {
						cbbCP.addItem(cp);
					}
				}

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
			MessageAlerter.showErrorMessageI18n(getWindow(),
					TM.get("form.uploadfile.null"));
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
		BufferedReader reader = null;
		File fileReport = null;
		ArrayList<String> list = new ArrayList<String>();
		int totalSize = 0;
		try {

			fileReport = new File(exportedDir + File.separator + "FileUpload"
					+ strFileName);
			if (fileReport.exists()) {
				fileReport.delete();
				fileReport = new File(exportedDir + File.separator
						+ "FileUpload" + strFileName);
			}
			outputStream = new RandomAccessFile(fileReport, "rw");

			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF8"));
			String strIsdn = "";
			richText.setValue("");

			long begin = System.currentTimeMillis();
			setValueRichText("File name: " + strFileName);
			setValueRichText("Begin: " + getCurrentDate());

			while ((strIsdn = reader.readLine()) != null) {
				if (strIsdn.trim().length() > 0) {
					if (FormUtil.msisdnValidateUpload(FormUtil.cutMSISDN(strIsdn
							.trim()))) {
						list.add(FormUtil.cutMSISDN(strIsdn.trim()));
						
						Output output = new Output();
						output.setDate(getCurrentDate());
						output.setIsdn(strIsdn.trim());
						output.setStatus("1");
						cacheOutputRG.add(output);

					} else {

						if (!"".equals(strIsdn)) {
							Output output = new Output();
							output.setDate(getCurrentDate());
							output.setIsdn(strIsdn.trim());
							output.setStatus("0");
							cacheOutputRG.add(output);
						}
					}

					// totalRecord++;
					totalSize++;
				}
			}
			if (totalSize > 100000) {

				return;
			}

			Date ssCurrent = Calendar.getInstance().getTime();
			if (list.size() > 0) {
				
				if (cacheOutputRG.size() > 0) {
					writeFile();
//					cacheOutputRG.clear();
				}
				// insert dữ liệu
				EsmeFileUpload upload = new EsmeFileUpload();
				upload.setFileUploadId(idFileUploadOld);
				upload.setCreateDate(Calendar.getInstance().getTime());
				upload.setEsmeServices(treeService);
				upload.setStatus("1");
				upload.setFileName(strFileName);
				upload.setUrl(localFilePat);
				EsmeCp cpId = (EsmeCp) cbbCP.getValue();
				EsmeSmsCommand smsCommand = (EsmeSmsCommand) cbbCommand
						.getValue();
				EsmeShortCode shortCode = (EsmeShortCode) cbbShortCode
						.getValue();
				EsmeMessageContent msgContent = null;
				try {
					msgContent = CacheServiceClient.serviceMessageContent
							.findByMessageIdAndLanguageId(
									((EsmeMessage) cbbMessage.getValue())
											.getMessageId(),
									((EsmeLanguage) cbbLanguage.getValue())
											.getLanguageId());
				} catch (Exception e1) {

					e1.printStackTrace();
				}

				try {
					long idFileUpload = CacheServiceClient.fileUploadService
							.add(upload);
					if (idFileUpload > 0) {
						idFileUploadOld = idFileUpload;
						 EsmeSmsMt smsMt = new EsmeSmsMt();
						 smsMt.setFileUploadId(idFileUpload);
						 smsMt.setCpId(cpId.getCpId());
						 smsMt.setCommandCode(smsCommand.getCode());
						 smsMt.setShortCode(shortCode.getCode());
						 if (msgContent != null) {
						 smsMt.setMessage(msgContent.getMessage());
						 }
						 smsMt.setRequestTime(ssCurrent);
						 smsMt.setStatus("0");
						 smsMt.setRetryNumber(0);
						 smsMt.setReloadNumber(0);
						 smsMt.setRegisterDeliveryReport("0");
						 
						 upload.setFileUploadId(idFileUpload);
						 t = new UploadfileThread(list, smsMt, upload);
						 t.start();
						 
//						arrMt.clear();
//						for (String string : list) {
//
//							EsmeSmsMt smsMt = new EsmeSmsMt();
//							smsMt.setFileUploadId(idFileUpload);
//							smsMt.setCpId(cpId.getCpId());
//							smsMt.setCommandCode(smsCommand.getCode());
//							smsMt.setShortCode(shortCode.getCode());
//							if (msgContent != null) {
//								smsMt.setMessage(msgContent.getMessage());
//							}
//							smsMt.setRequestTime(ssCurrent);
//							smsMt.setStatus("0");
//							smsMt.setRetryNumber(0);
//							smsMt.setReloadNumber(0);
//							smsMt.setRegisterDeliveryReport("0");
//							smsMt.setMsisdn(string.trim());
//
//							Output output = new Output();
//							output.setDate(getCurrentDate());
//							output.setIsdn(string.trim());
//							output.setStatus("1");
//							cacheOutputRG.add(output);
//
//							arrMt.add(smsMt);
//
//						}
//
//						try {
//
//							CacheServiceClient.smsMtService.addMultiProcess(arrMt);
//
//						} catch (Exception e) {
//
//							e.printStackTrace();
//						}
//
					} else {
						MessageAlerter.showErrorMessage(getWindow(),
								TM.get("common.msg.add.fail", "file upload"));
					}
				} catch (Exception e) {

					e.printStackTrace();
				}
			}else{
				cacheOutputRG.clear();
				MessageAlerter.showErrorMessage(getWindow(),
						TM.get("form.uploadfile.null.format.error"));
			}
			setValueRichText("End: " + getCurrentDate());
			long end = System.currentTimeMillis() - begin;
			System.out.println("Total time : " + end);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
//			try {
//				if (cacheOutputRG.size() > 0) {
//					writeFile();
////					cacheOutputRG.clear();
//				}
//				if (reader != null) {
//					reader.close();
//				}
//				outputStream.close();
//				if (totalRecord > 0) {
////					MessageAlerter.showMessage(getWindow(),
////							TM.get("formupload.insert.success"));
//
//					if (idFileUploadOld > 0) {
//						EsmeFileUpload upload = new EsmeFileUpload();
//						upload.setFileUploadId(idFileUploadOld);
//						upload.setTotalRecord((long) totalRecord);
//						upload.setTotalSucess((long) totalRecordSuccess);
//						upload.setTotalFail((long) totalRecordFail);
//						upload.setCreateDate(Calendar.getInstance().getTime());
//						upload.setEsmeServices(treeService);
//						upload.setStatus("1");
//						upload.setFileName(strFileName);
//						upload.setUrl(localFilePat);
//
//						try {
//							CacheServiceClient.fileUploadService.update(upload);
//						} catch (Exception e) {
//
//							e.printStackTrace();
//						}
//					}
////					returnFileDownloadResource(fileReport);
//
//				} else {
					if (totalSize > 100000) {

						setValueRichText(TM.get("total.error",
								TM.get("totalRG.size.max")));
					} else {

//						setValueRichText(TM.get("total.error",
//								TM.get("main.upload.empty")));
					}
//				}
				
				btnImport.setEnabled(false);
				cbbCP.select(null);
				cbbShortCode.select(null);
				cbbCommand.select(null);
				cbbLanguage.select(null);
				cbbMessage.select(null);
				txtFileName.setReadOnly(false);
				txtFileName.setValue("");
				txtFileName.setReadOnly(true);
				form.setValidationVisible(false);

//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
	}

	public void setValueRichText(String message) {

		builder.append(message + "<br/>");
		richText.setValue(builder.toString());

	}

	private void returnFileDownloadResource(File fileResource) {
		System.out.println("fileResource = " + fileResource);
		FileDownloadResource fileDownloadResource = new FileDownloadResource(
				fileResource, getApplication());
		System.out.println("fileDownloadResource = "
				+ fileDownloadResource.getFilename());
		getApplication().getMainWindow().open(fileDownloadResource);

	}

	private String getCurrentDate() {
		String dtCurrent = FormUtil.simpleDateFormat.format(Calendar
				.getInstance().getTime());
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

			String str = output.getIsdn() + "\t" + output.getDate() + "\t\t"
					+ output.getStatus() + "\r\n";
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
		MessageAlerter.showMessage(getWindow(),
				TM.get("formupload.insert.success"));
	}

	@Override
	public void buttonClick(ClickEvent event) {

		Button source = event.getButton();
		if (source == btnImport) {

			if (isValid()) {
				accept();
			}

		} else if (source == btnCancel) {

			 t.stop();
			try {
				
				CacheServiceClient.smsMtService.stopUpload();
//				if (idFileUploadOld > 0) {
////					System.out.println("file upload id????" + idFileUploadOld);
//					CacheServiceClient.smsMtService
//							.deleteByFileUploadId(idFileUploadOld);
//				}

			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

}
