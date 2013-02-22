package jp.rainbowdevil.snippets.ui.windows.action;

import jp.rainbowdevil.snippets.model.IGroupItem;
import jp.rainbowdevil.snippets.model.SnippetsBuilder;
import jp.rainbowdevil.snippets.ui.ISnippetWindow;
import jp.rainbowdevil.snippets.ui.windows.WindowsSnippetWindow;

/**
 * �X�j�y�b�g���C�u�����̍쐬�A�N�V����
 * 
 * �����̃X�j�y�b�g���C�u������ǉ�����B
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
		log.debug("�V�����O���[�v�̍쐬");
		IGroupItem item = snippetWindow.getCurrentSelectedTreeItem();
		if (item != null){
			SnippetsBuilder.createNewGroup(item);
		}
		snippetWindow.refresh();
	}
	
}
