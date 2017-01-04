package co.notifying.microservices.notification.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import co.notifying.microservices.notification.RequestHandler;
import co.notifying.microservices.notification.domain.RequestWebNotification;
import co.notifying.microservices.notification.domain.ResponseWebNotification;
import co.notifying.microservices.notification.exception.BadRequestException;
import co.notifying.microservices.notification.exception.InternalErrorException;
import co.notifying.microservices.notification.util.JsonUtils;

/**
 * TODO Implements Unit, Performance and Applications Tests
 */
public class TesterRequestHandler {

	private static String ID_TOKEN_INSTANCE_BUG = "XXXXX6UGasWJXo:APA91bEad3xlRwK2AThol5FV0LLLC3ApJlzvz9YLecjbfEkmMAcLpHEj8u806Zp8XvFADn31GbhiEMXgnakjaKI";

	private static String ID_TOKEN_INSTANCE_OK = "cBsJFOcgjm8:APA91bGR7ZTKdYG8oLkGc9ctigdd2fs2E_jbGz1_KvbFTULjeKcdA-JWpeG9-3QhgDtHiEV2PBEMqDYGEYRP0AyKPid_XQhCH9z1_A6o8bsa11fsr2f2Upc1DuzBbb41RUfZB8dWd1Gu";

	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();

		ResponseWebNotification webPush = null;

		Long requester_id = -99L;
		String title = "Notif Title";
		String body = "Notif Body... Its Work!";
		String url_icon = "";
		String url_click_action = "https://facebook.github.io/react/";
		List<String> targets = new ArrayList<>();
		targets.add(ID_TOKEN_INSTANCE_BUG);
		targets.add(ID_TOKEN_INSTANCE_OK);
		RequestWebNotification requestWebNotification = new RequestWebNotification(requester_id, title, body, url_icon,
				url_click_action, targets);

		Gson gsonTester = JsonUtils.getGsonFromBuilder();
		String jsonRequest = gsonTester.toJson(requestWebNotification, RequestWebNotification.class);

		InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
		OutputStream output = new ByteArrayOutputStream();

		try {
			RequestHandler.lambdaFunction(input, output, null);

			ByteArrayOutputStream baos = (ByteArrayOutputStream) output;
			String jsonResponse = new String(baos.toByteArray(), "UTF-8");

			webPush = gsonTester.fromJson(jsonResponse, ResponseWebNotification.class);

		} catch (BadRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternalErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (webPush == null) {
			System.out.println("OPS!! Troubles ");
		} else {
			System.out.println("webPush.getRequest_notification_id: " + webPush.getRequest_notification_id());
			System.out.println("webPush.getNum_failure: " + webPush.getNum_failure());
			if (webPush.getResult() != null && !webPush.getResult().isEmpty()) {
				webPush.getResult().forEach(t -> System.out
						.println("getRegistration_id: " + t.getRegistration_id() + " - getError: " + t.getError()));
			} else {
				System.out.println("(webPush.getResult() == null");
			}
		}

		long t2 = System.currentTimeMillis();

		System.out.printf("#### TesterRequestHandler time: %d ms\n", (t2 - t1));
	}
}
