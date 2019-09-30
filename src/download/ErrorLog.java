package download;

import java.io.*;
import java.util.*;

public class ErrorLog {
	
	private static String logName = "log";
	private static String logExt = ".txt";
	
	public static void saveError(Exception e)
	{
		try {
			Date date = java.util.Calendar.getInstance().getTime();
			String country = System.getProperty("user.country");
	
			String os = System.getProperty("os.name");
			String osArch = System.getProperty("os.arch");
			String osVer = System.getProperty("os.version");
	
			String javaVer = System.getProperty("java.version");
			
			StackTraceElement[] traceElements = e.getStackTrace();
			
			BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(logName+logExt, true));
			bufferWriter.append("[");
			bufferWriter.newLine();
			bufferWriter.append("Date: " + date + " - " + country);
			bufferWriter.newLine();
			bufferWriter.append("OS: " + os);
			bufferWriter.newLine();
			bufferWriter.append("OS Architecture: " + osArch);
			bufferWriter.newLine();
			bufferWriter.append("OS Version: " + osVer);
			bufferWriter.newLine();
			bufferWriter.append("Java Version: " + javaVer);
			bufferWriter.newLine();
			bufferWriter.append("Message: " + e.getMessage());
			bufferWriter.newLine();
			for (StackTraceElement element : traceElements)
			{
				bufferWriter.append("Class: " + element.getClassName());
				bufferWriter.newLine();
				bufferWriter.append("File: " + element.getFileName());
				bufferWriter.newLine();
				bufferWriter.append("Line: " + element.getLineNumber());
				bufferWriter.newLine();
				bufferWriter.append("Method: " + element.getMethodName());
				bufferWriter.newLine();
				bufferWriter.newLine();
			}
			bufferWriter.append("]");
			bufferWriter.newLine();
			bufferWriter.newLine();
			
			bufferWriter.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
