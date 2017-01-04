package co.notifying.microservices.notification;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.gson.Gson;

import co.notifying.microservices.notification.domain.RequestWebNotification;
import co.notifying.microservices.notification.domain.ResponseWebNotification;
import co.notifying.microservices.notification.exception.BadRequestException;
import co.notifying.microservices.notification.exception.InternalErrorException;
import co.notifying.microservices.notification.fcm.FCMWebNotificationService;
import co.notifying.microservices.notification.util.JsonUtils;

public class RequestHandler {

	public static void lambdaFunction(InputStream request, OutputStream response, Context context)
			throws BadRequestException, InternalErrorException {

		NotificationService notService = new FCMWebNotificationService();
		ResponseWebNotification responseWebNotification = null;

		Gson gson = JsonUtils.gsonSingleton();

		//LambdaLogger logger = context.getLogger();

		RequestWebNotification requestWebNotification = null;

		requestWebNotification = (RequestWebNotification) JsonUtils.inputStreamToObject(gson, request,
				RequestWebNotification.class);

		// TODO Create VALIDATE to Required Field in RequestWebNotification
		// Throw specifics Exceptions

		try {
			responseWebNotification = notService.webPush(requestWebNotification);
		} catch (Exception e1) {
			// TODO Auto-generated catch block [Exception]
			e1.printStackTrace();
		}

		JsonUtils.writeResponseJson(response, gson, responseWebNotification);

		/**
		 * Finishing / Release Resources
		 */
		try {
			if (request != null) {
				request.close();
			}

			if (response != null) {
				response.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
