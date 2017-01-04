package co.notifying.microservices.notification.fcm;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 * Based at Firebase Cloud Messaging HTTP Protocol
 * https://firebase.google.com/docs/cloud-messaging/http-server-ref
 *
 */
public class FCMRequestDTO {

	private List<String> targets;
	private Map<String, Object> requestParameters;
	private Map<String, Object> notificationParameters;

	private void init() {
		// initRequestParameters
		this.requestParameters = new HashMap<String, Object>();
		// initNotificationParameters
		this.notificationParameters = new HashMap<String, Object>();
	}

	/**
	 * Minimums Parameters for perform a request
	 * 
	 * @param title
	 * @param body
	 * @param targets
	 */
	public FCMRequestDTO(String title, String body, List<String> targets) {

		init();

		addNotificationParameters("title", title);
		addNotificationParameters("body", body);
		setTarget(targets);
	}

	/**
	 * When just 01 client set parameter "to". when many client set multicast
	 * collection.
	 * 
	 * @param targets
	 */
	private void setTarget(List<String> targets) {
		if (targets.size() == 1) {
			// to
			addRequestParameters("to", targets.get(0));
		} else {
			this.targets = new ArrayList<String>(targets);
		}
	}

	private void addRequestParameters(String key, Object value) {
		this.requestParameters.put(key, value);
	}

	private void addNotificationParameters(String key, Object value) {
		this.notificationParameters.put(key, value);
	}

	/**************************************************************
	 * Add NOTIFICATIONS Parameters
	 **************************************************************/
	public FCMRequestDTO icon(String icon) {
		addNotificationParameters("icon", icon);
		return this;
	}

	public FCMRequestDTO click_action(String click_action) {
		addNotificationParameters("click_action", click_action);
		return this;
	}

	/**************************************************************
	 * Add REQUESTS Parameters
	 **************************************************************/
	public FCMRequestDTO time_to_live(Long time_to_live) {
		addRequestParameters("time_to_live", time_to_live);
		return this;
	}

	public String asJson(final Gson gson) {
		Type typeMap = new TypeToken<Map<String, Object>>() {
		}.getType();

		Type typeList = new TypeToken<List<String>>() {
		}.getType();

		JsonObject rootNode = new JsonObject();
		JsonElement notificationElement = gson.toJsonTree(this.notificationParameters, typeMap);
		rootNode.add("notification", notificationElement);

		requestParameters.forEach((String k, Object v) -> rootNode.add(k, gson.toJsonTree(v)));

		if (targets != null && !targets.isEmpty()) {
			JsonElement targetsElement = gson.toJsonTree(targets, typeList);
			rootNode.add("registration_ids", targetsElement);
		}

		return gson.toJson(rootNode);
	}
}
