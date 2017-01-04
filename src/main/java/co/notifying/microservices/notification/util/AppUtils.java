package co.notifying.microservices.notification.util;

import java.util.Optional;

import co.notifying.microservices.notification.exception.InternalErrorException;

public class AppUtils {

	public static String getEnvVariables(String key) throws InternalErrorException {
		String value = System.getenv(key);

		return Optional.ofNullable(value)
				.orElseThrow(() -> new InternalErrorException("Missing FCM_SERVER_KEY environment variable"));
	}
}
