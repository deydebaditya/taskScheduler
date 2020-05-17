package com.debaditya.taskScheduler.models.response;

import com.debaditya.taskScheduler.models.taskMeta.TaskStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskStatusResponse {

	private String taskName;
	private TaskStatus taskCurrentStatus;
	private Long numberOfExecutionsDone;

	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public TaskStatus getTaskCurrentStatus() {
		return taskCurrentStatus;
	}
	public void setTaskCurrentStatus(TaskStatus taskCurrentStatus) {
		this.taskCurrentStatus = taskCurrentStatus;
	}
	public Long getNumberOfExecutionsDone() {
		return numberOfExecutionsDone;
	}
	public void setNumberOfExecutionsDone(Long numberOfExecutionsDone) {
		this.numberOfExecutionsDone = numberOfExecutionsDone;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numberOfExecutionsDone == null) ? 0 : numberOfExecutionsDone.hashCode());
		result = prime * result + ((taskName == null) ? 0 : taskName.hashCode());
		result = prime * result + ((taskCurrentStatus == null) ? 0 : taskCurrentStatus.hashCode());
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
		TaskStatusResponse other = (TaskStatusResponse) obj;
		if (numberOfExecutionsDone == null) {
			if (other.numberOfExecutionsDone != null)
				return false;
		} else if (!numberOfExecutionsDone.equals(other.numberOfExecutionsDone))
			return false;
		if (taskName == null) {
			if (other.taskName != null)
				return false;
		} else if (!taskName.equals(other.taskName))
			return false;
		if (taskCurrentStatus != other.taskCurrentStatus)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "TaskStatusResponse [taskName=" + taskName + ", taskStatus=" + taskCurrentStatus + ", numberOfExecutionsDone="
				+ numberOfExecutionsDone + "]";
	}
	
}
