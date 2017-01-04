package co.notifying.microservices.notification.fcm;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;

import co.notifying.microservices.notification.NotificationService;
import co.notifying.microservices.notification.domain.RequestWebNotification;
import co.notifying.microservices.notification.domain.ResponseWebNotification;
import co.notifying.microservices.notification.domain.ResultResponse;
import co.notifying.microservices.notification.exception.InternalErrorException;
import co.notifying.microservices.notification.util.AppUtils;
import co.notifying.microservices.notification.util.Constants;
import co.notifying.microservices.notification.util.JsonUtils;

public class FCMWebNotificationService implements NotificationService {

	@Override
	public ResponseWebNotification webPush(RequestWebNotification notification) {

		ResponseWebNotification responseWebNotification = null;

		FCMRequestDTO fcmRequest = setFCMRequest(notification);

		CloseableHttpClient httpclient = HttpClients.createDefault();

		HttpPost httpPost = null;
		try {
			httpPost = JsonUtils.getHttpPostFromBuilder(Constants.FCM_ENDPOINT,
					AppUtils.getEnvVariables(Constants.ENV_PROVIDER_SERVER_KEY));
		} catch (InternalErrorException e1) {
			// TODO Problems w/ Env Variables
			e1.printStackTrace();
		}

		Gson gson = JsonUtils.gsonSingleton();
		StringEntity jsonEntity = getJsonEntity(fcmRequest, gson);
		httpPost.setEntity(jsonEntity);

		try {
			ResponseHandler<FCMResponseDTO> responseHandler = responseHandler(gson);

			FCMResponseDTO fcmResponse = httpclient.execute(httpPost, responseHandler);

			// TODO Save Log in a DB Collection
			responseWebNotification = getResponseWebNotification(fcmResponse);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return responseWebNotification;
	}

	private ResponseHandler<FCMResponseDTO> responseHandler(Gson gson) {
		return new ResponseHandler<FCMResponseDTO>() {
			@Override
			public FCMResponseDTO handleResponse(HttpResponse response) throws ClientProtocolException, IOException {

				int status = response.getStatusLine().getStatusCode();

				if (status >= 200 && status < 300) {
					return extractEntityFromHttpResponse(gson, response);
				} else {
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			}
		};
	}

	public ResponseWebNotification getResponseWebNotification(FCMResponseDTO fcmResponse) {
		// TODO Define strategy to ResponseWebNotification Service
		// To know which client was successfully notified.. Need keep original..
		// target request list and compare w/ FCMResponseDTO.result

		List<ResultResponse> resultResponses = null;

		if (fcmResponse.getResult() != null && !fcmResponse.getResult().isEmpty()) {

			resultResponses = fcmResponse.getResult().stream().map(convertResultResponse())
					.collect(Collectors.<ResultResponse>toList());
		}
		return new ResponseWebNotification(fcmResponse.getMulticast_id(), fcmResponse.getFailure(), resultResponses);
	}

	public Function<FCMResultResponseDTO, ResultResponse> convertResultResponse() {
		return new Function<FCMResultResponseDTO, ResultResponse>() {
			@Override
			public ResultResponse apply(FCMResultResponseDTO dto) {
				return new ResultResponse(dto.getRegistration_id(), dto.getError());
			}
		};
	}

	private StringEntity getJsonEntity(FCMRequestDTO fcmRequest, Gson gson) {
		StringEntity jsonEntity = null;

		try {
			jsonEntity = new StringEntity(fcmRequest.asJson(gson));
		} catch (UnsupportedEncodingException uEx) {
			// TODO Auto-generated catch block
			uEx.printStackTrace();
		}
		return jsonEntity;
	}

	private FCMRequestDTO setFCMRequest(RequestWebNotification notification) {
		FCMRequestDTO request = new FCMRequestDTO(notification.getTitle(), notification.getBody(),
				notification.getTargets());

		request.click_action(notification.getUrl_click_action());
		request.icon(notification.getUrl_icon());
		return request;
	}

	private FCMResponseDTO extractEntityFromHttpResponse(Gson gson, HttpResponse response) throws IOException {

		FCMResponseDTO fcmResponseDTO = null;
		InputStream inputStream = null;

		try {
			inputStream = response.getEntity().getContent();
			fcmResponseDTO = (FCMResponseDTO) JsonUtils.inputStreamToObject(gson, inputStream, FCMResponseDTO.class);
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return fcmResponseDTO;
	}
}
