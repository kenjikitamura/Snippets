package jp.rainbowdevil.snippets.ui.windows.preferences;

import jp.rainbowdevil.snippets.preferences.ISnippetPreference;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;

public class AccountPreferencePage extends FieldEditorPreferencePage{

	public AccountPreferencePage(){
		setTitle("アカウント");
        setMessage("アカウント設定");
	}
	
	@Override
	protected void createFieldEditors() {
		addField(new StringFieldEditor(ISnippetPreference.ACCOUNT_EMAIL, "メールアドレス:",
		        getFieldEditorParent()));
		
		PasswordFieldEditor password = new PasswordFieldEditor(ISnippetPreference.ACCOUNT_PASSWORD, "パスワード:",
		        getFieldEditorParent());
		addField(password);
	}

}
