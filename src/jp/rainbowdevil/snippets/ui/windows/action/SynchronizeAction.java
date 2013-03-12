package jp.rainbowdevil.snippets.ui.windows.action;

import java.io.IOException;

import jp.rainbowdevil.snippets.preferences.ISnippetPreference;
import jp.rainbowdevil.snippets.preferences.PreferencesBuilder;
import jp.rainbowdevil.snippets.sync.SynchronizeListener;
import jp.rainbowdevil.snippets.ui.ISnippetWindow;
import jp.rainbowdevil.snippets.ui.windows.WindowsSnippetWindow;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;

public class SynchronizeAction extends SnippetWindowAction{
	protected static org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(SynchronizeAction.class);
	
	public SynchronizeAction(WindowsSnippetWindow snippetsWindow, String title) {
		super(snippetsWindow, title);
	}

	@Override
	public void run() {
		try{
			ISnippetPreference preference = PreferencesBuilder.getSnippetPreference();
			String email = preference.getString(ISnippetPreference.ACCOUNT_EMAIL, null);
			String password = preference.getString(ISnippetPreference.ACCOUNT_PASSWORD, null);
			if (email != null){
				snippetWindow.getSynchronizeManager().login(email, password);
			}else{
				MessageBox box = new MessageBox(snippetWindow.getShell());
				box.setText(ISnippetWindow.APP_NAME);
				box.setMessage("メニューの設定から、アカウント情報を入力してください。");
				box.open();
				return;
			}
		}catch(IOException e){
			log.error("ログイン失敗",e);
			if (e.getMessage().contains("Server returned HTTP response code: 401")){
				MessageBox box = new MessageBox(snippetWindow.getShell());
				box.setText(ISnippetWindow.APP_NAME);
				box.setMessage("認証に失敗しました。\nメールアドレス、パスワードを確認してください。\n原因 : "+e.getMessage());
				box.open();
			}else{
				MessageBox box = new MessageBox(snippetWindow.getShell());
				box.setText(ISnippetWindow.APP_NAME);
				box.setMessage("ログインに失敗しました。\n原因 : "+e.getClass().getName() + ":"+e.getMessage());
				box.open();	
			}
			
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
