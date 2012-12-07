package jp.rainbowdevil.snippets;

import java.util.ArrayList;
import java.util.List;

import jp.rainbowdevil.snippets.model.Group;
import jp.rainbowdevil.snippets.model.GroupItem;
import jp.rainbowdevil.snippets.model.ISnippet;
import jp.rainbowdevil.snippets.model.RootGroupItem;
import jp.rainbowdevil.snippets.model.Snippet;

public class SnippetContext {
	
	private List<GroupItem> libraryList;
	private RootGroupItem rootItem;
	
	public SnippetContext(){
		rootItem = new RootGroupItem();
		libraryList = new ArrayList<GroupItem>();
		rootItem.setChildren(libraryList);
		
		Group group = new Group();
		group.setTitle("hoge1");		
		libraryList.add(group);
		
		group = new Group();
		group.setTitle("hoge2");		
		libraryList.add(group);
		
		Group group1 = new Group();
		group1.setTitle("hoge2-1");		
		group.addChild(group1);
		
		ISnippet snippet;
		snippet = new Snippet();
		snippet.setTitle("タイトル1");
		snippet.setText("ふがふが\nほげ");
		group1.addSnippet(snippet);
		
		snippet = new Snippet();
		snippet.setTitle("タイトル2");
		snippet.setText("おおおお\nうう");
		group1.addSnippet(snippet);
	}
	
	public List<GroupItem> getLibraryList() {
		return libraryList;
	}

	public void setLibraryList(List<GroupItem> libraryList) {
		this.libraryList = libraryList;
	}

	public RootGroupItem getRootItem() {
		return rootItem;
	}

	public void setRootItem(RootGroupItem rootItem) {
		this.rootItem = rootItem;
	}

	
}
