package com.irdstudio.smcenter.agent.utils;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Objects;

import sun.misc.Unsafe;

public class ClassLoaderUtils {
	
	public static URL[] getUrls(ClassLoader classLoader) {
		if ((classLoader instanceof URLClassLoader)) {
			return ((URLClassLoader) classLoader).getURLs();
		}
		if (classLoader.getClass().getName().startsWith("jdk.internal.loader.ClassLoaders$")) {
			try {
				Field field = Unsafe.class.getDeclaredField("theUnsafe");
				boolean fieldAccessible = field.isAccessible();
				if (!fieldAccessible) {
				    field.setAccessible(true);
				}
				Unsafe unsafe = null;
				try {
				    unsafe = (Unsafe) field.get(null);
				} catch (Exception e) {
				    
				} finally {
				    field.setAccessible(fieldAccessible);
				}
				
				if (Objects.isNull(unsafe)) {
				    return null;
				}

				Field ucpField = classLoader.getClass().getDeclaredField("ucp");
				long ucpFieldOffset = unsafe.objectFieldOffset(ucpField);
				Object ucpObject = unsafe.getObject(classLoader, ucpFieldOffset);

				Field pathField = ucpField.getType().getDeclaredField("path");
				long pathFieldOffset = unsafe.objectFieldOffset(pathField);
				@SuppressWarnings("unchecked")
				ArrayList<URL> path = (ArrayList<URL>) unsafe.getObject(ucpObject, pathFieldOffset);

				return (URL[]) path.toArray(new URL[path.size()]);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
}
