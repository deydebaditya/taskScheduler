package com.debaditya.taskScheduler.rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.debaditya.taskScheduler.models.request.TaskRequest;
import com.debaditya.taskScheduler.models.request.TaskRequestModel;
import com.debaditya.taskScheduler.models.response.ResponseBaseModel;
import com.debaditya.taskScheduler.models.response.TaskStatusResponseModel;
import com.debaditya.taskScheduler.services.InfoService;
import com.google.common.collect.Lists;

@RestController
@RequestMapping("/task/info")
public class TaskInfoEndpoints {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskInfoEndpoints.class);
	
	@Autowired
	private InfoService<TaskRequestModel, TaskStatusResponseModel> infoService;
	
	@RequestMapping(value="/inactiveTasks",
			method=RequestMethod.GET,
			produces="application/JSON")
	public ResponseBaseModel<TaskStatusResponseModel> getInactiveTaskInfo(@RequestParam("taskName") @Nullable String taskName) {
		if(Objects.isNull(taskName)) {
			return infoService.populateResponse(infoService.getInactiveTaskInfo(null), "task/info/inactiveTasks", "SUCCESS");
		}
		TaskRequest taskRequest = new TaskRequest();
		taskRequest.setTaskName(taskName);
		List<TaskRequest> taskRequests = Lists.newArrayList();
		taskRequests.add(taskRequest);
		TaskRequestModel request = new TaskRequestModel();
		request.setTaskRequests(taskRequests);
		return infoService.populateResponse(infoService.getInactiveTaskInfo(request), "task/info/inactiveTasks", "SUCCESS");
	}

	@RequestMapping(value="/activeTasks",
			method=RequestMethod.GET,
			produces="application/JSON")
	public ResponseBaseModel<TaskStatusResponseModel> getActiveTaskInfo(@RequestParam("taskName") @Nullable String taskName) {
		if(Objects.isNull(taskName)) {
			return infoService.populateResponse(infoService.getActiveTaskInfo(null), "task/info/activeTasks", "SUCCESS");
		}
		TaskRequest taskRequest = new TaskRequest();
		taskRequest.setTaskName(taskName);
		List<TaskRequest> taskRequests = Lists.newArrayList();
		taskRequests.add(taskRequest);
		TaskRequestModel request = new TaskRequestModel();
		request.setTaskRequests(taskRequests);
		return infoService.populateResponse(infoService.getActiveTaskInfo(request), "task/info/activeTasks", "SUCCESS");
	}
	
	@RequestMapping(value="/completedTasks",
			method=RequestMethod.GET,
			produces="application/JSON")
	public ResponseBaseModel<TaskStatusResponseModel> getCompletedTaskInfo(@RequestParam("taskName") @Nullable String taskName) {
		if(Objects.isNull(taskName)) {
			return infoService.populateResponse(infoService.getCompletedTaskInfo(null), "task/info/completedTasks", "SUCCESS");
		}
		TaskRequest taskRequest = new TaskRequest();
		taskRequest.setTaskName(taskName);
		List<TaskRequest> taskRequests = Lists.newArrayList();
		taskRequests.add(taskRequest);
		TaskRequestModel request = new TaskRequestModel();
		request.setTaskRequests(taskRequests);
		return infoService.populateResponse(infoService.getCompletedTaskInfo(request), "task/info/completedTasks", "SUCCESS");
	}
	
	@RequestMapping(value="/executionStatus",
			method=RequestMethod.GET,
			produces="application/JSON")
	public ResponseBaseModel<TaskStatusResponseModel> getTaskExecutionStatus(@RequestParam("t1") String startTime,
			@RequestParam("t2") String endTime) {
		DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
		try {
			Date start = dateFormat.parse(startTime);
			Date end = dateFormat.parse(endTime);
			return infoService.populateResponse(infoService.getTaskExecutionStatus(start, end), "/task/info/executionStatus", "SUCCESS");
		} catch (ParseException e) {
			LOGGER.error("Error occured while parsing date: {}", e.getMessage());
			return infoService.populateResponse(null, "/task/info/executionStatus", "FAILURE");
		}
	}
	
}
