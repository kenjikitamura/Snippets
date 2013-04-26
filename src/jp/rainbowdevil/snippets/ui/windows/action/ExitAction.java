package jp.rainbowdevil.snippets.ui.windows.action;

import jp.rainbowdevil.snippets.ui.ISnippetWindow;
import jp.rainbowdevil.snippets.ui.windows.WindowsSnippetWindow;

import org.eclipse.jface.action.Action;

public class ExitAction extends SnippetWindowAction {
	
	public ExitAction(WindowsSnippetWindow snippetsWindow) {
		super(snippetsWindow, "終了");
	}
	
	@Override
	public void run() {
		snippetWindow.exit();
	}

}
