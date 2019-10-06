package download;

import java.io.FileInputStream;
import java.net.URL;
import java.security.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hash {
	
	public boolean compareHash(String h1, String h2) {
		return (h1 == h2);
	}
	
	public static Integer getTotalFiles() {
		
		try {
			
			URL url = new URL(MainWindow.webAddress + MainWindow.hashList);
			Scanner s = new Scanner(url.openStream());
		   
			String text = "";
		   
			while (s.hasNextLine()) {
				text += s.nextLine() + "\n";
			}
			s.close();
		   
			Pattern pat = Pattern.compile("totalfiles=(.*)");
			Matcher mat = pat.matcher(text);
			if (mat.find()) {
				return Integer.parseInt(mat.group(1));
			}
		} catch(Exception e) {
			ErrorLog.saveError(e);
		}
		
		return 0;
	}
	
	public static HashMap<String, String> loadHash() {	
		
		HashMap<String, String> hList = new HashMap<String, String>();
		
		try {
			
			URL url = new URL(MainWindow.webAddress + MainWindow.hashList);
			Scanner s = new Scanner(url.openStream());
		   
			String text = "";
		   
			while (s.hasNextLine()) {
				text += s.nextLine() + "\n";
			}
			s.close();
		   
			Pattern pat = Pattern.compile("file=\"(.*)\", hash=\"(.*)\";");
			Matcher mat = pat.matcher(text);
			while (mat.find()) {
				hList.put(mat.group(1), mat.group(2));
			}
		} catch(Exception e) {
			ErrorLog.saveError(e);
		}
		
		return hList;
	}
	
	public String getHash(String path) {
		
		StringBuilder sb = new StringBuilder();
		
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
		
			//Get file input stream for reading the file content
	    	FileInputStream fis = new FileInputStream(path);
	     
	    	//Create byte array to read data in chunks
	    	byte[] byteArray = new byte[1024];
	    	int bytesCount = 0;
	      
	    	//Read file data and update in message digest
	    	while ((bytesCount = fis.read(byteArray)) != -1) {
	    		digest.update(byteArray, 0, bytesCount);
	    	};
	     
	    	//close the stream; We don't need it now.
	    	fis.close();
	     
	    	//Get the hash's bytes
	    	byte[] bytes = digest.digest();
	     
	    	//This bytes[] has bytes in decimal format;
	    	//Convert it to hexadecimal format
	    	for(int i=0; i< bytes.length ;i++)
	    	{
	    		sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	    	}
	     
	    	//return complete hash
		} catch (Exception e) {
			ErrorLog.saveError(e);
		}
		
	    return sb.toString();
	}
}
