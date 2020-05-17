package com.debaditya.taskScheduler.models.taskMeta;

public enum TaskPriority {

	LOW(1, "LOW"),
	MEDIUM(5, "MEDIUM"),
	HIGH(10, "HIGH");
	
	private Integer taskPriority;
	private String taskPriorityString;
	
	private TaskPriority(Integer taskPriority, String taskPriorityString) {
		this.taskPriority = taskPriority;
		this.taskPriorityString = taskPriorityString;
	}
	
	public Integer getPriorityNumeral() {
		return this.taskPriority;
	}
	
	@Override
	public String toString() {
		return this.taskPriorityString;
	}
	
}
