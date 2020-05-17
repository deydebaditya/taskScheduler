package com.debaditya.taskScheduler.taskCreator.creators;

import java.util.Date;

import com.debaditya.taskScheduler.models.taskMeta.TaskPriority;
import com.debaditya.taskScheduler.models.taskMeta.TaskStatus;
import com.debaditya.taskScheduler.models.taskMeta.TaskType;

public abstract class Task implements Comparable<Task>, CallableTask, Runnable {

	public abstract String getTaskName();
	public abstract TaskPriority getTaskPriority();
	public abstract Long getTaskDuration();
	public abstract TaskStatus getTaskStatus();
	public abstract Date getTaskStartTime();
	public abstract String getTaskSchedule();
	public abstract TaskType getTaskType();
	
}
