package jp.rainbowdevil.snippets.ui.windows.action;

import jp.rainbowdevil.snippets.model.IGroupItem;
import jp.rainbowdevil.snippets.model.SnippetsBuilder;
import jp.rainbowdevil.snippets.ui.windows.WindowsSnippetWindow;

import org.eclipse.jface.dialogs.InputDialog;

/**
 * �^�O�V�K�쐬�_�C�A���O���J���A�^�O��ǉ�����Action
 * @author kkitamura
 *
 */
public class OpenCreateNewTagDialogAction extends SnippetWindowAction{
	
	protected static org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(OpenCreateNewTagDialogAction.class);

	public OpenCreateNewTagDialogAction(WindowsSnippetWindow snippetsWindow,
			String title) {
		super(snippetsWindow, title);
	}
	
	@Override
	public void run() {
		InputDialog inputDialog = new InputDialog(snippetWindow.getShell(), "�V�����^�O�̒ǉ�", "�ǉ�����^�O������͂��Ă��������B", "", null);
		int ret = inputDialog.open();
		if (ret == InputDialog.OK){
			String tagName = inputDialog.getValue();
			if (tagName != null && tagName.trim().length() != 0){
				log.debug("���͂��ꂽ�^�O��="+tagName);
				IGroupItem item = snippetWindow.getCurrentSelectedTreeItem();
				if (item != null){
					SnippetsBuilder.createNewTag(item,tagName);
					snippetWindow.refresh();
				}
			}else{
				log.debug("�������͂���Ȃ������B");
			}
		}else{
			log.debug("�L�����Z�����ꂽ�B");
		}
	}

}
