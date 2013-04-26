package jp.rainbowdevil.snippets.model;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * グループアイテムの基底クラス
 * @author kitamura
 *
 */
@Root
abstract public class AbstractGroupItem implements IGroupItem{
	
	@Element
	/**
	 * サーバ上のグループID
	 */
	protected long id = -1;
	
	@Element
	protected String title;
	
	
	protected IGroupItem parent;
	
	@ElementList
	protected List<IGroupItem> children;
	
	@ElementList
	protected List<ISnippet> snippets;
	
	public AbstractGroupItem(){
		snippets = new ArrayList<ISnippet>();
		children = new ArrayList<IGroupItem>();
	}
	
	public String toString(){
		return "GroupItem{"+getTitle()+"}";
	}

	public List<IGroupItem> getChildren() {
		return children;
	}

	public void setChildren(List<IGroupItem> children) {
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
	public int getSnippetsSize() {
		int size = snippets.size();
		if (children == null){
			return size;
		}
		
		for(IGroupItem item : children){
			size += item.getSnippetsSize();
		}

		return size;
	}
	
	@Override
	public boolean hasChildren() {
		return getChildrenSize() != 0;
	}
	
	@Override
	@Element(required=false)
	public IGroupItem getParent() {
		return parent;
	}
	
	@Override
	public void addChild(IGroupItem item) {
		if (children == null){
			children = new ArrayList<IGroupItem>();
		}
		children.add(item);		
	}
	
	@Override
	public boolean removeChild(IGroupItem item) {
		return children.remove(item);		
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

	@Element(required=false)
	public void setParent(IGroupItem parent) {
		this.parent = parent;
	}
	
	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}
	 
}
