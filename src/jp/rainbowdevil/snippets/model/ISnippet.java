package jp.rainbowdevil.snippets.model;

import java.util.Date;

/**
 * スニペットのinterface
 * @author kkitamura
 *
 */
public interface ISnippet { 
	
	public long getId();
	public void setId(long id);
	
	
	/** スニペットのタイトルを取得する。 */
	public String getTitle();
	
	/** スニペットのタイトルを設定する。 */
	public void setTitle(String title);
	
	/** スニペットの本文を取得する。 */
	public String getBody();
	
	/** スニペットの本文を設定する。 */
	public void setBody(String text);
	
	/** 変更済みフラグを設定する。 */
	public void setDirty(boolean dirty);
	
	/** 変更済みフラグを取得する。 */
	public boolean isDirty();
	
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
	
	public long getUpdateCount();
	public void setUpdateCount(long updateCount);
	
	public boolean isDeleted();
	public void setDeleted(boolean isDeleted);
	
	public SnippetsLibrary getSnippetsLibrary();
	public void setSnippetsLibrary(SnippetsLibrary snippetsLibrary);
	
	public enum EditableType{
		EDITABLE,			// 編集可能
		USER_READ_ONLY,		// ユーザが編集ロック
		SYSTEM_READ_ONLY	// システムが編集ロック(リードオンリーのライブラリなど)
	}
	

}
