package jp.rainbowdevil.snippets.model;

import java.util.Date;

import jp.rainbowdevil.snippets.preferences.ISnippetPreference;
import jp.rainbowdevil.snippets.preferences.PreferencesBuilder;

/**
 * スニペットを新規作成するクラス
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
		
		// スニペット番号の保存。 保存に失敗した場合はログに詳細が保存される。
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
