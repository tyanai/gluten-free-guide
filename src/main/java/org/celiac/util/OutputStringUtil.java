package org.celiac.util;


public class OutputStringUtil {

	static public String treatOutput(String[] words, String _template){
		
		String nextWord = null;
		String template = _template;
		for (int i=0; i<words.length;i++){
			nextWord = words[i];
			template = template.substring(0,template.indexOf("*")) + nextWord + "\n" + template.substring(template.indexOf("*") + 1, template.length()) ;
		}
		return template;
		
	}
}
