package jp.rainbowdevil.snippets.model;

import java.util.Date;

/**
 * �X�j�y�b�g��interface
 * @author kkitamura
 *
 */
public interface ISnippet {
	
	/** �X�j�y�b�g�̃^�C�g�����擾����B */
	public String getTitle();
	
	/** �X�j�y�b�g�̃^�C�g����ݒ肷��B */
	public void setTitle(String title);
	
	/** �X�j�y�b�g�̖{�����擾����B */
	public String getBody();
	
	/** �X�j�y�b�g�̖{����ݒ肷��B */
	public void setBody(String text);
	
	public Date getCreateDate();
	public void setCreateDate(Date createDate);
	
	public Date getUpdateDate();
	public void setUpdateDate(Date updateDate);
	
	public String getNotes();
	public void setNotes(String note);
	
	public String getAuthor();
	public void setAuthor(String author);
	
	public String getRelatedUrl();
	public void setRelatedUrl(String url);
	
	public EditableType getEditableType();
	public void setEditableType(EditableType editableType);
	
	public enum EditableType{
		EDITABLE,			// �ҏW�\
		USER_READ_ONLY,		// ���[�U���ҏW���b�N
		SYSTEM_READ_ONLY	// �V�X�e�����ҏW���b�N(���[�h�I�����[�̃��C�u�����Ȃ�)
	}
	

}
