package jp.rainbowdevil.snippets.ui.windows;

import jp.rainbowdevil.snippets.ui.ISnippetWindow;
import jp.rainbowdevil.snippets.ui.windows.action.ExitAction;
import jp.rainbowdevil.snippets.ui.windows.action.SaveSnippetLibraryToLocalDatabaseAction;
import jp.rainbowdevil.snippets.ui.windows.action.SearchDialogOpenAction;
import jp.rainbowdevil.snippets.ui.windows.action.SnippetWindowAction;

import org.eclipse.jface.action.MenuManager;

public class SnippetsMemuBuilder {
	
	public MenuManager createMenuManager(WindowsSnippetWindow snippetsWindow){
		// ���j���[�o�[���쐬����B
		MenuManager menuManager = new MenuManager("");

		// �t�@�C��(F)���j���[���쐬���ă��j���[�o�[�ɒǉ�����B
		MenuManager fileMenu = new MenuManager("�t�@�C��(&F)");
		fileMenu.add(new ExitAction(snippetsWindow));
		fileMenu.add(new SaveSnippetLibraryToLocalDatabaseAction(snippetsWindow,"�ۑ�"));
		
		// �e�X�g�p�����E�C���h�E�\��
		fileMenu.add(new SearchDialogOpenAction(snippetsWindow, "�e�X�g�������\��"));
		
		menuManager.add(fileMenu);
		
		
		
		
		return menuManager;
	}

}
