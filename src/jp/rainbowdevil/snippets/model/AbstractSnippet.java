package jp.rainbowdevil.snippets.model;

import java.util.Date;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class AbstractSnippet implements ISnippet {

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
	
	@Element(required=false)
	/** スニペット編集可否 */
	private EditableType editableType = null;
	
	@Element(required=true)
	/** 編集フラグ この変数の値により、ローカルのデータが更新されているかを確認する */
	private boolean isDirty = false;
	
	@Element(required=true)
	private SnippetsLibrary snippetsLibrary;
	
	@Element(required=true)
	private long id = -1;
		
	@Element(required=true)
	/** 更新カウンタ この変数の値によりサーバ上のデータが更新されているかを確認する。 */
	private long updateCount = -1;
	
	@Element(required=true)
	private boolean isDeleted = false;
	
	//@Element(required=true)
	//private long libraryId = -1;

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
		if (notes == null){
			return "";
		}
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
		if (url == null){
			return "";
		}
		return url;
	}

	public void setRelatedUrl(String url) {
		this.url = url;
	}

	public String getAuthor() {
		if (author == null){
			return "";
		}
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getLicense() {
		if (license == null){
			return "";
		}
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


	public boolean isDirty() {
		return isDirty;
	}


	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getUpdateCount() {		
		return updateCount;
	}

	@Override
	public void setUpdateCount(long updateCount) {
		this.updateCount = updateCount;
	}

	public SnippetsLibrary getSnippetsLibrary() {
		return snippetsLibrary;
	}


	public void setSnippetsLibrary(SnippetsLibrary snippetsLibrary) {
		this.snippetsLibrary = snippetsLibrary;
	}


	public boolean isDeleted() {
		return isDeleted;
	}


	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
