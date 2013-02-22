package jp.rainbowdevil.snippets.ui.windows.action;

import jp.rainbowdevil.snippets.model.IGroupItem;
import jp.rainbowdevil.snippets.model.SnippetsBuilder;
import jp.rainbowdevil.snippets.ui.ISnippetWindow;
import jp.rainbowdevil.snippets.ui.windows.WindowsSnippetWindow;

/**
 * スニペットライブラリの作成アクション
 * 
 * 無名のスニペットライブラリを追加する。
 * @author kkitamura
 *
 */
public class CreateSnippetLibraryAction extends SnippetWindowAction{
	protected static org.apache.log4j.Logger log = org.apache.log4j.Logger
		.getLogger(CreateSnippetLibraryAction.class);
	public CreateSnippetLibraryAction(WindowsSnippetWindow snippetsWindow,
			String title) {
		super(snippetsWindow, title);
	}
	
	@Override
	public void run() {
		log.debug("新しいグループの作成");
		IGroupItem item = snippetWindow.getCurrentSelectedTreeItem();
		if (item != null){
			SnippetsBuilder.createNewGroup(item);
		}
		snippetWindow.refresh();
	}
	
}
