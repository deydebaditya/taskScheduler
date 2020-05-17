package com.debaditya.taskScheduler.models.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskStatusResponseModel {

	private List<TaskStatusResponse> taskStatuses;

	public List<TaskStatusResponse> getTaskStatuses() {
		return taskStatuses;
	}

	public void setTaskStatuses(List<TaskStatusResponse> taskStatuses) {
		this.taskStatuses = taskStatuses;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((taskStatuses == null) ? 0 : taskStatuses.hashCode());
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
		TaskStatusResponseModel other = (TaskStatusResponseModel) obj;
		if (taskStatuses == null) {
			if (other.taskStatuses != null)
				return false;
		} else if (!taskStatuses.equals(other.taskStatuses))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TaskStatusResponseModel [taskStatuses=" + taskStatuses + "]";
	}
	
}
