package jp.rainbowdevil.snippets.model;

import java.util.List;

/**
 * ���C�u�����܂��̓O���[�v��interface
 * */
public interface GroupItem {
	
	/** �c���[�ɕ\������^�C�g�����擾���� */
	public String getTitle();
	
	/** �q�̃��X�g���擾����B */
	public List<GroupItem> getChildren();
	
	/**
	 * �q�����邩���擾����B
	 * @return
	 */
	public boolean hasChildren();
	
	/**
	 * �q�̃T�C�Y���擾����B 
	 * @return
	 */
	public int getChildrenSize();
	
	/**
	 * Item�̐e���擾����B
	 * ���݂��Ȃ��ꍇ��null��Ԃ��B
	 * @return
	 */
	public GroupItem getParent();
	
	/**
	 * �q��ǉ�����B
	 * @param item
	 */
	public void addChild(GroupItem item);
	
	/**
	 * �O���[�v�Ɋ܂܂��X�j�y�b�g�̃��X�g���擾����B
	 * @return
	 */
	public List<ISnippet> getSnippets();
	
	/**
	 * �X�j�y�b�g��ǉ�����B
	 * @param snippet
	 */
	public void addSnippet(ISnippet snippet);

}
