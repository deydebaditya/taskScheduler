package com.debaditya.taskScheduler.services.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.debaditya.taskScheduler.models.request.TaskRequestModel;
import com.debaditya.taskScheduler.models.response.TaskStatusResponse;
import com.debaditya.taskScheduler.models.response.TaskStatusResponseModel;
import com.debaditya.taskScheduler.models.taskMeta.TaskStatus;
import com.debaditya.taskScheduler.services.InfoService;
import com.debaditya.taskScheduler.taskCreator.creators.Task;
import com.debaditya.taskScheduler.threadPool.ThreadPool;
import com.debaditya.taskScheduler.models.tasks.StandAloneTask;
import com.debaditya.taskScheduler.models.tasks.RecurringTask;
import com.google.common.collect.Lists;

@Service
public class InfoServiceImpl implements InfoService<TaskRequestModel, TaskStatusResponseModel> {

	private static final Logger LOGGER = LoggerFactory.getLogger(InfoServiceImpl.class);

	@Autowired
	private ThreadPool pool;
	
	@Override
	public TaskStatusResponseModel getActiveTaskInfo(TaskRequestModel task) {
		List<Task> activeTasks = pool.getActiveTasks();
		List<String> activeTaskNames = activeTasks.stream().map(activeTask -> activeTask.getTaskName()).collect(Collectors.toList());
		List<TaskStatusResponse> statusResponses = Lists.newArrayList();
		TaskStatusResponseModel response = new TaskStatusResponseModel();
		if(Objects.isNull(task)) {
			for(String taskName : activeTaskNames) {
				TaskStatusResponse statusResponse = new TaskStatusResponse();
				LOGGER.info("Task {} is active.", taskName);
				statusResponse.setTaskName(taskName);
				statusResponse.setTaskCurrentStatus(TaskStatus.ACTIVE);
				statusResponses.add(statusResponse);
			}
		} else {
			List<String> taskRequests = task.getTaskRequests().stream().map(taskReq -> taskReq.getTaskName()).collect(Collectors.toList());
			List<String> activeRequests = taskRequests.stream().filter(req -> activeTaskNames.contains(req)).collect(Collectors.toList());
			for(String taskName : activeRequests) {
				TaskStatusResponse statusResponse = new TaskStatusResponse();
				LOGGER.info("Task {} is active.", taskName);
				statusResponse.setTaskName(taskName);
				statusResponse.setTaskCurrentStatus(TaskStatus.ACTIVE);
				statusResponses.add(statusResponse);
			}
		}
		response.setTaskStatuses(statusResponses);
		return response;
	}
	
	@Override
	public TaskStatusResponseModel getInactiveTaskInfo(TaskRequestModel task) {
		List<Task> inactiveTasks = pool.getInactiveTasks();
		List<String> inactiveTaskNames = inactiveTasks.stream().map(inactiveTask -> inactiveTask.getTaskName()).collect(Collectors.toList());
		List<TaskStatusResponse> statusResponses = Lists.newArrayList();
		TaskStatusResponseModel response = new TaskStatusResponseModel();
		if(Objects.isNull(task)) {
			for(String taskName : inactiveTaskNames) {
				TaskStatusResponse statusResponse = new TaskStatusResponse();
				LOGGER.info("Task {} is inactive.", taskName);
				statusResponse.setTaskName(taskName);
				statusResponse.setTaskCurrentStatus(TaskStatus.INACTIVE);
				statusResponses.add(statusResponse);
			}
		} else {
			List<String> taskRequests = task.getTaskRequests().stream().map(taskReq -> taskReq.getTaskName()).collect(Collectors.toList());
			List<String> inactiveRequests = taskRequests.stream().filter(req -> inactiveTaskNames.contains(req)).collect(Collectors.toList());
			for(String taskName : inactiveRequests) {
				TaskStatusResponse statusResponse = new TaskStatusResponse();
				LOGGER.info("Task {} is inactive.", taskName);
				statusResponse.setTaskName(taskName);
				statusResponse.setTaskCurrentStatus(TaskStatus.INACTIVE);
				statusResponses.add(statusResponse);
			}
		}
		response.setTaskStatuses(statusResponses);
		return response;
	}
	
