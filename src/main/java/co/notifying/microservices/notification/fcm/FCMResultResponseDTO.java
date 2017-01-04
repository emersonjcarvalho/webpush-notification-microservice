package co.notifying.microservices.notification.fcm;

public class FCMResultResponseDTO {

	private String message_id;
	private String registration_id;

	// TODO: Create a ENUM for Erros Codes
	// https://firebase.google.com/docs/cloud-messaging/http-server-ref#table9
	private String error;

	public FCMResultResponseDTO(String message_id, String registration_id, String error) {
		this.message_id = message_id;
		this.registration_id = registration_id;
		this.error = error;
	}

	public FCMResultResponseDTO() {
	}

	public String getMessage_id() {
		return message_id;
	}

	public void setMessage_id(String message_id) {
		this.message_id = message_id;
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
