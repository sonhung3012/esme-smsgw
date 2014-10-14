package com.fis.esme.form;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.record.PageBreakRecord.Break;

import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.persistence.EsmeFileUpload;
import com.fis.esme.persistence.EsmeSmsMt;
import com.fis.esme.util.FormUtil;
import com.fis.esme.util.MessageAlerter;

import eu.livotov.tpt.i18n.TM;

public class UploadfileThread extends Thread implements Runnable {

	private ArrayList<String> list;
	private boolean running = true;
	private EsmeSmsMt smsMt;
	private EsmeFileUpload fileUpload;
	private ArrayList<EsmeSmsMt> arrMt = new ArrayList<EsmeSmsMt>();

	public UploadfileThread(ArrayList<String> list, EsmeSmsMt smsMt ,EsmeFileUpload fileUpload) {

		this.list = list;
		this.smsMt = smsMt;
		this.fileUpload = fileUpload;
	}

	public UploadfileThread() {

	}

	private String getCurrentDate() {
		String dtCurrent = FormUtil.simpleDateFormat.format(Calendar
				.getInstance().getTime());
		return dtCurrent;
	}

	@Override
	public void run() {

		try {
			// while (isRunning()) {
			// for (int i = 0; i < 1000; i++) {
			// if(!isRunning())
			// break;
			// Thread.sleep(1000);
			// System.out.println("Lần >>>>"+i);
			//
			// }
			arrMt.clear();
			for (String strMsisdn : list) {
				if (!isRunning()) {
//					arrMt.clear();
					break;
				}
				if (strMsisdn == null || "".equals(strMsisdn)) {
					break;
				}
//				smsMt.setMsisdn(strMsisdn);
				
				EsmeSmsMt smsMt1 = new EsmeSmsMt();
				 smsMt1.setFileUploadId(smsMt.getFileUploadId());
				 smsMt1.setCpId(smsMt.getCpId());
				 smsMt1.setCommandCode(smsMt.getCommandCode());
				 smsMt1.setShortCode(smsMt.getShortCode());
				 smsMt1.setMessage(smsMt.getMessage());
				 smsMt1.setRequestTime(Calendar.getInstance().getTime());
				 smsMt1.setStatus("0");
				 smsMt1.setRetryNumber(0);
				 smsMt1.setReloadNumber(0);
				 smsMt1.setRegisterDeliveryReport("0");
				 smsMt1.setMsisdn(strMsisdn);
				
				arrMt.add(smsMt1);
				
//				long idSmsMt = CacheServiceClient.smsMtService.add(smsMt);
//				if (idSmsMt != 0) {
//					Output output = new Output();
//					output.setDate(getCurrentDate());
//					output.setIsdn(strMsisdn);
//					output.setStatus("1");
//					PanelMT.cacheOutputRG.add(output);
//				} else {
//					Output output = new Output();
//					output.setDate(getCurrentDate());
//					output.setIsdn(strMsisdn);
//					output.setStatus("0");
//					PanelMT.cacheOutputRG.add(output);
//				}
			}
			long totalSuccess = CacheServiceClient.smsMtService.addMultiProcess(arrMt);
			long total = PanelMT.cacheOutputRG.size();
//			System.out.println("Tổng số bản ghi>>>>>"+total);
//			System.out.println("Tổng số insert thành công>>>???"+totalSuccess);
			if (totalSuccess > 0) {
				fileUpload.setTotalRecord(total);
				fileUpload.setTotalSucess(totalSuccess);
				fileUpload.setTotalFail(total-totalSuccess);
				fileUpload.setCreateDate(Calendar.getInstance().getTime());
				
				CacheServiceClient.fileUploadService.update(fileUpload);
				PanelMT.cacheOutputRG.clear();
//				FormFileUploadDetail  detail = new FormFileUploadDetail();
//				PanelMT mtup = new PanelMT(detail);
//				mtup.setValueRichText("");
//				mtup.setValueRichText("Upload successful!");
				
				return;
			}else{
				PanelMT.cacheOutputRG.clear();
				return;
			}

			// }

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void close() {

		this.close();
	}

	public void stopThread() {
		running = false;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

}
