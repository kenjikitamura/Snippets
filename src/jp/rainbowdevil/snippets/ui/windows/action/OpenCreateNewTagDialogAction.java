package jp.rainbowdevil.snippets.ui.windows.action;

import jp.rainbowdevil.snippets.model.IGroupItem;
import jp.rainbowdevil.snippets.model.SnippetsBuilder;
import jp.rainbowdevil.snippets.ui.windows.WindowsSnippetWindow;

import org.eclipse.jface.dialogs.InputDialog;

/**
 * タグ新規作成ダイアログを開き、タグを追加するAction
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
		InputDialog inputDialog = new InputDialog(snippetWindow.getShell(), "新しいタグの追加", "追加するタグ名を入力してください。", "", null);
		int ret = inputDialog.open();
		if (ret == InputDialog.OK){
			String tagName = inputDialog.getValue();
			if (tagName != null && tagName.trim().length() != 0){
				log.debug("入力されたタグ名="+tagName);
				IGroupItem item = snippetWindow.getCurrentSelectedTreeItem();
				if (item != null){
					SnippetsBuilder.createNewTag(item,tagName);
					snippetWindow.refresh();
				}
			}else{
				log.debug("何も入力されなかった。");
			}
		}else{
			log.debug("キャンセルされた。");
		}
	}

}
