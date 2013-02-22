package jp.rainbowdevil.snippets.model;

import java.util.Date;

/**
 * スニペットのinterface
 * @author kkitamura
 *
 */
public interface ISnippet {
	
	/** スニペットのタイトルを取得する。 */
	public String getTitle();
	
	/** スニペットのタイトルを設定する。 */
	public void setTitle(String title);
	
	/** スニペットの本文を取得する。 */
	public String getBody();
	
	/** スニペットの本文を設定する。 */
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
		EDITABLE,			// 編集可能
		USER_READ_ONLY,		// ユーザが編集ロック
		SYSTEM_READ_ONLY	// システムが編集ロック(リードオンリーのライブラリなど)
	}
	

}
