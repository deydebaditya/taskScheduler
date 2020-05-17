package com.debaditya.taskScheduler.services.impl;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.debaditya.taskScheduler.models.request.TaskRequestModel;
import com.debaditya.taskScheduler.scheduler.facade.TaskSchedulerFacade;
import com.debaditya.taskScheduler.services.SchedulerService;
import com.debaditya.taskScheduler.taskCreator.TaskCreatorFactory;
import com.debaditya.taskScheduler.taskCreator.creators.Task;

@Service
public class SchedulerServiceImpl implements SchedulerService<TaskRequestModel> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerServiceImpl.class);
	
	@Autowired
	private TaskSchedulerFacade taskSchedulerFacade;
	
	@Override
	public void scheduleTask(TaskRequestModel tasks) {
		
		tasks.getTaskRequests().stream().forEach(task -> {
			try {
				LOGGER.info("Trying to schedule task: {}", task.getTaskName());
				taskSchedulerFacade.fetchScheduler(task)
						.scheduleTask((Task)TaskCreatorFactory
								.getTaskCreator(task)
								.createTask(task));
			} catch (ParseException e) {
				LOGGER.error("Scheduling of task {} failed due to {}", task.getTaskName(), e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				LOGGER.error("Scheduling of task {} failed due to {}", task.getTaskName(), e.getMessage());
				e.printStackTrace();
			}
		});
		
	}

	@Override
	public void setTaskStatus(TaskRequestModel tasks) {
		
		tasks.getTaskRequests().stream().forEach(taskRequest -> {
			Task task;
			try {
				task = (Task)TaskCreatorFactory.getTaskCreator(taskRequest)
													.createTask(taskRequest);
				LOGGER.info("Trying to set status of task {} to {}", task.getTaskName(), task.getTaskStatus());
				taskSchedulerFacade.fetchScheduler(taskRequest).setTaskStatus(task);
			} catch (ParseException e) {
				LOGGER.error("Scheduling of task {} failed due to {}", taskRequest.getTaskName(), e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				LOGGER.error("Scheduling of task {} failed due to {}", taskRequest.getTaskName(), e.getMessage());
				e.printStackTrace();
			}
		});
		
	}

}
