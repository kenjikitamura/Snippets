package jp.rainbowdevil.snippets.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * アイテム(スニペット、ノート)の集合
 * @author kkitamura
 *
 */
@Root
public class SnippetsLibrary implements IGroupItem{
	
	@ElementList
	/** スニペットをグルーピングするGroupItemのリスト */
	private List<IGroupItem> groupList;
	
	@ElementList
	/** ライブラリに含まれるスニペット */
	private List<ISnippet> snippets; 
	
	@Attribute
	/** ライブラリのタイトル */
	private String title;
	
	@Element(required=false)
	private Date createdAt;
	
	@Element(required=false)
	private int createUserId;
	
	@Element(required=true)
	/** サーバ上のID サーバにアップしていない場合は-1 */
	private long id = -1;
	
	@Element(required=true)
	/** サーバと同期した際のバージョン番号 */
	private long updateCount = -1;
	
	@Element(required=true)
	/** 変更したかどうかフラグ */
	private boolean isDirty = false;
	
	public SnippetsLibrary(){
		groupList = new ArrayList<IGroupItem>();
		snippets = new ArrayList<ISnippet>();
	}
	
	public String toString(){
		return "SnippetLibrary{"+getTitle()+"}";
	}
	
	/**
	 * 指定のIDのスニペットを取得する。
	 * 存在しない場合はnullを返す。
	 * @param id
	 * @return
	 */
	public ISnippet getSnippet(long id){
		for(ISnippet snippet:snippets){
			if (snippet.getId() == id){
				return snippet;
			}
		}
		return null;
	}
	
	/**
	 * スニペットを引数のスニペットで更新する。
	 * @param snippet
	 */
	public void updateSnippet(ISnippet snippet){
		synchronized (snippets) {
			ISnippet oldSnippet = null;
			for(ISnippet tmpSnippet:snippets){
				if (tmpSnippet.getId() == snippet.getId()){
					oldSnippet = tmpSnippet;
				}
			}
			
			if (oldSnippet != null){
				oldSnippet.setTitle(snippet.getTitle());
				oldSnippet.setBody(snippet.getBody());
				oldSnippet.setAuthor(snippet.getAuthor());
				oldSnippet.setNotes(snippet.getNotes());
				oldSnippet.setRelatedUrl(snippet.getRelatedUrl());
				oldSnippet.setUpdateCount(snippet.getUpdateCount());
				oldSnippet.setUpdateDate(snippet.getUpdateDate());				
			}else{
				snippets.add(snippet);
			}	
		}		
	}
	
	//----------------------------------------------------------
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<IGroupItem> getItemList() {
		return groupList;
	}

	public void setItemList(List<IGroupItem> itemList) {
		this.groupList = itemList;
	}

	public List<ISnippet> getSnippets() {
		return snippets;
	}

	public void setSnippets(List<ISnippet> snippets) {
		this.snippets = snippets;
	}
	
	public void addSnippet(ISnippet snippet){
		snippets.add(snippet);
	}
	
	public void addGroupItem(IGroupItem groupItem){
		groupList.add(groupItem);
	}
	
	
	//------------------------------------------------
	// IGroupItemのメソッド
	@Override
	public void addChild(IGroupItem item) {
		addGroupItem(item);		
	}
	@Override
	public List<IGroupItem> getChildren() {
		return getItemList();
	}
	@Override
	public int getChildrenSize() {
		return getItemList().size();
	}
	@Override
	public boolean removeChild(IGroupItem item) {
		return getChildren().remove(item);
	}
	@Override
	public IGroupItem getParent() {
		return null;
	}
	@Override
	public void setParent(IGroupItem parent) {
	}
	@Override
	public boolean hasChildren() {
		return getItemList().size() != 0;
	}
	@Override
	public int getSnippetsSize() {
		int size = snippets.size();
		return size;
	}
	//------------------------------------------------

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUpdateCount() {
		return updateCount;
	}

	public void setUpdateCount(long update_count) {
		this.updateCount = update_count;
	}

	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}
	

	
}
