package download;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Language {
	public static HashSet<HashSet<String>> loadLanguages(String fName) {
		
		MatchResult result = null;
		
		HashSet<String> languagesHSet = new HashSet<String>();
		
		try {
			File languages = new File(String.format("%s//%s", MainWindow.rootDir, fName));
			
			Scanner s = new Scanner(languages);
			
			String text = "";
			
			while (s.hasNextLine()) {
				text += s.nextLine() + "\n";
			}
			s.close();
			
			Pattern pat = Pattern.compile("(.*)=(.*)");
			Matcher mat = pat.matcher(text);
			if (mat.find()) {
				result = mat.toMatchResult();
			}
			
			for (int i = 0; i < result.groupCount(); i++) {
				languagesHSet.add(result.group(i));
			}
			
		} catch (Exception e) {
			ErrorLog.saveError(e);
		}
		return null;
	}
}
