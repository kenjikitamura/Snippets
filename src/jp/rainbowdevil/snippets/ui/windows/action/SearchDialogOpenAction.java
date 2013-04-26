package jp.rainbowdevil.snippets.ui.windows.action;

import jp.rainbowdevil.snippets.ui.windows.WindowsSnippetWindow;
import jp.rainbowdevil.snippets.ui.windows.SnippetSearchWindow;

/**
 * スニペット検索ダイアログを開くAction
 * @author kkitamura
 *
 */
public class SearchDialogOpenAction extends SnippetWindowAction{
	protected static org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(SearchDialogOpenAction.class);

	private WindowsSnippetWindow mainWindow;
	
	public SearchDialogOpenAction(WindowsSnippetWindow snippetsWindow, String title) {
		super(snippetsWindow, title);
		mainWindow = snippetsWindow;
	}
	
	@Override
	public void run() {
		log.debug("テスト検索窓表示");
		SnippetSearchWindow searchWindow = new SnippetSearchWindow(snippetWindow.getShell());
		searchWindow.setMainWindow(mainWindow);
		searchWindow.open();
		log.debug("テスト検索窓表示完了");
	}

}
