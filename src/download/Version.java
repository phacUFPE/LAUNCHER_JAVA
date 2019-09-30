package download;

import java.io.File;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.*;

public class Version {
	
	public static boolean compareSL(String sVer, String lVer)
	{
		return (sVer.compareTo(lVer) == 0);
	}
	
	public static String getServer(String webAddr, String fName, String sMatch)
	{
		MatchResult result = null;
		try {
		   URL url = new URL(webAddr + fName);
		   Scanner s = new Scanner(url.openStream());
		   
		   String text = "";
		   
		   while (s.hasNextLine())
		   {
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
		if (result == null) { return null; }
		return result.group(1);
	}
	
	public static String getLocal(String dir, String fName)
	{
		MatchResult result = null;
		
	    try {
	    	File config = new File(String.format("%s\\%s", dir, fName));
	    	
	        Scanner s = new Scanner(config);

	        String text = "";
			   
			while (s.hasNextLine())
			{
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
		if (result == null) { return null; }
		return result.group(1);
		
	}
}
