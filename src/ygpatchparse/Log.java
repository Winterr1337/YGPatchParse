package ygpatchparse;


import org.tinylog.Logger;

public class Log {



	public static void Info(String message) {
		Logger.info(message);

	}
	public static void Error(String message) {
		Logger.error(message);

	}
	public static void Debug(Object object) {
		Logger.debug(object);
	}
	public static void Warn(String message) {
		Logger.warn(message);

	}
}