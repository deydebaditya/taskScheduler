package com.debaditya.taskScheduler.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.debaditya.taskScheduler.models.request.TaskRequestModel;
import com.debaditya.taskScheduler.services.SchedulerService;

@RestController
@RequestMapping("/task")
public class TaskSchedulingEndpoints {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskSchedulingEndpoints.class);
	
	@Autowired
	private SchedulerService<TaskRequestModel> schedulerService;

	@RequestMapping(value="/schedule",
			method=RequestMethod.POST,
			consumes="application/JSON",
			produces="application/JSON")
	public void scheduleTask(@RequestBody TaskRequestModel taskModel) {
		LOGGER.info("Received {} task(s) for scheduling.", taskModel.getTaskRequests().size());
		schedulerService.scheduleTask(taskModel);
	}
	
	@RequestMapping(value="/setStatus",
			method=RequestMethod.POST,
			consumes="application/JSON",
			produces="application/JSON")
	public void setTaskStatus(@RequestBody TaskRequestModel taskModel) {
		LOGGER.info("Received {} task(s) for status update.", taskModel.getTaskRequests().size());
		schedulerService.setTaskStatus(taskModel);
	}
	
}
