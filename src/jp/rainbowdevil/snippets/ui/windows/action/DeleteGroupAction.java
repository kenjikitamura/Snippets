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
			msg.setMessage("グループ "+item.getTitle()+" を削除しますか。\n削除するグループに含まれるスニペットは、どのグループにも所属せず、ライブラリに所属することになります。");
			int ret = msg.open();
			if (ret == SWT.OK){
				log.debug("グループ削除が選択された グループ="+item.getTitle());
				if (item.getParent() != null){
					boolean flg = item.getParent().removeChild(item);
					log.debug(item.getTitle()+"を、親の"+item.getParent().getTitle()+"から削除 削除成功?="+flg);
					snippetWindow.refresh();
				}else{
					log.debug(item.getTitle()+"の親はnullだったので削除できなかった。");
				}
			}else{
				log.debug("グループ削除 キャンセル");
			}
		}
	}

}
