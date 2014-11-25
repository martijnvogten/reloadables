package reloadables;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Date;
import java.util.List;

import reloadables.util.Files;

import com.google.common.collect.Lists;

public class Reloadable {

	private List<File> classFolders;
	
	private ClassLoader classLoader;
	private Date lastReload = new Date(0L);
	
	public Reloadable(List<File> classFolders) {
		this.classFolders = classFolders;
	}

	public void invoke(String className, String methodName, Class<?>[] paramTypes, Object... params) throws Exception {
		// blocks
		maybeReload();
		
		Class<?> entryPoint = classLoader.loadClass(className);
		Method theMethod = entryPoint.getMethod(methodName, paramTypes);
		theMethod.invoke(null, params);
	}

	private synchronized void maybeReload() throws MalformedURLException {
		if (classLoader != null && lastReload.getTime() > Files.lastModified(classFolders)) {
			return;
		}
		
		lastReload = new Date();
		List<URL> extraClassPath = Lists.newArrayList();
		for(File classFolder : classFolders) {
			extraClassPath.add(classFolder.toURI().toURL());
		}
		
		classLoader = new URLClassLoader(
				(URL[]) extraClassPath.toArray(new URL[extraClassPath.size()]), 
				Thread.currentThread().getContextClassLoader()
			);
	}
	
}
