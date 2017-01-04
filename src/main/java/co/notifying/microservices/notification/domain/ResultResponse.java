package co.notifying.microservices.notification.domain;

public class ResultResponse {
	private String registration_id;
	private String error;

	public ResultResponse(String registration_id, String error) {
		this.registration_id = registration_id;
		this.error = error;
	}

	public String getRegistration_id() {
		return registration_id;
	}

	public void setRegistration_id(String registration_id) {
		this.registration_id = registration_id;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
