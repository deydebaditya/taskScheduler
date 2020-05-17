package com.debaditya.taskScheduler.taskCreator;

import java.text.ParseException;

import com.debaditya.taskScheduler.models.request.TaskRequest;

public interface TaskCreator<T> {
	
	public T createTask(TaskRequest taskRequest) throws ParseException;
	
}
