package jp.rainbowdevil.snippets.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * �A�C�e��(�X�j�y�b�g�A�m�[�g)�̏W��
 * @author kkitamura
 *
 */
@Root
public class SnippetsLibrary implements IGroupItem{
	
	@ElementList
	/** �X�j�y�b�g���O���[�s���O����GroupItem�̃��X�g */
	private List<IGroupItem> groupList;
	
	@ElementList
	/** ���C�u�����Ɋ܂܂��X�j�y�b�g */
	private List<ISnippet> snippets; 
	
	@Attribute
	/** ���C�u�����̃^�C�g�� */
	private String title;
	
	@Element(required=false)
	private Date createdAt;
	
	@Element(required=false)
	private int createUserId;
	
	@Element(required=true)
	/** �T�[�o���ID �T�[�o�ɃA�b�v���Ă��Ȃ��ꍇ��-1 */
	private long id = -1;
	
	@Element(required=true)
	/** �T�[�o�Ɠ��������ۂ̃o�[�W�����ԍ� */
	private long updateCount = -1;
	
	@Element(required=true)
	/** �ύX�������ǂ����t���O */
	private boolean isDirty = false;
	
	public SnippetsLibrary(){
		groupList = new ArrayList<IGroupItem>();
		snippets = new ArrayList<ISnippet>();
	}
	
	public String toString(){
		return "SnippetLibrary{"+getTitle()+"}";
	}
	
	/**
	 * �w���ID�̃X�j�y�b�g���擾����B
	 * ���݂��Ȃ��ꍇ��null��Ԃ��B
	 * @param id
	 * @return
	 */
	public ISnippet getSnippet(long id){
		for(ISnippet snippet:snippets){
			if (snippet.getId() == id){
				return snippet;
			}
		}
		return null;
	}
	
	/**
	 * �X�j�y�b�g�������̃X�j�y�b�g�ōX�V����B
	 * @param snippet
	 */
	public void updateSnippet(ISnippet snippet){
		synchronized (snippets) {
			ISnippet oldSnippet = null;
			for(ISnippet tmpSnippet:snippets){
				if (tmpSnippet.getId() == snippet.getId()){
					oldSnippet = tmpSnippet;
				}
			}
			
			if (oldSnippet != null){
				oldSnippet.setTitle(snippet.getTitle());
				oldSnippet.setBody(snippet.getBody());
				oldSnippet.setAuthor(snippet.getAuthor());
				oldSnippet.setNotes(snippet.getNotes());
				oldSnippet.setRelatedUrl(snippet.getRelatedUrl());
				oldSnippet.setUpdateCount(snippet.getUpdateCount());
				oldSnippet.setUpdateDate(snippet.getUpdateDate());				
			}else{
				snippets.add(snippet);
			}	
		}		
	}
	
	//----------------------------------------------------------
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<IGroupItem> getItemList() {
		return groupList;
	}

	public void setItemList(List<IGroupItem> itemList) {
		this.groupList = itemList;
	}

	public List<ISnippet> getSnippets() {
		return snippets;
	}

	public void setSnippets(List<ISnippet> snippets) {
		this.snippets = snippets;
	}
	
	public void addSnippet(ISnippet snippet){
		snippets.add(snippet);
	}
	
	public void addGroupItem(IGroupItem groupItem){
		groupList.add(groupItem);
	}
	
	
	//------------------------------------------------
	// IGroupItem�̃��\�b�h
	@Override
	public void addChild(IGroupItem item) {
		addGroupItem(item);		
	}
	@Override
	public List<IGroupItem> getChildren() {
		return getItemList();
	}
	@Override
	public int getChildrenSize() {
		return getItemList().size();
	}
	@Override
	public boolean removeChild(IGroupItem item) {
		return getChildren().remove(item);
	}
	@Override
	public IGroupItem getParent() {
		return null;
	}
	@Override
	public void setParent(IGroupItem parent) {
	}
	@Override
	public boolean hasChildren() {
		return getItemList().size() != 0;
	}
	@Override
	public int getSnippetsSize() {
		int size = snippets.size();
		return size;
	}
	//------------------------------------------------

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUpdateCount() {
		return updateCount;
	}

	public void setUpdateCount(long update_count) {
		this.updateCount = update_count;
	}

	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}
	

	
}
