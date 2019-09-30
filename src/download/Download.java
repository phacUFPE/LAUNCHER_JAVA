package download;
import java.io.*;
import java.net.*;

public class Download {
	public static Runnable DownloadFile(String FILE_URL, String FILE_NAME) {
		try (BufferedInputStream in = new BufferedInputStream(new URL(FILE_URL).openStream());
				  FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME)) {
				    byte dataBuffer[] = new byte[1024];
				    int bytesRead;
				    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
				        fileOutputStream.write(dataBuffer, 0, bytesRead);
				    }
				} catch (Exception e) {
				    ErrorLog.saveError(e);
				}
		return null;
	}
}
