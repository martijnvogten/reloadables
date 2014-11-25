package examples;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;

public class WebApp {
	public static void main(String[] args) throws Exception {
		Handler webScriptHandler = new WebRequestHandler();
		Server server = new Server(8080);
		server.setHandler(webScriptHandler);
		server.setStopAtShutdown(true);
		server.start();
		server.join();
	}
}
