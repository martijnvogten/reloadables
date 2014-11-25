package reloadables.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class Files {
	private interface FileVisitor {
		void visit(File f);
	}

	public static List<File> findFiles(List<File> path, final Pattern p) {
		final List<File> result = new ArrayList<>();
		final Set<String> relativePaths = new HashSet<>();

		try {
			for (File parent : path) {
				final int parentPathLength = parent.getCanonicalPath().length();
				iterateFiles(Collections.singletonList(parent), new FileVisitor() {
					@Override
					public void visit(File f) {
						if (f.isFile() && p.matcher(f.getName()).matches()) {
							String relativePath = f.getAbsolutePath().substring(parentPathLength);
							if (!relativePaths.contains(relativePath)) {
								result.add(f);
							}
							relativePaths.add(relativePath);
						}
					}
				});
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	public static long lastModified(List<File> files) {
		final long[] last = new long[] { 0L };
		FileVisitor visitor = new FileVisitor() {
			public void visit(File f) {
				if (f.lastModified() > last[0]) {
					last[0] = f.lastModified();
				}
			}
		};
		iterateFiles(files, visitor);
		return last[0];
	}

	public static void iterateFiles(List<File> files, FileVisitor v) {
		for (File f : files) {
			if (f.getName().equals(".") || f.getName().equals("..")) {
				continue;
			}
			v.visit(f);
			if (f.isDirectory()) {
				iterateFiles(Arrays.asList(f.listFiles()), v);
			}
		}
	}

	public static void assertDirectory(File dir) {
		try {
			com.google.common.io.Files.createParentDirs(new File(dir, "x"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
