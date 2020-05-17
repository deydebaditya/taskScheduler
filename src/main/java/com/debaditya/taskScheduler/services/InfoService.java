package com.debaditya.taskScheduler.services;

import java.util.Date;

public interface InfoService<T, R> extends Service {

	public R getActiveTaskInfo(T task);
	public R getInactiveTaskInfo(T task);
	public R getCompletedTaskInfo(T task);
	public R getTaskExecutionStatus(Date startTime, Date endTime);
	
}
