package jp.rainbowdevil.snippets.model;

import java.util.List;

/**
 * アイテム(スニペット、ノート)の集合
 * @author kkitamura
 *
 */
public class ItemLibrary {
	
	/** アイテムリスト */
	private List<GroupItem> itemList;
	
	/** ライブラリのタイトル */
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<GroupItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<GroupItem> itemList) {
		this.itemList = itemList;
	}
	
}
