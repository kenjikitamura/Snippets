package jp.rainbowdevil.snippets.model;

import java.util.List;

/**
 * �A�C�e��(�X�j�y�b�g�A�m�[�g)�̏W��
 * @author kkitamura
 *
 */
public class ItemLibrary {
	
	/** �A�C�e�����X�g */
	private List<GroupItem> itemList;
	
	/** ���C�u�����̃^�C�g�� */
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
