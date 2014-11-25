package webapp;

import groovy.xml.MarkupBuilder;

import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import templates.home;
import util.Globals;
import util.ThreadLocals;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class Main {
	
	public static void main(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			ThreadLocals.init(request, response);
			handleRequest(request, response);
		} finally {
			ThreadLocals.clear();
		}
	}

	private static void handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		boolean handled = false;
		
		if (!request.getRequestURI().endsWith("/")) {
			// Check for public resources in /public
			String resourceName = "/public" + request.getRequestURI();
			URL publicResource = Main.class.getResource(resourceName);
			if (publicResource != null) {
				serveResource(publicResource, response);
				handled = true;
			}
		} 
		
		if (!handled && "/".equals(request.getRequestURI())) {
			response.setContentType("text/html");
			response.setCharacterEncoding(Charsets.UTF_8.name());
			MarkupBuilder builder = new MarkupBuilder(response.getWriter());
			builder.setDoubleQuotes(true);
			builder.setOmitNullAttributes(true);
			Globals.setBuilder(builder);
			new home().run();
			handled = true;
		} 
		
		if (!handled) {
			response.sendError(404);
		}
	}

	private static void serveResource(URL publicResource, HttpServletResponse response) throws IOException {
		if (publicResource.toString().endsWith(".css")) {
			response.setContentType("text/css");
			response.setCharacterEncoding(Charsets.UTF_8.name());
		}
		if (publicResource.toString().endsWith(".js")) {
			response.setContentType("text/javascript");
			response.setCharacterEncoding(Charsets.UTF_8.name());
		}
		Resources.copy(publicResource, response.getOutputStream());
		response.flushBuffer();
	}
}
