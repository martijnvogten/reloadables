package util;

import groovy.xml.MarkupBuilder;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.ThreadLocals;

public class Globals {
	
	private static String ATTR_MARKUPBUILDER = "globals_markupbuilder";
	
	public static HttpServletRequest getRequest() {
		return ThreadLocals.getRequest();
	}
	
	public static HttpServletResponse getResponse() {
		return ThreadLocals.getResponse();
	}
	
	public static Writer getOut() {
		try {
			return getResponse().getWriter();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static MarkupBuilder getBuilder() {
		return (MarkupBuilder) getRequest().getAttribute(ATTR_MARKUPBUILDER);
	}
	
	public static void setBuilder(MarkupBuilder builder) {
		getRequest().setAttribute(ATTR_MARKUPBUILDER, builder);
	}
	
}
