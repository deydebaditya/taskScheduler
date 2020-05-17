package com.debaditya.taskScheduler.services;

public interface SchedulerService<T> extends Service {

	public void scheduleTask(T tasks);
	public void setTaskStatus(T tasks);
	
}
