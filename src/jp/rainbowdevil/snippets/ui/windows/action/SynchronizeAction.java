package jp.rainbowdevil.snippets.ui.windows.action;

import java.io.IOException;

import org.eclipse.swt.widgets.Display;

import jp.rainbowdevil.snippets.sync.SynchronizeListener;
import jp.rainbowdevil.snippets.sync.SynchronizeManager;
import jp.rainbowdevil.snippets.ui.windows.WindowsSnippetWindow;

public class SynchronizeAction extends SnippetWindowAction{
	protected static org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(SynchronizeAction.class);
	
	public SynchronizeAction(WindowsSnippetWindow snippetsWindow, String title) {
		super(snippetsWindow, title);
	}

	@Override
	public void run() {
		try{
			snippetWindow.getSynchronizeManager().login("test8@test.com", "kitamura");
		}catch(IOException e){
			log.error("���O�C�����s",e);
			return;
		}
		snippetWindow.getSynchronizeManager().synchronize(snippetWindow.getSnippetManager(), new SynchronizeListener() {
			
			@Override
			public void updateProgress(int current, int max) {
				log.debug("������("+current+"/"+max+")");
			}
			
			@Override
			public void error(String message, Throwable e) {
				log.debug("�����G���[");
			}
				
			@Override
			public void complete() {
				log.debug("��������");
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						snippetWindow.refresh();
					}
				});
			}
		});
	}
}
