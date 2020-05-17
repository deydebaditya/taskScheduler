package com.debaditya.taskScheduler.models.tasks;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.debaditya.taskScheduler.models.taskMeta.TaskPriority;
import com.debaditya.taskScheduler.models.taskMeta.TaskStatus;
import com.debaditya.taskScheduler.models.taskMeta.TaskType;
import com.debaditya.taskScheduler.taskCreator.creators.Task;

public class StandAloneTask extends Task {

	private static final Logger LOGGER = LoggerFactory.getLogger(StandAloneTask.class);
	
	private String taskName;
	private Long taskDuration;
	private Date taskStartTime;
	private TaskPriority taskPriority;
	private TaskStatus taskStatus;
	private TaskType taskType = TaskType.A;
	
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Long getTaskDuration() {
		return taskDuration;
	}
	public void setTaskDuration(Long taskDuration) {
		this.taskDuration = taskDuration;
	}
	public Date getTaskStartTime() {
		return taskStartTime;
	}
	public void setTaskStartTime(Date taskStartTime) {
		this.taskStartTime = taskStartTime;
	}
	public TaskPriority getTaskPriority() {
		return taskPriority;
	}
	public void setTaskPriority(TaskPriority taskPriority) {
		this.taskPriority = taskPriority;
	}
	public void setTaskStatus(TaskStatus taskStatus) {
		this.taskStatus = taskStatus;
	}
	public TaskStatus getTaskStatus() {
		return taskStatus;
	}
	public String getTaskSchedule() {
		return null;
	}
	public TaskType getTaskType() {
		return taskType;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((taskDuration == null) ? 0 : taskDuration.hashCode());
		result = prime * result + ((taskName == null) ? 0 : taskName.hashCode());
		result = prime * result + ((taskPriority == null) ? 0 : taskPriority.hashCode());
		result = prime * result + ((taskStartTime == null) ? 0 : taskStartTime.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StandAloneTask other = (StandAloneTask) obj;
		if (taskDuration == null) {
			if (other.taskDuration != null)
				return false;
		} else if (!taskDuration.equals(other.taskDuration))
			return false;
		if (taskName == null) {
			if (other.taskName != null)
				return false;
		} else if (!taskName.equals(other.taskName))
			return false;
		if (taskPriority != other.taskPriority)
			return false;
		if (taskStartTime == null) {
			if (other.taskStartTime != null)
				return false;
		} else if (!taskStartTime.equals(other.taskStartTime))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "StandAloneTask [taskName=" + taskName + ", taskDuration=" + taskDuration + ", taskStartTime="
				+ taskStartTime + ", taskPriority=" + taskPriority + "]";
	}
	
	@Override
	public Task call() {
		Thread.currentThread().setName(this.taskName);
		try {
			this.taskStatus = TaskStatus.ACTIVE;
			LOGGER.info("Executing stand alone task {} for {} seconds.", taskName, taskDuration);
			Thread.sleep(taskDuration * 1000);
			LOGGER.info("Completed execution of stand alone task {} for {} seconds.", taskName, taskDuration);
			this.taskStatus = TaskStatus.COMPLETED;
			return this;
		} catch (Exception e) {
			this.taskStatus = TaskStatus.INACTIVE;
			LOGGER.info("Exception occured while running task {}. Exception: {}", taskName, e.getMessage());
			return this;
		}
	}
	
	@Override
	public void run() {
		Thread.currentThread().setName(this.taskName);
		Thread.currentThread().setPriority(this.taskPriority.getPriorityNumeral());
		try {
			this.taskStatus = TaskStatus.ACTIVE;
			LOGGER.info("Executing stand alone task {} for {} seconds.", taskName, taskDuration);
			Thread.sleep(taskDuration * 1000);
			LOGGER.info("Completed execution of stand alone task {} for {} seconds.", taskName, taskDuration);
			this.taskStatus = TaskStatus.COMPLETED;
		} catch (Exception e) {
			this.taskStatus = TaskStatus.INACTIVE;
			LOGGER.info("Exception occured while running task {}. Exception: {}", taskName, e.getMessage());
		}
	}
	
	@Override
	public int compareTo(Task task) {
		return this.taskPriority.getPriorityNumeral().compareTo(task.getTaskPriority().getPriorityNumeral());
	}
	
}
