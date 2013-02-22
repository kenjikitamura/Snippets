package jp.rainbowdevil.snippets.ui.windows;

import jp.rainbowdevil.snippets.ui.windows.action.CreateGroupAction;
import jp.rainbowdevil.snippets.ui.windows.action.DeleteGroupAction;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;

/**
 * ��ʍ��̃��C�u�����A�O���[�v�c���[�̃R���e�L�X�g���j���[
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
		menuManager.add(new CreateGroupAction(mainWindow, "�V�����O���[�v�̒ǉ�"));
		menuManager.add(new DeleteGroupAction(mainWindow, "�폜"));
		return menuManager.createContextMenu(control);
	}

}
