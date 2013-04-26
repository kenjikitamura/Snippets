package jp.rainbowdevil.snippets.ui.windows.action;

import java.io.IOException;

import jp.rainbowdevil.snippets.preferences.PreferencesBuilder;
import jp.rainbowdevil.snippets.preferences.windows.WindowsSnippetPreference;
import jp.rainbowdevil.snippets.ui.windows.WindowsSnippetWindow;
import jp.rainbowdevil.snippets.ui.windows.preferences.AccountPreferencePage;
import jp.rainbowdevil.snippets.ui.windows.preferences.ConnectionPreferencePage;

import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;

public class OpenPreferenceDialogAction extends SnippetWindowAction{
	
	protected static org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(OpenPreferenceDialogAction.class);

	public OpenPreferenceDialogAction(WindowsSnippetWindow snippetsWindow,
			String title) {
		super(snippetsWindow, title);
	}
	
	public void run() {
		PreferenceManager pm = new PreferenceManager();
		PreferenceNode pnode2 = new PreferenceNode("アカウント");
		pnode2.setPage(new AccountPreferencePage());
		pm.addToRoot(pnode2);
		
		PreferenceNode pnode3 = new PreferenceNode("接続");
		pnode3.setPage(new ConnectionPreferencePage());
		pm.addToRoot(pnode3);
		
		PreferenceDialog dialog = new PreferenceDialog(snippetWindow.getShell(), pm);
		WindowsSnippetPreference preference = (WindowsSnippetPreference) PreferencesBuilder.getSnippetPreference();
		dialog.setPreferenceStore(preference.getPreferenceStore());
		
		dialog.open();
		try {
			preference.getPreferenceStore().save();
		} catch (IOException e) {
			log.debug("設定保存失敗",e);
		}
	}
}
