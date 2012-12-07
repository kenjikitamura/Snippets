package jp.rainbowdevil.snippets.model;

import java.util.ArrayList;
import java.util.List;

abstract public class AbstractGroupItem implements GroupItem{
	protected String title;
	protected GroupItem parent;
	protected List<GroupItem> children;
	protected List<ISnippet> snippets;
	
	public AbstractGroupItem(){
		snippets = new ArrayList<ISnippet>();
		children = new ArrayList<GroupItem>();
	}

	public List<GroupItem> getChildren() {
		return children;
	}

	public void setChildren(List<GroupItem> children) {
		this.children = children;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public int getChildrenSize() {
		if (children == null){
			return 0;
		}
		return children.size();
	}
	
	@Override
	public boolean hasChildren() {
		return getChildrenSize() != 0;
	}
	
	@Override
	public GroupItem getParent() {
		return parent;
	}
	
	@Override
	public void addChild(GroupItem item) {
		if (children == null){
			children = new ArrayList<GroupItem>();
		}
		children.add(item);		
	}

	public List<ISnippet> getSnippets() {
		return snippets;
	}

	public void setSnippets(List<ISnippet> snippets) {
		this.snippets = snippets;
	}
	
	@Override
	public void addSnippet(ISnippet snippet) {
		snippets.add(snippet);
	}
	 
}
