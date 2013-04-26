package jp.rainbowdevil.snippets.model;

import java.util.List;

/**
 * ライブラリまたはグループのinterface
 * */
public interface IGroupItem {
	
	public long getId();
	public void setId(long id);
	
	/** ツリーに表示するタイトルを取得する */
	public String getTitle();
	
	public void setTitle(String title);
	
	/** 子のリストを取得する。 */
	public List<IGroupItem> getChildren();
	
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
	 * このグループが含むスニペットのサイズを返す
	 * @return
	 */
	public int getSnippetsSize();
	
	/**
	 * Itemの親を取得する。
	 * 存在しない場合はnullを返す。
	 * @return
	 */
	public IGroupItem getParent();
	
	public void setParent(IGroupItem parent);
	
	/**
	 * 子を追加する。
	 * @param item
	 */
	public void addChild(IGroupItem item);
	
	/**
	 * 子を削除する。
	 * @return 削除に成功したかどうか
	 * @param item
	 */
	public boolean removeChild(IGroupItem item);
	
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
