package util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ThreadLocals {
	private static ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();
	private static ThreadLocal<HttpServletResponse> responseHolder = new ThreadLocal<>();
	
	public static HttpServletResponse getResponse() {
		return responseHolder.get();
	}
	
	public static HttpServletRequest getRequest() {
		return requestHolder.get();
	}

	public static void init(HttpServletRequest request, HttpServletResponse response) {
		requestHolder.set(request);
		responseHolder.set(response);
	}

	public static void clear() {
		requestHolder.remove();
		responseHolder.remove();
	}
}
