package jp.rainbowdevil.snippets.ui.windows.syntax;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;

public class SyntaxManager {
	private static Map<String, SyntaxData> data = new Hashtable<String, SyntaxData>();
	private String java = "class public private protected extends interface void null return if swtich this newsynchronized throw throws int double float long for package import";
	
	 public synchronized SyntaxData getSyntaxData(String extension) {
		    // Check in cache
		    SyntaxData sd = (SyntaxData) data.get(extension);
		    if (sd == null) {
		      // Not in cache; load it and put in cache
		      sd = loadSyntaxData(extension);
		      if (sd != null)
		        data.put(sd.getExtension(), sd);
		    }
		    return sd;
		 }
	
	private SyntaxData loadSyntaxData(String extension) {
	    SyntaxData sd = null;

	      //ResourceBundle rb = ResourceBundle.getBundle("examples.ch11." + extension);
	      sd = new SyntaxData(extension);
	      sd.setComment("//");
	      sd.setMultiLineCommentStart("/*");
	      sd.setMultiLineCommentEnd("*/");

	      // Load the keywords
	      Collection<String> keywords = new ArrayList<String>();
	      
	      for (StringTokenizer st = new StringTokenizer(java, " "); st
	          .hasMoreTokens();) {
	        keywords.add(st.nextToken());
	      }
	      sd.setKeywords(keywords);

	      // Load the punctuation
	      sd.setPunctuation(";");
	    
	    return sd;
	  }

}
