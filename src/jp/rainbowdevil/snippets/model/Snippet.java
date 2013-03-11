package jp.rainbowdevil.snippets.model;

import java.util.Date;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Snippet implements ISnippet{
	
	@Element(required=false)
	/** �X�j�y�b�g�^�C�g�� */
	private String title = "";
	
	@Element(required=false)
	/** �X�j�y�b�g�{�� */
	private String body = "";
	
	@Element(required=false)
	/** �m�[�g */
	private String notes = "";

	@ElementList(required=false)
	/** ���x�� */
	private List<String> labels;
	
	@Element(required=false)
	/** ���p��URL */
	private String url = "";
	
	@Element(required=false)
	/** �R�[�h��� */
	private String author = "";
	
	@Element(required=false)
	/** ���C�Z���X */
	private String license = "";
	
	@Element(required=false)
	/** �X�j�y�b�g�쐬���� */
	private Date createDate;
	
	@Element(required=false)
	/** �X�j�y�b�g�X�V���� */
	private Date updateDate;
	
	@Element(required=false)
	/** �X�j�y�b�g�ҏW�� */
	private EditableType editableType = null;
	
	@Element(required=true)
	/** �ҏW�t���O ���̕ϐ��̒l�ɂ��A���[�J���̃f�[�^���X�V����Ă��邩���m�F���� */
	private boolean isDirty = false;
	
	@Element(required=true)
	private SnippetsLibrary snippetsLibrary;
	
	@Element(required=true)
	private long id = -1;
		
	@Element(required=true)
	/** �X�V�J�E���^ ���̕ϐ��̒l�ɂ��T�[�o��̃f�[�^���X�V����Ă��邩���m�F����B */
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