	@Override
	public TaskStatusResponseModel getCompletedTaskInfo(TaskRequestModel task) {
		List<Task> completedTasks = pool.getCompletedTasks();
		List<String> completedTaskNames = completedTasks.stream().map(completedTask -> completedTask.getTaskName()).collect(Collectors.toList());
		List<TaskStatusResponse> statusResponses = Lists.newArrayList();
		TaskStatusResponseModel response = new TaskStatusResponseModel();
		if(Objects.isNull(task)) {
			for(String taskName : completedTaskNames) {
				TaskStatusResponse statusResponse = new TaskStatusResponse();
				LOGGER.info("Task {} has been completed.", taskName);
				statusResponse.setTaskName(taskName);
				statusResponse.setTaskCurrentStatus(TaskStatus.COMPLETED);
				statusResponses.add(statusResponse);
			}
		} else {
			List<String> taskRequests = task.getTaskRequests().stream().map(taskReq -> taskReq.getTaskName()).collect(Collectors.toList());
			List<String> completedRequests = taskRequests.stream().filter(req -> completedTaskNames.contains(req)).collect(Collectors.toList());
			for(String taskName : completedRequests) {
				TaskStatusResponse statusResponse = new TaskStatusResponse();
				LOGGER.info("Task {} has been completed.", taskName);
				statusResponse.setTaskName(taskName);
				statusResponse.setTaskCurrentStatus(TaskStatus.COMPLETED);
				statusResponses.add(statusResponse);
			}
		}
		response.setTaskStatuses(statusResponses);
		return response;
	}

	@Override
	public TaskStatusResponseModel getTaskExecutionStatus(Date startTime, Date endTime) {
		List<Task> activeTasks = pool.getActiveTasks();
		List<Task> inactiveTasks = pool.getInactiveTasks();
		List<Task> completedTasks = pool.getCompletedTasks();
		List<TaskStatusResponse> taskRunStatus = Lists.newArrayList();
		activeTasks.stream().forEach(task -> {
			populateTaskStatusResponse(task, taskRunStatus, startTime, endTime);
		});
		inactiveTasks.stream().forEach(task -> {
			populateTaskStatusResponse(task, taskRunStatus, startTime, endTime);
		});
		completedTasks.stream().forEach(task -> {
			populateTaskStatusResponse(task, taskRunStatus, startTime, endTime);
		});
		TaskStatusResponseModel responseModel = new TaskStatusResponseModel();
		responseModel.setTaskStatuses(taskRunStatus);
		return responseModel;
	}
	
	private void populateTaskStatusResponse(Task task, List<TaskStatusResponse> taskRunStatus, Date startTime, Date endTime) {
		TaskStatusResponse taskStatusResponse = new TaskStatusResponse();
		switch(task.getTaskType()) {
			case A:
				StandAloneTask standAloneTask = (StandAloneTask)task;
				if(standAloneTask.getTaskStartTime().after(startTime) && standAloneTask.getTaskStartTime().before(endTime)) {
					taskStatusResponse.setTaskName(standAloneTask.getTaskName());
					taskStatusResponse.setTaskCurrentStatus(standAloneTask.getTaskStatus());
					taskRunStatus.add(taskStatusResponse);
				}
				break;
			case B:
				RecurringTask recurringTask = (RecurringTask)task;
				Long execNumber = 0L;
				if(recurringTask.getNumberOfTaskRuns() != 0) {
					Iterator<Entry<Date, Long>> execIter = recurringTask.getExecutions().entrySet().iterator();
					while(execIter.hasNext()) {
						Date executionTime = execIter.next().getKey();
						if(executionTime.after(startTime) && executionTime.before(endTime)) {
							execNumber++;
						}
					}
					taskStatusResponse = new TaskStatusResponse();
					taskStatusResponse.setTaskName(recurringTask.getTaskName());
					taskStatusResponse.setTaskCurrentStatus(recurringTask.getTaskStatus());
					taskStatusResponse.setNumberOfExecutionsDone(execNumber);
					taskRunStatus.add(taskStatusResponse);
				}
				break;
		}
	}

}
