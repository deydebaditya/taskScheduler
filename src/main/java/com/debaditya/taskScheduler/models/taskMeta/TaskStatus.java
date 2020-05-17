package com.debaditya.taskScheduler.models.taskMeta;

public enum TaskStatus {

	ACTIVE("ACTIVE"),
	INACTIVE("INACTIVE"),
	COMPLETED("COMPLETED");
	
	private String taskStatus;
	
	private TaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	
	@Override
	public String toString() {
		return this.taskStatus;
	}
	
}
