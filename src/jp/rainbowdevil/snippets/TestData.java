package jp.rainbowdevil.snippets;

import java.util.ArrayList;
import java.util.List;

import jp.rainbowdevil.snippets.model.Group;
import jp.rainbowdevil.snippets.model.IGroupItem;
import jp.rainbowdevil.snippets.model.ISnippet;
import jp.rainbowdevil.snippets.model.RootGroupItem;
import jp.rainbowdevil.snippets.model.Snippet;
import jp.rainbowdevil.snippets.model.SnippetsLibrary;

public class TestData {
	
	public static List<SnippetsLibrary> getTestData(){
		List<SnippetsLibrary> snippetsLibraries = new ArrayList<SnippetsLibrary>();
		
		SnippetsLibrary testLibrary = new SnippetsLibrary();
		testLibrary.setTitle("テストライブラリ");
		snippetsLibraries.add(testLibrary);
		
		RootGroupItem rootItem = new RootGroupItem();
		
		
		//List<IGroupItem> libraryList = new ArrayList<IGroupItem>();
		//libraryList.add(testLibrary);
		
		rootItem.getSnippetsLibraries().add(testLibrary);
		
		Group group = new Group();
		group.setTitle("hoge1");		
		//libraryList.add(group);
		testLibrary.addGroupItem(group);
		
		group = new Group();
		group.setTitle("hoge2");		
		//libraryList.add(group);
		testLibrary.addGroupItem(group);
		
		Group group1 = new Group();
		group1.setTitle("hoge2-1");		
		group.addChild(group1);
		
		ISnippet snippet;
		snippet = new Snippet();
		snippet.setTitle("タイトル1");
		snippet.setBody("ふがふが\nほげ");
		snippet.setSnippetsLibrary(testLibrary);
		group1.addSnippet(snippet);
		testLibrary.addSnippet(snippet);
		
		snippet = new Snippet();
		snippet.setTitle("タイトル2");
		snippet.setBody("おおおお\nうう");
		snippet.setSnippetsLibrary(testLibrary);
		group1.addSnippet(snippet);
		testLibrary.addSnippet(snippet);
		
		return snippetsLibraries;
	}

}
