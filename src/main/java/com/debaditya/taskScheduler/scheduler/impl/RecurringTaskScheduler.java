package com.debaditya.taskScheduler.scheduler.impl;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.debaditya.taskScheduler.models.response.TaskStatusResponse;
import com.debaditya.taskScheduler.models.tasks.RecurringTask;
import com.debaditya.taskScheduler.scheduler.Scheduler;
import com.debaditya.taskScheduler.taskCreator.creators.Task;
import com.debaditya.taskScheduler.threadPool.ThreadPool;

@Component
@Qualifier("recurringTaskScheduler")
public class RecurringTaskScheduler implements Scheduler<Task> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RecurringTaskScheduler.class);

	@Autowired
	private ThreadPool pool;
	
	@Override
	public TaskStatusResponse scheduleTask(Task task) {
		RecurringTask recurringTask = (RecurringTask)task;
		LOGGER.info("Scheduling recurring task {} with priority {} for {} seconds and for schedule {}.", 
				recurringTask.getTaskName(),
				recurringTask.getTaskPriority().toString(),
				recurringTask.getTaskDuration(),
				recurringTask.getTaskSchedule());
		pool.execute(recurringTask, recurringTask.getTaskName(), recurringTask.getTaskPriority().getPriorityNumeral());
		return null;
	}

	@Override
	public TaskStatusResponse setTaskStatus(Task task) throws InterruptedException, ExecutionException {
		RecurringTask recurringTask = (RecurringTask)task;
		LOGGER.info("Interrupting recurring task {}.", 
				recurringTask.getTaskName());
		pool.interruptTask(recurringTask, recurringTask.getTaskName(), recurringTask.getTaskStatus());
		return null;
	}

}
