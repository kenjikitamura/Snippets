package jp.rainbowdevil.snippets.model;

import java.util.List;

/**
 * ���C�u�����܂��̓O���[�v��interface
 * */
public interface IGroupItem {
	
	public long getId();
	public void setId(long id);
	
	/** �c���[�ɕ\������^�C�g�����擾���� */
	public String getTitle();
	
	public void setTitle(String title);
	
	/** �q�̃��X�g���擾����B */
	public List<IGroupItem> getChildren();
	
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
	 * ���̃O���[�v���܂ރX�j�y�b�g�̃T�C�Y��Ԃ�
	 * @return
	 */
	public int getSnippetsSize();
	
	/**
	 * Item�̐e���擾����B
	 * ���݂��Ȃ��ꍇ��null��Ԃ��B
	 * @return
	 */
	public IGroupItem getParent();
	
	public void setParent(IGroupItem parent);
	
	/**
	 * �q��ǉ�����B
	 * @param item
	 */
	public void addChild(IGroupItem item);
	
	/**
	 * �q���폜����B
	 * @return �폜�ɐ����������ǂ���
	 * @param item
	 */
	public boolean removeChild(IGroupItem item);
	
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
