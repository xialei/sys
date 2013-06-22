package com.aug3.sys.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * A delegate for helping with overriding Object's base methods.
 */
public final class ObjectDelegate {

	/**
	 * Callback helper for classes overriding {@link Object#toString()}.
	 * 
	 * Methods invoked on class overriding toString() are encapsulated by
	 * {@link BeanMethodNameType} enumeration.
	 * 
	 * Do not use this helper for overriding toString for an enumeration.
	 */
	public static String toString(Object objectToString) {
		if (objectToString == null) {
			return "null";
		}

		String className = objectToString.getClass().getSimpleName();
		StringBuffer returnString = new StringBuffer("[").append(className)
				.append(" Begin]");

		Method[] methods = objectToString.getClass().getDeclaredMethods();
		String methodName = null;
		int counter = 1;
		for (Method method : methods) {
			methodName = method.getName().toLowerCase();
			try {
				if (methodName.length() > 2) {
					Object attributeValue = null;
					if (methodName.startsWith(BeanMethodNameType.GET
							.getMethodNameType())
							&& method.getParameterTypes().length == 0) {
						attributeValue = method.invoke(objectToString,
								(Object[]) null);
						if (!objectToString.equals(attributeValue)) {
							returnString
									.append(methodName
											.substring(BeanMethodNameType.GET
													.getMethodNameType()
													.length())).append("=")
									.append(attributeValue);
							if (counter++ < methods.length) {
								returnString.append(", ");
							}
						}
						continue;
					}

					if (methodName.startsWith(BeanMethodNameType.IS
							.getMethodNameType())) {
						attributeValue = method.invoke(objectToString,
								(Object[]) null);
						if (!objectToString.equals(attributeValue)) {
							returnString
									.append(methodName
											.substring(BeanMethodNameType.IS
													.getMethodNameType()
													.length())).append("=")
									.append(attributeValue).append(", ");
							if (counter++ < methods.length) {
								returnString.append(", ");
							}
						}
						continue;
					}
				}
			} catch (InvocationTargetException invocationTargetException) {
				throw new RuntimeException(invocationTargetException);
			} catch (IllegalAccessException illegalAccessException) {
				throw new RuntimeException(illegalAccessException);
			}
		}

		returnString.append("[").append(className).append(" End]");
		return returnString.toString();
	}

	/**
	 * Deep copy given cloneCandidate object.
	 */
	public static Object clone(Object cloneCandidate) {
		ObjectOutputStream objectOutputStream = null;
		ObjectInputStream objectInputStream = null;
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(cloneCandidate);
			objectOutputStream.flush();
			byte[] objectBytes = byteArrayOutputStream.toByteArray();

			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
					objectBytes);
			objectInputStream = new ObjectInputStream(byteArrayInputStream);
			return objectInputStream.readObject();
		} catch (Exception exception) {
			throw new RuntimeException(exception.getMessage(), exception);
		} finally {
			try {
				if (objectOutputStream != null) {
					objectOutputStream.close();
				}
				if (objectInputStream != null) {
					objectInputStream.close();
				}
			} catch (IOException ioException) {
				// Do Nothing!
			}
		}
	}

	public static enum BeanMethodNameType {
		GET("get"), IS("is");

		private final String methodNameType;

		private BeanMethodNameType(final String methodNameType) {
			this.methodNameType = methodNameType;
		}

		// Bean methods
		public String getMethodNameType() {
			return methodNameType;
		}

		public static Set<BeanMethodNameType> getAllMethodNameTypes() {
			return Collections.unmodifiableSet(EnumSet.of(GET, IS));
		}
	}

	/*
	 * Prevent instance escape at all costs.
	 */
	private ObjectDelegate() {
	}

}