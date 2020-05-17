package com.debaditya.taskScheduler.models.request;

import com.debaditya.taskScheduler.models.taskMeta.TaskPriority;
import com.debaditya.taskScheduler.models.taskMeta.TaskStatus;
import com.debaditya.taskScheduler.models.taskMeta.TaskType;

public class TaskRequest {

	private String taskName = null;
	private Long taskDuration;
	private String taskTriggerTime;
	private TaskType taskType;
	private TaskPriority taskPriority;
	private String taskSchedule = null;
	private TaskStatus taskStatus = null;
	
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
	public String getTaskTriggerTime() {
		return taskTriggerTime;
	}
	public void setTaskTriggerTime(String taskTriggerTime) {
		this.taskTriggerTime = taskTriggerTime;
	}
	public TaskType getTaskType() {
		return taskType;
	}
	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}
	public String getTaskSchedule() {
		return taskSchedule;
	}
	public void setTaskSchedule(String taskSchedule) {
		this.taskSchedule = taskSchedule;
	}
	
	public TaskPriority getTaskPriority() {
		return taskPriority;
	}
	public void setTaskPriority(TaskPriority taskPriority) {
		this.taskPriority = taskPriority;
	}
	
	public TaskStatus getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(TaskStatus taskStatus) {
		this.taskStatus = taskStatus;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((taskDuration == null) ? 0 : taskDuration.hashCode());
		result = prime * result + ((taskName == null) ? 0 : taskName.hashCode());
		result = prime * result + ((taskPriority == null) ? 0 : taskPriority.hashCode());
		result = prime * result + ((taskSchedule == null) ? 0 : taskSchedule.hashCode());
		result = prime * result + ((taskStatus == null) ? 0 : taskStatus.hashCode());
		result = prime * result + ((taskTriggerTime == null) ? 0 : taskTriggerTime.hashCode());
		result = prime * result + ((taskType == null) ? 0 : taskType.hashCode());
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
		TaskRequest other = (TaskRequest) obj;
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
		if (taskSchedule == null) {
			if (other.taskSchedule != null)
				return false;
		} else if (!taskSchedule.equals(other.taskSchedule))
			return false;
		if (taskStatus != other.taskStatus)
			return false;
		if (taskTriggerTime == null) {
			if (other.taskTriggerTime != null)
				return false;
		} else if (!taskTriggerTime.equals(other.taskTriggerTime))
			return false;
		if (taskType != other.taskType)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "TaskRequest [taskName=" + taskName + ", taskDuration=" + taskDuration
				+ ", taskTriggerTime=" + taskTriggerTime + ", taskType=" + taskType + ", taskPriority=" + taskPriority
				+ ", taskSchedule=" + taskSchedule + ", taskStatus=" + taskStatus + "]";
	}
	
}
