package jp.rainbowdevil.snippets.ui.windows.action;

import java.io.IOException;

import jp.rainbowdevil.snippets.ui.ISnippetWindow;
import jp.rainbowdevil.snippets.ui.windows.WindowsSnippetWindow;

public class SaveSnippetLibraryToLocalDatabaseAction extends SnippetWindowAction{
	protected static org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(SaveSnippetLibraryToLocalDatabaseAction.class);
	
	public SaveSnippetLibraryToLocalDatabaseAction(
			WindowsSnippetWindow snippetsWindow, String title) {
		super(snippetsWindow, title);
	}
	
	@Override
	public void run() {
		try {
			snippetWindow.getSnippetManager().saveSnippetLibraryToLocalDatabase();
		} catch (IOException e) {
			log.error("保存に失敗。",e);
		}
	}

}
