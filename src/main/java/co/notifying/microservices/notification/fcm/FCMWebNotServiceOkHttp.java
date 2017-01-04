//package co.notifying.microservices.notification.fcm;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.ResponseHandler;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//import co.notifying.microservices.notification.NotificationService;
//import co.notifying.microservices.notification.RequestHandler;
//import co.notifying.microservices.notification.domain.RequestWebNotification;
//import co.notifying.microservices.notification.domain.ResponseWebNotification;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//public class FCMWebNotServiceOkHttp implements NotificationService {
//
//	private static final String ENCONDING = "UTF-8";
//	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//
//	@Override
//	public ResponseWebNotification webPush(RequestWebNotification notification) {
//
//		ResponseWebNotification responseWebNotification = null;
//
//		long t1 = System.currentTimeMillis();
//		FCMRequestDTO fcmRequest = setFCMRequest(notification);
//		long t2 = System.currentTimeMillis();
//		System.out.printf("1 - setFCMRequest(notification): %d ms\n", (t2 - t1));
//
//		long ta1 = System.currentTimeMillis();
//		OkHttpClient okHttpClient = new OkHttpClient();
//
//		long ta2 = System.currentTimeMillis();
//		System.out.printf("2.1 - new OkHttpClient(): %d ms\n", (ta2 - ta1));
//
//		Gson gson = getGsonFromBuilder();
//
//		long t5 = System.currentTimeMillis();
//		String json = fcmRequest.asJson(gson);
//		long t6 = System.currentTimeMillis();
//		System.out.printf("3.0 - fcmRequest.asJson: %d ms\n", (t6 - t5));
//
//		long t51 = System.currentTimeMillis();
//		RequestBody body = RequestBody.create(JSON, json);
//		long t52 = System.currentTimeMillis();
//		System.out.printf("3.1 - RequestBody.create(JSON, json): %d ms\n", (t52 - t51));
//
//		long tb1 = System.currentTimeMillis();
//		Request okHttpRequest = new Request.Builder()
//				.addHeader("Authorization", String.format("key=%s", RequestHandler.FCM_SERVER_KEY))
//				.url(RequestHandler.API_PROVIDER_URL).post(body).build();
//		// httpPost.setHeader("Accept", "application/json");
//		long tb2 = System.currentTimeMillis();
//		System.out.printf("3.2 - okHttpRequest new Request.Builder: %d ms\n", (tb2 - tb1));
//
//		try {
//
//			long t9 = System.currentTimeMillis();
//
//			Response okHttpResponse = okHttpClient.newCall(okHttpRequest).execute();
//
//			long t10 = System.currentTimeMillis();
//			System.out.printf("4 - okHttpClient.newCall(okHttpRequest): %d ms\n", (t10 - t9));
//
//			long tc1 = System.currentTimeMillis();
//			FCMResponseDTO fcmResponse = extractEntityFromHttpResponse(gson, okHttpResponse);
//			long tc2 = System.currentTimeMillis();
//			System.out.printf("5 - extractEntityFromHttpResponse(gson, okHttpResponse): %d ms\n", (tc2 - tc1));
//
//			// TODO Save Log in a DB Collection
//			long t11 = System.currentTimeMillis();
//			responseWebNotification = getResponseWebNotification(fcmResponse);
//			long t12 = System.currentTimeMillis();
//			System.out.printf("6 - getResponseWebNotification(fcmResponse): %d ms\n", (t12 - t11));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//		}
//		return responseWebNotification;
//	}
//
//	public ResponseWebNotification getResponseWebNotification(FCMResponseDTO fcmResponse) {
//
//		// TODO Define stratefy to ResponseWebNotification Service
//
//		ResponseWebNotification responseWebNotification = null;
//
//		boolean success = Boolean.valueOf(fcmResponse.getSuccess().toString());
//
//		String message = (fcmResponse.getMulticast_id() != null) ? fcmResponse.getMulticast_id().toString()
//				: " Multicast_id == null";
//
//		responseWebNotification = new ResponseWebNotification(success, message);
//		return responseWebNotification;
//	}
//
//	private StringEntity getJsonEntity(FCMRequestDTO fcmRequest, Gson gson) {
//		StringEntity jsonEntity = null;
//
//		try {
//			jsonEntity = new StringEntity(fcmRequest.asJson(gson));
//		} catch (UnsupportedEncodingException uEx) {
//			// TODO Auto-generated catch block
//			uEx.printStackTrace();
//		}
//		return jsonEntity;
//	}
//
//	private FCMRequestDTO setFCMRequest(RequestWebNotification notification) {
//		FCMRequestDTO request = new FCMRequestDTO(notification.getTitle(), notification.getBody(),
//				notification.getTargets());
//
//		request.click_action(notification.getUrl_click_action());
//		request.icon(notification.getUrl_icon());
//		return request;
//	}
//
//	private HttpPost getHttpPostFromBuilder(String endPoint, String AuthKey) {
//		HttpPost httpPost = new HttpPost(endPoint);
//		httpPost.setHeader("Accept", "application/json");
//		httpPost.setHeader("Content-type", "application/json");
//		httpPost.setHeader("Authorization", String.format("key=%s", AuthKey));
//		return httpPost;
//	}
//
//	public Gson getGsonFromBuilder() {
//		return new GsonBuilder()
//				// .setDateFormat(DateFormat.LONG)
//				// .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
//				// .setPrettyPrinting()
//				.create();
//	}
//
//	private FCMResponseDTO extractEntityFromHttpResponse(Gson gson, Response okHttpResponse) throws IOException {
//
//		long tr1 = System.currentTimeMillis();
//
//		FCMResponseDTO fcmResponseDTO = null;
//		InputStream inputStream = null;
//
//		InputStreamReader reader = null;
//		try {
//			inputStream = okHttpResponse.body().byteStream();
//			reader = new InputStreamReader(inputStream);
//			fcmResponseDTO = gson.fromJson(reader, FCMResponseDTO.class);
//		} catch (UnsupportedOperationException uEx) {
//			// TODO Auto-generated catch block
//			uEx.printStackTrace();
//		} finally {
//			if (inputStream != null) {
//				inputStream.close();
//			}
//			if (reader != null) {
//				reader.close();
//			}
//		}
//
//		long tr2 = System.currentTimeMillis();
//		System.out.printf("4.2 - handleResponse-extractEntityFromHttpResponse: %d ms\n", (tr2 - tr1));
//
//		return fcmResponseDTO;
//	}
//}
