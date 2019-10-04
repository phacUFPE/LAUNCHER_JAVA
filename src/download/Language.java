package download;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Language {
	public static HashMap<String, HashMap<String, String>> loadLanguages() {

		HashMap<String, String> hMapLang = new HashMap<String, String>();
		HashMap<String, HashMap<String, String>> hMapLoc = new HashMap<String, HashMap<String, String>>();
		
		try {
			
			final File folder = new File(String.format("%s//%s", MainWindow.rootDir, MainWindow.langFolder));
			
			for (final File file : folder.listFiles()) {
				
				String tempName = file.getName();
				
				if (file.isFile() && tempName.contains(MainWindow.extLang)) {
			
					File languages = new File(String.format("%s//%s//%s", MainWindow.rootDir, MainWindow.langFolder, file.getName()));
			
					Scanner s = new Scanner(languages);
			
					String text = "";
			
					while (s.hasNextLine()) {
						text += s.nextLine() + "\n";
					}
					s.close();
			
					Pattern pat = Pattern.compile("(.*)=\"(.*)\";");
					Matcher mat = pat.matcher(text);
					
					while (mat.find()) {
						hMapLang.put(mat.group(1), mat.group(2));
					}
					
					pat = Pattern.compile("\\|(.*)\\|;");
					mat = pat.matcher(text);
					
					if (mat.find())
					{
						hMapLoc.put(mat.group(1), hMapLang);
					}
				}
			}
			
			return hMapLoc;
			
			
			
		} catch (Exception e) {
			ErrorLog.saveError(e);
		}
		
		return null;
	}
}
	