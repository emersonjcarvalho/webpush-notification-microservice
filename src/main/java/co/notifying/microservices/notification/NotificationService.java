package co.notifying.microservices.notification;

import co.notifying.microservices.notification.domain.RequestWebNotification;
import co.notifying.microservices.notification.domain.ResponseWebNotification;

/**
 * Define a contract to be implement a Request to WebPush Notification using a
 * specific Provider.
 *
 * Examples Providers FCM(Firebase Cloud Messaging), Amazon SNS(Simple
 * Notification Service)
 *
 */
public interface NotificationService {

	/**
	 * Performace a Request send a Web Notification to a target(s)
	 * 
	 * @param requestWebNotification
	 * @return ResponseWebNotification
	 * @throws Exception
	 */
	// TODO Replace throws Exception for Exception Strategy
	public ResponseWebNotification webPush(RequestWebNotification requestWebNotification) throws Exception;

}
