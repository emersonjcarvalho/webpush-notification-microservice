package co.notifying.microservices.notification.domain;

import java.util.List;

public class ResponseWebNotification {

	private Long request_notification_id;

	private Long num_failure;

	private List<ResultResponse> result;

	public ResponseWebNotification() {
	}

	public ResponseWebNotification(Long request_notification_id, Long num_failure, List<ResultResponse> result) {
		this.request_notification_id = request_notification_id;
		this.num_failure = num_failure;
		this.result = result;
	}

	public Long getRequest_notification_id() {
		return request_notification_id;
	}

	public void setRequest_notification_id(Long request_notification_id) {
		this.request_notification_id = request_notification_id;
	}

	public Long getNum_failure() {
		return num_failure;
	}

	public void setNum_failure(Long num_failure) {
		this.num_failure = num_failure;
	}

	public List<ResultResponse> getResult() {
		return result;
	}

	public void setResult(List<ResultResponse> result) {
		this.result = result;
	}
}
