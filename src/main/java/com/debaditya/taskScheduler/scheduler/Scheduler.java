package com.debaditya.taskScheduler.scheduler;

import java.util.concurrent.ExecutionException;

import com.debaditya.taskScheduler.models.response.TaskStatusResponse;
import com.debaditya.taskScheduler.taskCreator.creators.Task;

public interface Scheduler<T extends Task> {

	public TaskStatusResponse scheduleTask(T task);
	public TaskStatusResponse setTaskStatus(T task) throws InterruptedException, ExecutionException;
	
}