package jp.rainbowdevil.snippets.ui.windows.preferences;

import jp.rainbowdevil.snippets.preferences.ISnippetPreference;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;

public class ConnectionPreferencePage extends FieldEditorPreferencePage{
	public ConnectionPreferencePage(){
		setTitle("�ڑ��ݒ�");
        setMessage("�T�[�o�ڑ��ݒ�");
	}
	
	@Override
	protected void createFieldEditors() {
		addField(new BooleanFieldEditor(ISnippetPreference.CONNECTION_USE_PROXY, "Proxy���g��", getFieldEditorParent()));
		addField(new StringFieldEditor(ISnippetPreference.CONNECTION_PROXY_SERVER, "Proxy�T�[�o�̃A�h���X",getFieldEditorParent()));
		addField(new StringFieldEditor(ISnippetPreference.CONNECTION_PROXY_PORT, "Proxy�T�[�o�̃|�[�g",getFieldEditorParent()));
	}

}
