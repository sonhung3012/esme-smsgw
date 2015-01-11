package com.fis.esme.core.entity;

public class SMSMORoutingObject {
	private int service_id;
	private int parent_service_id;
	private int root_id;
	private int short_code_id;
	private int cp_id;
	private int command_id;
	private String short_code_code;
	private String cmd_code;
	private String cp_code;
	private String protocal;
	private String url;
	private String username;
	private String password;


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getParent_service_id() {
		return parent_service_id;
	}

	public void setParent_service_id(int parent_service_id) {
		this.parent_service_id = parent_service_id;
	}

	public int getService_id() {
		return service_id;
	}

	public void setService_id(int service_id) {
		this.service_id = service_id;
	}

	public int getRoot_id() {
		return root_id;
	}

	public void setRoot_id(int root_id) {
		this.root_id = root_id;
	}

	public int getShort_code_id() {
		return short_code_id;
	}

	public void setShort_code_id(int short_code_id) {
		this.short_code_id = short_code_id;
	}

	public int getCp_id() {
		return cp_id;
	}

	public void setCp_id(int cp_id) {
		this.cp_id = cp_id;
	}

	public int getCommand_id() {
		return command_id;
	}

	public void setCommand_id(int command_id) {
		this.command_id = command_id;
	}

	public String getShort_code_code() {
		return short_code_code;
	}

	public void setShort_code_code(String short_code_code) {
		this.short_code_code = short_code_code;
	}

	public String getCmd_code() {
		return cmd_code;
	}

	public void setCmd_code(String cmd_code) {
		this.cmd_code = cmd_code;
	}

	public String getCp_code() {
		return cp_code;
	}

	public void setCp_code(String cp_code) {
		this.cp_code = cp_code;
	}

	public String getProtocal() {
		return protocal;
	}

	public void setProtocal(String protocal) {
		this.protocal = protocal;
	}

}
