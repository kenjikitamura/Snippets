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
				box.setMessage("���j���[�̐ݒ肩��A�A�J�E���g������͂��Ă��������B");
				box.open();
				return;
			}
		}catch(IOException e){
			log.error("���O�C�����s",e);
			if (e.getMessage().contains("Server returned HTTP response code: 401")){
				MessageBox box = new MessageBox(snippetWindow.getShell());
				box.setText(ISnippetWindow.APP_NAME);
				box.setMessage("�F�؂Ɏ��s���܂����B\n���[���A�h���X�A�p�X���[�h���m�F���Ă��������B\n���� : "+e.getMessage());
				box.open();
			}else{
				MessageBox box = new MessageBox(snippetWindow.getShell());
				box.setText(ISnippetWindow.APP_NAME);
				box.setMessage("���O�C���Ɏ��s���܂����B\n���� : "+e.getClass().getName() + ":"+e.getMessage());
				box.open();	
			}
			
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
