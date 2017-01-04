package co.notifying.microservices.notification.fcm;

import java.util.List;

/**
 * See Defination Protocol in
 * https://firebase.google.com/docs/cloud-messaging/http-server-ref
 * 
 * Table 5 Downstream HTTP message response body (JSON).
 */
public class FCMResponseDTO {

	private Long multicast_id;
	private Long success;
	private Long failure;
	private Long canonical_ids;

	private List<FCMResultResponseDTO> result;
	// TODO: Define best kind Collection

	public FCMResponseDTO(Long multicast_id, Long success, Long failure, Long canonical_ids,
			List<FCMResultResponseDTO> result) {
		this.multicast_id = multicast_id;
		this.success = success;
		this.failure = failure;
		this.canonical_ids = canonical_ids;
		this.result = result;
	}

	public FCMResponseDTO() {
	}

	public Long getMulticast_id() {
		return multicast_id;
	}

	public void setMulticast_id(Long multicast_id) {
		this.multicast_id = multicast_id;
	}

	public Long getSuccess() {
		return success;
	}

	public void setSuccess(Long success) {
		this.success = success;
	}

	public Long getFailure() {
		return failure;
	}

	public void setFailure(Long failure) {
		this.failure = failure;
	}

	public Long getCanonical_ids() {
		return canonical_ids;
	}

	public void setCanonical_ids(Long canonical_ids) {
		this.canonical_ids = canonical_ids;
	}

	public List<FCMResultResponseDTO> getResult() {
		return result;
	}

	public void setResult(List<FCMResultResponseDTO> result) {
		this.result = result;
	}
}
