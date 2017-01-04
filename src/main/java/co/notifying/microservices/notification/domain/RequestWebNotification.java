package co.notifying.microservices.notification.domain;

import java.util.List;

public class RequestWebNotification {

	/**
	 * ID Service or App is requesting the Notification
	 */
	private Long requester_id; // Required
	private String title; // Required
	private String body; // Required
	private String url_icon;
	private String url_click_action;

	/**
	 * ID or Endpoint of one or multiple Targets to receive this Message
	 */
	private List<String> targets; // Required

	public RequestWebNotification() {
	}

	public RequestWebNotification(Long requester_id, String title, String body, String url_icon,
			String url_click_action, List<String> targets) {
		this.requester_id = requester_id;
		this.title = title;
		this.body = body;
		this.url_icon = url_icon;
		this.url_click_action = url_click_action;
		this.targets = targets;
	}

	public Long getRequester_id() {
		return requester_id;
	}

	public void setRequester_id(Long requester_id) {
		this.requester_id = requester_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getUrl_icon() {
		return url_icon;
	}

	public void setUrl_icon(String url_icon) {
		this.url_icon = url_icon;
	}

	public String getUrl_click_action() {
		return url_click_action;
	}

	public void setUrl_click_action(String url_click_action) {
		this.url_click_action = url_click_action;
	}

	public List<String> getTargets() {
		return targets;
	}

	public void setTargets(List<String> targets) {
		this.targets = targets;
	}
}
