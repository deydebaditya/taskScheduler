package com.debaditya.taskScheduler.taskCreator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.debaditya.taskScheduler.models.request.TaskRequest;
import com.debaditya.taskScheduler.models.tasks.RecurringTask;
import com.debaditya.taskScheduler.models.tasks.StandAloneTask;
import com.debaditya.taskScheduler.taskCreator.creators.RecurringTaskCreator;
import com.debaditya.taskScheduler.taskCreator.creators.StandAloneTaskCreator;

public class TaskCreatorFactory {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskCreatorFactory.class);

	public static TaskCreator<?> getTaskCreator(TaskRequest taskRequest) throws Exception {
		switch (taskRequest.getTaskType()) {
			case A:
				LOGGER.info("{} task's type found to be A. Fetching stand alone task creator.", taskRequest.getTaskName());
				TaskCreator<StandAloneTask> standAloneTaskCreator = new StandAloneTaskCreator();
				return standAloneTaskCreator;
			case B:
				LOGGER.info("{} task's type found to be B. Fetching recurring task creator.", taskRequest.getTaskName());
				TaskCreator<RecurringTask> recurringTaskCreator = new RecurringTaskCreator();
				return recurringTaskCreator;
			default:
				LOGGER.info("No task creator found for task {}", taskRequest.getTaskName());
				throw new Exception("Unsupported task type");
		}
	}

}
