package com.debaditya.taskScheduler.models.response;

public class ResponseBaseModel<T> {

	private ResponseHeaders headers;
	private T responseBody;
	
	public ResponseHeaders getHeaders() {
		return headers;
	}
	public void setHeaders(ResponseHeaders headers) {
		this.headers = headers;
	}
	public T getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(T responseBody) {
		this.responseBody = responseBody;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((headers == null) ? 0 : headers.hashCode());
		result = prime * result + ((responseBody == null) ? 0 : responseBody.hashCode());
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
		ResponseBaseModel other = (ResponseBaseModel) obj;
		if (headers == null) {
			if (other.headers != null)
				return false;
		} else if (!headers.equals(other.headers))
			return false;
		if (responseBody == null) {
			if (other.responseBody != null)
				return false;
		} else if (!responseBody.equals(other.responseBody))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ResponseBaseModel [headers=" + headers + ", responseBody=" + responseBody + "]";
	}
	
}
