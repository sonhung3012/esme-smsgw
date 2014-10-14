package com.fis.esme.persistence;

import java.util.Date;

public class EsmeGroups implements java.io.Serializable{
	
	private long groupId;
	private String status;
	private Date createDate;
	private String description;
	private String name;
	private long rootId;
	private long parentId;
	
	public EsmeGroups() {
		super();
	}

	public EsmeGroups(long groupId, String status, Date createDate,
			String description, String name, long rootId, long parentId) {
		super();
		this.groupId = groupId;
		this.status = status;
		this.createDate = createDate;
		this.description = description;
		this.name = name;
		this.rootId = rootId;
		this.parentId = parentId;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getRootId() {
		return rootId;
	}

	public void setRootId(long rootId) {
		this.rootId = rootId;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
}
