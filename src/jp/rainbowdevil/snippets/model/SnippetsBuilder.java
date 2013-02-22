package jp.rainbowdevil.snippets.model;

import java.util.Date;

import jp.rainbowdevil.snippets.preferences.ISnippetPreference;
import jp.rainbowdevil.snippets.preferences.PreferencesBuilder;

/**
 * �X�j�y�b�g��V�K�쐬����N���X
 * @author kkitamura
 *
 */
public class SnippetsBuilder {
	public static ISnippet createNewSnippet(){
		ISnippetPreference preference = PreferencesBuilder.getSnippetPreference();
		int no = preference.getInt(ISnippetPreference.NEW_SNIPPETS_NUMBER, 0);
		Snippet snippet = new Snippet();
		String body = "";
		String title = "Snippet "+(no + 1);		
		snippet.setBody(body);
		snippet.setTitle(title);
		snippet.setCreateDate(new Date());
		
		preference.setValue(ISnippetPreference.NEW_SNIPPETS_NUMBER, no + 1);
		
		// �X�j�y�b�g�ԍ��̕ۑ��B �ۑ��Ɏ��s�����ꍇ�̓��O�ɏڍׂ��ۑ������B
		preference.saveQuietly();
		return snippet;
	}
	
	public static IGroupItem createNewGroup(IGroupItem parent){
		Group group = new Group();
		group.setParent(parent);
		group.setTitle("Group");
		if (parent != null){
			parent.addChild(group);
		}
		return group;
	}
}
