package co.notifying.microservices.notification.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.apache.http.client.methods.HttpPost;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import co.notifying.microservices.notification.domain.ResponseWebNotification;

public class JsonUtils {

	private static Gson gsonInstance = null;

	// TODO need test peformance w/ Singleton
	public static Gson gsonSingleton() {
		if (gsonInstance == null) {
			gsonInstance = getGsonFromBuilder();
		}
		return gsonInstance;
	}

	public static Gson getGsonFromBuilder() {
		return new GsonBuilder()
				// .setDateFormat(DateFormat.LONG)
				// .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
				// .setPrettyPrinting()
				.create();
	}

	// TODO Future Improvement: Pass as Paramenter the Object Config(Headers
	// options)
	public static HttpPost getHttpPostFromBuilder(String endPoint, String AuthKey) {
		HttpPost httpPost = new HttpPost(endPoint);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setHeader("Authorization", String.format("key=%s", AuthKey));
		return httpPost;
	}

	public static <T> Object inputStreamToObject(final Gson gson, final InputStream inputStream, Class<T> type) {

		InputStreamReader streamReader = null;
		try {
			streamReader = new InputStreamReader(inputStream);

			return gson.fromJson(streamReader, type);
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (streamReader != null) {
				try {
					streamReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static void writeResponseJson(OutputStream output, Gson gson,
			ResponseWebNotification responseWebNotification) {

		String data = null;

		try {
			data = gson.toJson(responseWebNotification, ResponseWebNotification.class);
			Charset charset = Charset.defaultCharset();
			output.write(data.getBytes(charset));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
