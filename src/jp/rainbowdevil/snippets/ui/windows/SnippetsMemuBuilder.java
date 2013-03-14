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
		// ���j���[�o�[���쐬����B
		MenuManager menuManager = new MenuManager("");

		// �t�@�C��(F)���j���[���쐬���ă��j���[�o�[�ɒǉ�����B
		MenuManager fileMenu = new MenuManager("�t�@�C��(&F)");
		fileMenu.add(new ExitAction(snippetsWindow));
		fileMenu.add(new SaveSnippetLibraryToLocalDatabaseAction(snippetsWindow,"�ۑ�"));
		fileMenu.add(new DeleteSnippetsAction(snippetsWindow, "�X�j�y�b�g�폜"));
		fileMenu.add(new OpenPreferenceDialogAction(snippetsWindow, "�ݒ�"));
		fileMenu.add(new OpenCreateNewTagDialogAction(snippetsWindow, "�^�O�̒ǉ�"));
		
		// �e�X�g�p�����E�C���h�E�\��
		fileMenu.add(new SearchDialogOpenAction(snippetsWindow, "�e�X�g�������\��"));
		
		fileMenu.add(new SynchronizeAction(snippetsWindow, "�T�[�o�����e�X�g"));
		
		menuManager.add(fileMenu);
		
		return menuManager;
	}

}
