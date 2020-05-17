package com.debaditya.taskScheduler.models.request;

import java.util.List;

public class TaskRequestModel {

	private List<TaskRequest> taskRequests;

	public List<TaskRequest> getTaskRequests() {
		return taskRequests;
	}

	public void setTaskRequests(List<TaskRequest> taskRequests) {
		this.taskRequests = taskRequests;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((taskRequests == null) ? 0 : taskRequests.hashCode());
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
		TaskRequestModel other = (TaskRequestModel) obj;
		if (taskRequests == null) {
			if (other.taskRequests != null)
				return false;
		} else if (!taskRequests.equals(other.taskRequests))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TaskRequestModel [taskRequests=" + taskRequests + "]";
	}
	
}
