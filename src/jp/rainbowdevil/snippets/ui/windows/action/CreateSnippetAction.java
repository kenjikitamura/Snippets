package jp.rainbowdevil.snippets.ui.windows.action;

import org.apache.log4j.Logger;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import jp.rainbowdevil.snippets.ui.ISnippetWindow;
import jp.rainbowdevil.snippets.ui.SnippetsException;
import jp.rainbowdevil.snippets.ui.windows.WindowsImageRegistory;
import jp.rainbowdevil.snippets.ui.windows.WindowsSnippetWindow;

/**
 * �V�����X�j�y�b�g���쐬����Action
 * @author kkitamura
 *
 */
public class CreateSnippetAction extends SnippetWindowAction {
	protected static Logger log = Logger.getLogger(CreateSnippetAction.class);
	
	private WindowsSnippetWindow snippetsWindow;
	public CreateSnippetAction(WindowsSnippetWindow snippetsWindow, String title) {		
		super(snippetsWindow, title);
		this.snippetsWindow = (WindowsSnippetWindow) snippetsWindow;
	}
	
	@Override
	public void run() {
		try {
			snippetsWindow.getSnippetManager().createNewSnippet();
		} catch (SnippetsException e) {
			log.debug("�V�����X�j�y�b�g�̍쐬�Ɏ��s�B",e);
			MessageBox msg = new MessageBox(snippetsWindow.getShell(), SWT.OK);
			msg.setText(snippetsWindow.getApplicationTitle());
			msg.setMessage(e.getMessage());
			msg.open();
		}
	}
	
	@Override
	public ImageDescriptor getImageDescriptor() {
		ImageDescriptor imageDescriptor = WindowsImageRegistory.getImageDescriptor("hoge");	
		return imageDescriptor;
	}

}
