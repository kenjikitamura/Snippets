package jp.rainbowdevil.snippets.ui.windows;

import jp.rainbowdevil.snippets.ui.windows.action.CreateGroupAction;
import jp.rainbowdevil.snippets.ui.windows.action.DeleteGroupAction;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;

/**
 * 画面左のライブラリ、グループツリーのコンテキストメニュー
 * @author kkitamura
 *
 */
public class TreeContextMenuBuilder {
	private WindowsSnippetWindow mainWindow;
	public TreeContextMenuBuilder(WindowsSnippetWindow mainWindow){
		this.mainWindow = mainWindow;
	}
	
	public Menu createContextMenu(Control control){
		MenuManager menuManager = new MenuManager();
		menuManager.add(new CreateGroupAction(mainWindow, "新しいグループの追加"));
		menuManager.add(new DeleteGroupAction(mainWindow, "削除"));
		return menuManager.createContextMenu(control);
	}

}
