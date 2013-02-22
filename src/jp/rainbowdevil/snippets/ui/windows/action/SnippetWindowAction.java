package jp.rainbowdevil.snippets.ui.windows.action;

import jp.rainbowdevil.snippets.ui.ISnippetWindow;
import jp.rainbowdevil.snippets.ui.windows.WindowsSnippetWindow;

import org.apache.log4j.chainsaw.Main;
import org.eclipse.jface.action.Action;

public class SnippetWindowAction extends Action {
	
	protected WindowsSnippetWindow snippetWindow;
	
	public SnippetWindowAction(WindowsSnippetWindow snippetsWindow, String title) {
		super(title);
		this.snippetWindow = snippetsWindow;
	}

}
