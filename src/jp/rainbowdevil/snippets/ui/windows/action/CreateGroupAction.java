package jp.rainbowdevil.snippets.ui.windows.action;

import jp.rainbowdevil.snippets.model.IGroupItem;
import jp.rainbowdevil.snippets.model.SnippetsBuilder;
import jp.rainbowdevil.snippets.ui.ISnippetWindow;
import jp.rainbowdevil.snippets.ui.windows.WindowsSnippetWindow;

public class CreateGroupAction extends SnippetWindowAction {

	public CreateGroupAction(WindowsSnippetWindow snippetWindow, String title) {
		super(snippetWindow, title);
	}
	
	@Override
	public void run() {
		IGroupItem item = snippetWindow.getCurrentSelectedTreeItem();
		if (item != null){
			SnippetsBuilder.createNewGroup(item);
			snippetWindow.refresh();
		}
	}

}
