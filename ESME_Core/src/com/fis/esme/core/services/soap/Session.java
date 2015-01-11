package com.fis.esme.core.services.soap;
import java.util.concurrent.ConcurrentHashMap;
import com.fss.queue.Message;


/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * Company: FIS
 * </p>
 * 
 * @author HungVM
 * @version 1.0
 */
public class Session {
	private String user;
	private String password;
	private String ip;
	private int userid;
	private long lastSendRequest = System.currentTimeMillis();
	private long firstSendRequest = System.currentTimeMillis();
	public long getFirstSendRequest() {
		return firstSendRequest;
	}
	
	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public void setFirstSendRequest(long firstSendRequest) {
		this.firstSendRequest = firstSendRequest;
	}

	ConcurrentHashMap<String, Message> queueResponse = new ConcurrentHashMap<String, Message>();

	public Session() {
	}

	public void setClientAddress(String clientAddress) {
		this.ip = clientAddress;
	}

	public String getClientAddress() {
		return this.ip;
	}

	public void setLastSendRequest(long lastSent) {
		this.lastSendRequest = lastSent;
	}

	public long getLastSendRequest() {
		return this.lastSendRequest;
	}

	public long updateLastSendRequest(long interval) {
		this.lastSendRequest += interval;
		return this.lastSendRequest;
	}

	public String getUser() {
		return this.user;
	}

	public String getPassword() {
		return this.password;
	}
	
	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Message getResponse(String transid) {
		return queueResponse.remove(transid);
	}

	public ConcurrentHashMap<String, Message> getQueueResponse() {
		return this.queueResponse;
	}

}
