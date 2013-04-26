package jp.rainbowdevil.snippets.ui.windows.preferences;

import jp.rainbowdevil.snippets.preferences.ISnippetPreference;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;

public class ConnectionPreferencePage extends FieldEditorPreferencePage{
	public ConnectionPreferencePage(){
		setTitle("接続設定");
        setMessage("サーバ接続設定");
	}
	
	@Override
	protected void createFieldEditors() {
		addField(new BooleanFieldEditor(ISnippetPreference.CONNECTION_USE_PROXY, "Proxyを使う", getFieldEditorParent()));
		addField(new StringFieldEditor(ISnippetPreference.CONNECTION_PROXY_SERVER, "Proxyサーバのアドレス",getFieldEditorParent()));
		addField(new StringFieldEditor(ISnippetPreference.CONNECTION_PROXY_PORT, "Proxyサーバのポート",getFieldEditorParent()));
	}

}
