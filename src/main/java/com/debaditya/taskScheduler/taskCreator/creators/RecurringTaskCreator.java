package com.debaditya.taskScheduler.taskCreator.creators;

import java.util.Objects;
import java.util.UUID;

import com.debaditya.taskScheduler.models.request.TaskRequest;
import com.debaditya.taskScheduler.models.tasks.RecurringTask;
import com.debaditya.taskScheduler.taskCreator.TaskCreator;

public class RecurringTaskCreator implements TaskCreator<RecurringTask> {

	@Override
	public RecurringTask createTask(TaskRequest taskRequest) {
		RecurringTask task = new RecurringTask();
		task.setTaskName(Objects.isNull(taskRequest.getTaskName())
				? taskRequest.getTaskTriggerTime().concat(UUID.randomUUID().toString())
						: taskRequest.getTaskName());
		task.setTaskPriority(taskRequest.getTaskPriority());
		task.setTaskDuration(taskRequest.getTaskDuration());
		task.setTaskStatus(taskRequest.getTaskStatus());
		task.setTaskSchedule(taskRequest.getTaskSchedule());
		return task;
	}

}
