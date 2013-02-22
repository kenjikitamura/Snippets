package jp.rainbowdevil.snippets.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.simpleframework.xml.Attribute;
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
	
	private Date createdAt;
	
	private int createUserId;
	
	/** �T�[�o���ID �T�[�o�ɃA�b�v���Ă��Ȃ��ꍇ��-1 */
	private int id = -1;
	
	public SnippetsLibrary(){
		groupList = new ArrayList<IGroupItem>();
		snippets = new ArrayList<ISnippet>();
	}
	
	public String toString(){
		return "SnippetLibrary{"+getTitle()+"}";
	}

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
