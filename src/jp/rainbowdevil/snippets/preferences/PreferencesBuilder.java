package jp.rainbowdevil.snippets.preferences;

import java.io.IOException;

import org.apache.log4j.Logger;

import jp.rainbowdevil.snippets.preferences.windows.WindowsSnippetPreference;

public class PreferencesBuilder {
	protected static Logger log = Logger.getLogger(PreferencesBuilder.class);
	
	private static ISnippetPreference snippetPreference;
	
	synchronized public static ISnippetPreference getSnippetPreference(){
		if (snippetPreference == null){
			snippetPreference = new WindowsSnippetPreference();
			try {
				snippetPreference.load();
			} catch (IOException e) {
				log.debug("設定ファイルの読み込みに失敗。初回起動時は問題なし。");
			}
		}
		return snippetPreference;
	}
}
