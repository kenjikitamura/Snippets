package jp.rainbowdevil.snippets.ui.windows.action;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import jp.rainbowdevil.snippets.model.ISnippet;
import jp.rainbowdevil.snippets.ui.ISnippetWindow;
import jp.rainbowdevil.snippets.ui.windows.WindowsSnippetWindow;

public class DeleteSnippetsAction extends SnippetWindowAction{
	protected static org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(DeleteSnippetsAction.class);
	
	public DeleteSnippetsAction(WindowsSnippetWindow snippetsWindow, String title) {
		super(snippetsWindow, title);
	}
	
	@Override
	public void run() {
		List<ISnippet> list = snippetWindow.getCurrentSelectedSnipptes();
		if (list.size() != 0){
			MessageBox msg = new MessageBox(snippetWindow.getShell(), SWT.OK | SWT.CANCEL);
			msg.setText(ISnippetWindow.APP_NAME);
			if (list.size() == 1){
				msg.setMessage("スニペット("+list.get(0).getTitle()+")を削除しますか。");
			}else{
				msg.setMessage("スニペットを "+list.size()+"件削除しますか。");
			}
			int ret = msg.open();
			if (ret == SWT.OK){
				for(ISnippet snippet:list){
					log.debug("スニペットを削除 title="+snippet.getTitle());
					snippet.setDeleted(true);
					snippet.setDirty(true);
					snippet.getSnippetsLibrary().setDirty(true);
				}
			}
			snippetWindow.refresh();
		}
	}
}
