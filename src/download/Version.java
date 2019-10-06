package download;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.*;

public class Version {
	
	public static boolean compareSL(String sVer, String lVer) {
		if (sVer == null || lVer == null) { return false; }
		return (sVer.compareTo(lVer) == 0);
	}
	
	public static String getServer(String sMatch) {
		MatchResult result = null;
		try {
		   URL url = new URL(MainWindow.webAddress + MainWindow.fileVersion);
		   Scanner s = new Scanner(url.openStream());
		   
		   String text = "";
		   
		   while (s.hasNextLine()) {
			   text += s.nextLine() + "\n";
		   }
		   s.close();
		   
		   Pattern pat = Pattern.compile(sMatch + "=(.*)");
		   Matcher mat = pat.matcher(text);
		   if (mat.find()) {
			   result = mat.toMatchResult();
		   }
		}
		catch(Exception e) {
		   ErrorLog.saveError(e);
		}
		if (result == null) { return "0"; }
		return result.group(1);
	}
	
	public static void writeLocal() {
		try {
			File config = new File(String.format("%s\\%s", MainWindow.rootDir, MainWindow.fileConfig));
			
			BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(config));
			
			bufferWriter.write("[Geral]");
			bufferWriter.newLine();
			bufferWriter.write(String.format("engine=%s", MainWindow.gameEngine));
			bufferWriter.newLine();
			bufferWriter.write(String.format("version=%s", MainWindow.localVer));
			bufferWriter.newLine();
			bufferWriter.write(String.format("language=%s", MainWindow.launcherLanguage));
			bufferWriter.newLine();
			
			bufferWriter.close();
			
		} catch (Exception e) {
			ErrorLog.saveError(e);
		}
	}
	
	public static String getLocal() {
		
		if (!new File(String.format("%s\\%s", MainWindow.rootDir, MainWindow.fileConfig)).isFile()) { writeLocal(); return "0"; }
		
		MatchResult result = null;
		
	    try {
	    	File config = new File(String.format("%s\\%s", MainWindow.rootDir, MainWindow.fileConfig));
	    	
	        Scanner s = new Scanner(config);

	        String text = "";
			   
			while (s.hasNextLine()) {
				text += s.nextLine() + "\n";
			}		
			s.close();
			
			Pattern pat = Pattern.compile("version=(.*)");
			Matcher mat = pat.matcher(text);
			if (mat.find()) {
				result = mat.toMatchResult();
			}
			
	    } catch (Exception e) {
	    	ErrorLog.saveError(e);
	    }
		if (result == null) { return "0"; }
		return result.group(1);
		
	}
}
