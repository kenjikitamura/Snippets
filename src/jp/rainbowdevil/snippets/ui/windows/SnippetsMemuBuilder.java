package jp.rainbowdevil.snippets.ui.windows;

import jp.rainbowdevil.snippets.ui.ISnippetWindow;
import jp.rainbowdevil.snippets.ui.windows.action.ExitAction;
import jp.rainbowdevil.snippets.ui.windows.action.SaveSnippetLibraryToLocalDatabaseAction;
import jp.rainbowdevil.snippets.ui.windows.action.SearchDialogOpenAction;
import jp.rainbowdevil.snippets.ui.windows.action.SnippetWindowAction;

import org.eclipse.jface.action.MenuManager;

public class SnippetsMemuBuilder {
	
	public MenuManager createMenuManager(WindowsSnippetWindow snippetsWindow){
		// メニューバーを作成する。
		MenuManager menuManager = new MenuManager("");

		// ファイル(F)メニューを作成してメニューバーに追加する。
		MenuManager fileMenu = new MenuManager("ファイル(&F)");
		fileMenu.add(new ExitAction(snippetsWindow));
		fileMenu.add(new SaveSnippetLibraryToLocalDatabaseAction(snippetsWindow,"保存"));
		
		// テスト用検索ウインドウ表示
		fileMenu.add(new SearchDialogOpenAction(snippetsWindow, "テスト検索窓表示"));
		
		menuManager.add(fileMenu);
		
		
		
		
		return menuManager;
	}

}
