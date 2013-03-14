package jp.rainbowdevil.snippets.ui.windows;

import jp.rainbowdevil.snippets.ui.windows.action.DeleteSnippetsAction;
import jp.rainbowdevil.snippets.ui.windows.action.ExitAction;
import jp.rainbowdevil.snippets.ui.windows.action.OpenCreateNewTagDialogAction;
import jp.rainbowdevil.snippets.ui.windows.action.OpenPreferenceDialogAction;
import jp.rainbowdevil.snippets.ui.windows.action.SaveSnippetLibraryToLocalDatabaseAction;
import jp.rainbowdevil.snippets.ui.windows.action.SearchDialogOpenAction;
import jp.rainbowdevil.snippets.ui.windows.action.SynchronizeAction;

import org.eclipse.jface.action.MenuManager;

public class SnippetsMemuBuilder {
	
	public MenuManager createMenuManager(WindowsSnippetWindow snippetsWindow){
		// メニューバーを作成する。
		MenuManager menuManager = new MenuManager("");

		// ファイル(F)メニューを作成してメニューバーに追加する。
		MenuManager fileMenu = new MenuManager("ファイル(&F)");
		fileMenu.add(new ExitAction(snippetsWindow));
		fileMenu.add(new SaveSnippetLibraryToLocalDatabaseAction(snippetsWindow,"保存"));
		fileMenu.add(new DeleteSnippetsAction(snippetsWindow, "スニペット削除"));
		fileMenu.add(new OpenPreferenceDialogAction(snippetsWindow, "設定"));
		fileMenu.add(new OpenCreateNewTagDialogAction(snippetsWindow, "タグの追加"));
		
		// テスト用検索ウインドウ表示
		fileMenu.add(new SearchDialogOpenAction(snippetsWindow, "テスト検索窓表示"));
		
		fileMenu.add(new SynchronizeAction(snippetsWindow, "サーバ同期テスト"));
		
		menuManager.add(fileMenu);
		
		return menuManager;
	}

}
