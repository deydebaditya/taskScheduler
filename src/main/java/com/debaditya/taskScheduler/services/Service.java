package com.debaditya.taskScheduler.services;

import com.debaditya.taskScheduler.models.response.ResponseBaseModel;
import com.debaditya.taskScheduler.models.response.ResponseHeaders;

public interface Service {

	default <T> ResponseBaseModel<T> populateResponse(T data, String apiEndpoint, String status) {
		ResponseBaseModel<T> response = new ResponseBaseModel<T>();
		response.setResponseBody(data);
		ResponseHeaders headers = new ResponseHeaders();
		headers.setApiEndpoint(apiEndpoint);
		headers.setStatus(status);
		response.setHeaders(headers);
		return response;
	}
	
}
