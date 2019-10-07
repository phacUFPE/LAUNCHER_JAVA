package download;
import java.io.*;
import java.net.*;

public class Download {
	public static Runnable DownloadFile(String fURL, String pathDesk, String fName) {
		
		long totalBytes = 0;
		
		try {
			HttpURLConnection conn = (HttpURLConnection)(new URL(fURL)).openConnection();
			conn.setRequestMethod("HEAD");
			conn.getInputStream();
			totalBytes = (long) Math.ceil(conn.getContentLength() / 1024);
			conn.getInputStream().close();
		} catch (Exception e) {
			ErrorLog.saveError(e);
		}
		
		try (BufferedInputStream in = new BufferedInputStream(new URL(fURL).openStream());
				  FileOutputStream fileOutputStream = new FileOutputStream(String.format("%s\\%s", MainWindow.rootDir, pathDesk + fName))) {
				    byte dataBuffer[] = new byte[1024];
				    long rBytesTotal = 0;
				    int bytesReceived = 0;
				    int bytesRead;
				    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
				    	
				    	rBytesTotal += bytesRead;
				    	
				        fileOutputStream.write(dataBuffer, 0, bytesRead);
				        
				        bytesReceived = (int) Math.round((double) rBytesTotal / 1024);
				        
				        double div = (double)bytesReceived/(double)totalBytes; 
						int percent = (int) Math.round((div * 100) * (MainWindow.pgWidth * 0.01));
				        
				        MainWindow.lblBytes.setText(String.format("%d / %d KB", bytesReceived, totalBytes));
				        MainWindow.lblProgressFileFull.setSize(percent, MainWindow.lblProgressFileFull.getHeight());
				        
				    }
				} catch (Exception e) {
				    ErrorLog.saveError(e);
				}
		return null;
	}
}
