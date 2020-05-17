package com.debaditya.taskScheduler.scheduler.impl;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.debaditya.taskScheduler.models.response.TaskStatusResponse;
import com.debaditya.taskScheduler.models.tasks.StandAloneTask;
import com.debaditya.taskScheduler.scheduler.Scheduler;
import com.debaditya.taskScheduler.taskCreator.creators.Task;
import com.debaditya.taskScheduler.threadPool.ThreadPool;

@Component
@Qualifier("standAloneTaskScheduler")
public class StandAloneTaskScheduler implements Scheduler<Task> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StandAloneTaskScheduler.class);
	
	@Autowired
	private ThreadPool pool;

	@Override
	public TaskStatusResponse scheduleTask(Task task) {
		StandAloneTask standAloneTask = (StandAloneTask)task;
		LOGGER.info("Scheduling stand alone task {} with priority {}, starting at {} and for {} seconds.", 
				standAloneTask.getTaskName(),
				standAloneTask.getTaskPriority().toString(),
				standAloneTask.getTaskStartTime().toString(),
				standAloneTask.getTaskDuration());
		pool.execute(standAloneTask, standAloneTask.getTaskName(), standAloneTask.getTaskPriority().getPriorityNumeral());
		return null;
	}

	@Override
	public TaskStatusResponse setTaskStatus(Task task) throws InterruptedException, ExecutionException {
		StandAloneTask standAloneTask = (StandAloneTask)task;
		LOGGER.info("Interrupting stand alone task {} with priority {}.", 
				standAloneTask.getTaskName(),
				standAloneTask.getTaskPriority().toString());
		pool.interruptTask(standAloneTask, standAloneTask.getTaskName(), standAloneTask.getTaskStatus());
		return null;
	}
	
}
