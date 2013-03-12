package jp.rainbowdevil.snippets.ui.windows.preferences;

import jp.rainbowdevil.snippets.preferences.ISnippetPreference;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;

public class AccountPreferencePage extends FieldEditorPreferencePage{

	public AccountPreferencePage(){
		setTitle("�A�J�E���g");
        setMessage("�A�J�E���g�ݒ�");
	}
	
	@Override
	protected void createFieldEditors() {
		addField(new StringFieldEditor(ISnippetPreference.ACCOUNT_EMAIL, "���[���A�h���X:",
		        getFieldEditorParent()));
		
		PasswordFieldEditor password = new PasswordFieldEditor(ISnippetPreference.ACCOUNT_PASSWORD, "�p�X���[�h:",
		        getFieldEditorParent());
		addField(password);
	}

}
