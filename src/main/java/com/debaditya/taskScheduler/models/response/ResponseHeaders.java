package com.debaditya.taskScheduler.models.response;

public class ResponseHeaders {

	private String apiEndpoint;
	private String status;
	
	@Override
	public String toString() {
		return "ResponseHeaders [apiEndpoint=" + apiEndpoint + ", status=" + status + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apiEndpoint == null) ? 0 : apiEndpoint.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		ResponseHeaders other = (ResponseHeaders) obj;
		if (apiEndpoint == null) {
			if (other.apiEndpoint != null)
				return false;
		} else if (!apiEndpoint.equals(other.apiEndpoint))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	
	public String getApiEndpoint() {
		return apiEndpoint;
	}
	public void setApiEndpoint(String apiEndpoint) {
		this.apiEndpoint = apiEndpoint;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
