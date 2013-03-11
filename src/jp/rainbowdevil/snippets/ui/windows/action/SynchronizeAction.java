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
			log.error("ログイン失敗",e);
			return;
		}
		snippetWindow.getSynchronizeManager().synchronize(snippetWindow.getSnippetManager(), new SynchronizeListener() {
			
			@Override
			public void updateProgress(int current, int max) {
				log.debug("同期中("+current+"/"+max+")");
			}
			
			@Override
			public void error(String message, Throwable e) {
				log.debug("同期エラー");
			}
				
			@Override
			public void complete() {
				log.debug("同期完了");
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
