package com.fis.esme.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.fis.esme.config.Config;
import com.fis.esme.config.ConfigConstant;
import com.fis.esme.fileupload.FileUploadTransferer;
import com.fis.esme.fileupload.FileUploadTransfererService;

public class FileUploadTransfererClient {

	private static final QName SERVICE_NAME = new QName(
			"http://fileupload.esme.fis.com/", "FileUploadTransfererService");

	private static final String WSDL_URL = Config
			.getURL(ConfigConstant.FILE_UPLOAD_PORT_KEY);

	private FileUploadTransfererClient() {

	}

	public static FileUploadTransferer getService() throws Exception {
		URL url = new URL(WSDL_URL);
		FileUploadTransfererService ss = new FileUploadTransfererService(url,
				SERVICE_NAME);
		FileUploadTransferer port = ss.getFileUploadTransfererPort();
		return port;
	}

}
