package examples;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reloaded.Reloadable;

public class WebRequestHandler extends AbstractHandler {

	static Logger logger = LoggerFactory.getLogger(WebRequestHandler.class);

	private final Reloadable reloadable = new Reloadable(Arrays.asList(new File("reloadables/webapp/bin")));

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
			reloadable.invoke("webapp.Main", "main", new Class<?>[] {HttpServletRequest.class, HttpServletResponse.class},request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	@Override
	public void destroy() {

	}

	
}
