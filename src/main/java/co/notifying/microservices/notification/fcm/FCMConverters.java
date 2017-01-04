package co.notifying.microservices.notification.fcm;

import java.util.function.Function;

import co.notifying.microservices.notification.domain.ResultResponse;

public class FCMConverters {

	public static Function<FCMResultResponseDTO, ResultResponse> convertResultResponse() {
		return new Function<FCMResultResponseDTO, ResultResponse>() {
			@Override
			public ResultResponse apply(FCMResultResponseDTO dto) {
				return new ResultResponse(dto.getRegistration_id(), dto.getError());
			}
		};
	}
}
