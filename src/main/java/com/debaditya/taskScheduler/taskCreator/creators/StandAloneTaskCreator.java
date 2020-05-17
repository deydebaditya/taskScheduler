package com.debaditya.taskScheduler.taskCreator.creators;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import com.debaditya.taskScheduler.models.request.TaskRequest;
import com.debaditya.taskScheduler.models.tasks.StandAloneTask;
import com.debaditya.taskScheduler.taskCreator.TaskCreator;

public class StandAloneTaskCreator implements TaskCreator<StandAloneTask> {

	@Override
	public StandAloneTask createTask(TaskRequest taskRequest) throws ParseException {
		StandAloneTask task = new StandAloneTask();
		task.setTaskName(Objects.isNull(taskRequest.getTaskName())
				? taskRequest.getTaskTriggerTime().concat(UUID.randomUUID().toString())
						: taskRequest.getTaskName());
		task.setTaskPriority(taskRequest.getTaskPriority());
		task.setTaskDuration(taskRequest.getTaskDuration());
		task.setTaskStatus(taskRequest.getTaskStatus());
		DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
		Date date = dateFormat.parse(taskRequest.getTaskTriggerTime());
		task.setTaskStartTime(date);
		return task;
	}

}
