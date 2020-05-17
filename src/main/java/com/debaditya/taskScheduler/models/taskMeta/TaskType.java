package com.debaditya.taskScheduler.models.taskMeta;

public enum TaskType {

	A("STANDALONE"),
	B("RECURRING");
	
	private String taskTypeDescrition;
	
	private TaskType(String taskTypeDescrition) {
		this.taskTypeDescrition = taskTypeDescrition;
	}
	
	public String getTaskDescription() {
		return this.taskTypeDescrition;
	}
	
}
