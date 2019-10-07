package download;
import java.io.*;
import java.net.*;

public class Download {
	public static Runnable DownloadFile(String fURL, String pathDesk, String fName) {
		try (BufferedInputStream in = new BufferedInputStream(new URL(fURL).openStream());
				  FileOutputStream fileOutputStream = new FileOutputStream(String.format("%s\\%s", pathDesk, fName))) {
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
