package com.fis.esme.persistence;

public class EsmeScheduler implements java.io.Serializable{
	private long schedulerId;
	private String type;
	private String name;
	private String description;
	private String schedulerStatus;
	private String status;
	private String time;
	
	public EsmeScheduler() {
		super();
	}

	public EsmeScheduler(long schedulerId, String type, String name,
			String description, String schedulerStatus, String status,
			String time) {
		super();
		this.schedulerId = schedulerId;
		this.type = type;
		this.name = name;
		this.description = description;
		this.schedulerStatus = schedulerStatus;
		this.status = status;
		this.time = time;
	}

	public long getSchedulerId() {
		return schedulerId;
	}

	public void setSchedulerId(long schedulerId) {
		this.schedulerId = schedulerId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSchedulerStatus() {
		return schedulerStatus;
	}

	public void setSchedulerStatus(String schedulerStatus) {
		this.schedulerStatus = schedulerStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
