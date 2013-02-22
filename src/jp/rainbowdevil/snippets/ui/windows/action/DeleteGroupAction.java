package jp.rainbowdevil.snippets.ui.windows.action;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import jp.rainbowdevil.snippets.model.IGroupItem;
import jp.rainbowdevil.snippets.ui.ISnippetWindow;
import jp.rainbowdevil.snippets.ui.windows.WindowsSnippetWindow;

public class DeleteGroupAction extends SnippetWindowAction{
	
	protected static org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(DeleteGroupAction.class);

	public DeleteGroupAction(WindowsSnippetWindow snippetsWindow, String title) {
		super(snippetsWindow, title);
	}
	
	@Override
	public void run() {
		IGroupItem item = snippetWindow.getCurrentSelectedTreeItem();
		if (item != null){
			MessageBox msg = new MessageBox(snippetWindow.getShell(), SWT.OK | SWT.CANCEL);
			msg.setText(ISnippetWindow.APP_NAME);
			msg.setMessage("�O���[�v "+item.getTitle()+" ���폜���܂����B\n�폜����O���[�v�Ɋ܂܂��X�j�y�b�g�́A�ǂ̃O���[�v�ɂ����������A���C�u�����ɏ������邱�ƂɂȂ�܂��B");
			int ret = msg.open();
			if (ret == SWT.OK){
				log.debug("�O���[�v�폜���I�����ꂽ �O���[�v="+item.getTitle());
				if (item.getParent() != null){
					boolean flg = item.getParent().removeChild(item);
					log.debug(item.getTitle()+"���A�e��"+item.getParent().getTitle()+"����폜 �폜����?="+flg);
					snippetWindow.refresh();
				}else{
					log.debug(item.getTitle()+"�̐e��null�������̂ō폜�ł��Ȃ������B");
				}
			}else{
				log.debug("�O���[�v�폜 �L�����Z��");
			}
		}
	}

}
