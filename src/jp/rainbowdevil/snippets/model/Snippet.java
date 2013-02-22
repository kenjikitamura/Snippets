package jp.rainbowdevil.snippets.model;

import java.util.Date;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Snippet implements ISnippet{
	
	@Element(required=false)
	/** スニペットタイトル */
	private String title = "";
	
	@Element(required=false)
	/** スニペット本文 */
	private String body = "";
	
	@Element(required=false)
	/** ノート */
	private String notes = "";

	@ElementList(required=false)
	/** ラベル */
	private List<String> labels;
	
	@Element(required=false)
	/** 引用元URL */
	private String url = "";
	
	@Element(required=false)
	/** コード作者 */
	private String author = "";
	
	@Element(required=false)
	/** ライセンス */
	private String license = "";
	
	@Element(required=false)
	/** スニペット作成日時 */
	private Date createDate;
	
	@Element(required=false)
	/** スニペット更新日時 */
	private Date updateDate;
	
	/** スニペット編集可否 */
	private EditableType editableType;

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Snippets{title=");
		sb.append(title);
		sb.append("}");
		return sb.toString();
	}
	
	
	@Override
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String text) {
		this.body = text;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public String getRelatedUrl() {
		return url;
	}

	public void setRelatedUrl(String url) {
		this.url = url;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public EditableType getEditableType() {
		return editableType;
	}

	public void setEditableType(EditableType editableType) {
		this.editableType = editableType;
	}
}
