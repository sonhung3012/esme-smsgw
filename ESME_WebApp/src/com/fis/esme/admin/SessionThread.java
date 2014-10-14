package com.fis.esme.admin;

import java.io.Serializable;

import com.fss.ddtp.DDTP;

public class SessionThread implements Runnable, Serializable {

	private boolean flag = true;
	private Thread thread;
	private AppClient appClient;

	public SessionThread(AppClient appClient) {
		thread = new Thread(this);
		this.appClient = appClient;
	}

	@Override
	public void run() {
		while (flag) {

			try {
				DDTP request = new DDTP();
				appClient.sendRequest("UserBean", "acceptRequestClient",
						request);
//				System.out.println(appClient.getSessionKey()
//						+ "Send request to Server...");
				Thread.sleep(30000);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Thread Exception...");
				stopThread();
			}
		}
	}

	public void stopThread() {
		flag = false;
		thread = null;
	}

	public void startThread() {
		if (thread == null) {
			thread = new Thread(this);
		}
		thread.start();
	}
}
