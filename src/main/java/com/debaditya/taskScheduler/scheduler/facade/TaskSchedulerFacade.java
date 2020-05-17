package com.debaditya.taskScheduler.scheduler.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.debaditya.taskScheduler.models.request.TaskRequest;
import com.debaditya.taskScheduler.scheduler.Scheduler;
import com.debaditya.taskScheduler.taskCreator.creators.Task;

@Component
public class TaskSchedulerFacade {

	@Autowired
	@Qualifier("standAloneTaskScheduler")
	private Scheduler<Task> standAloneTaskScheduler;
	
	@Autowired
	@Qualifier("recurringTaskScheduler")
	private Scheduler<Task> recurringTaskScheduler;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskSchedulerFacade.class);
	
	public Scheduler<Task> fetchScheduler(TaskRequest request) throws Exception {
		
		switch(request.getTaskType()) {
			case A:
				LOGGER.info("Fetching stand alone task scheduler for task {}.", request.getTaskName());
				return standAloneTaskScheduler;
			case B:
				LOGGER.info("Fetching recurring task scheduler for task {}.", request.getTaskName());
				return recurringTaskScheduler;
			default:
				LOGGER.info("No valid task scheduler found for task {}.", request.getTaskName());
				throw new Exception("Invalid task type, no scheduler found!");
				
		}
		
	}
	
}
