package jp.rainbowdevil.snippets.model;

import java.util.List;

/**
 * ライブラリまたはグループのinterface
 * */
public interface GroupItem {
	
	/** ツリーに表示するタイトルを取得する */
	public String getTitle();
	
	/** 子のリストを取得する。 */
	public List<GroupItem> getChildren();
	
	/**
	 * 子がいるかを取得する。
	 * @return
	 */
	public boolean hasChildren();
	
	/**
	 * 子のサイズを取得する。 
	 * @return
	 */
	public int getChildrenSize();
	
	/**
	 * Itemの親を取得する。
	 * 存在しない場合はnullを返す。
	 * @return
	 */
	public GroupItem getParent();
	
	/**
	 * 子を追加する。
	 * @param item
	 */
	public void addChild(GroupItem item);
	
	/**
	 * グループに含まれるスニペットのリストを取得する。
	 * @return
	 */
	public List<ISnippet> getSnippets();
	
	/**
	 * スニペットを追加する。
	 * @param snippet
	 */
	public void addSnippet(ISnippet snippet);

}
