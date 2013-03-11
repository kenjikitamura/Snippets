package jp.rainbowdevil.snippets;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import jp.rainbowdevil.snippets.model.IGroupItem;
import jp.rainbowdevil.snippets.model.ISnippet;
import jp.rainbowdevil.snippets.model.SnippetsBuilder;
import jp.rainbowdevil.snippets.model.SnippetsLibrary;
import jp.rainbowdevil.snippets.model.RootGroupItem;
import jp.rainbowdevil.snippets.ui.ISnippetWindow;
import jp.rainbowdevil.snippets.ui.SnippetsException;


public class SnippetManager {
	protected static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(SnippetManager.class);
	
	//private List<IGroupItem> libraryList;
	 
	private RootGroupItem rootItem;
	private ISnippetWindow snippetsWindow;
	private List<SnippetsLibrary> snippetsLibraries;
	
	/** ���ݗL���ȃJ�����g�X�j�y�b�g���C�u����
	 *  �V�K�X�j�y�b�g�̒ǉ��͂��̃J�����g�X�j�y�b�g���C�u�����ɑ΂��čs���B */
	private SnippetsLibrary currentSnippetsLibrary;
	
	/** ���ݑI������Ă���O���[�v */
	private IGroupItem currentGroupItem;
	
	public SnippetManager(){
		//snippetsLibraries = TestData.getTestData();		
		rootItem = new RootGroupItem();
	}
	
	/**
	 * �V�����X�j�y�b�g���J�����g�X�j�y�b�g���C�u�����ɒǉ�����B
	 * @throws SnippetsException 
	 */
	public void createNewSnippet() throws SnippetsException{
		if (currentSnippetsLibrary == null){
			throw new SnippetsException("�X�j�y�b�g���C�u�������I������Ă��܂���B");
		}
		ISnippet snippet = SnippetsBuilder.createNewSnippet();
		snippet.setSnippetsLibrary(currentSnippetsLibrary);
		currentSnippetsLibrary.addSnippet(snippet);
		snippetsWindow.selectCurrentSnippet(snippet);
		snippetsWindow.refresh();
	}
	
	/**
	 * �X�j�y�b�g���C�u���������[�J���t�@�C���ɕۑ�����B
	 * @throws IOException
	 */
	public void saveSnippetLibraryToLocalDatabase() throws IOException{
		SnippetStore snippetStore = new SnippetStore();
		snippetStore.saveLocalDatabase(snippetsLibraries);
	}
	
	/**
	 * �X�j�y�b�g���C�u���������[�J���t�@�C������ǂݍ��ށB
	 * @throws IOException
	 */
	public void loadSnippetLibraryFromLocalDatabase() throws IOException{
		SnippetStore snippetStore = new SnippetStore();
		try{
			snippetsLibraries = snippetStore.loadLocalDatabase();			
		}catch(FileNotFoundException e){
			log.error("���[�J���X�j�y�b�g���C�u�����f�[�^�x�[�X�t�@�C����������Ȃ������B",e);
			/*
			List<IGroupItem> list = new ArrayList<IGroupItem>();
			IGroupItem item = new SnippetsLibrary();
			item.setTitle("�}�C���C�u����");
			list.add(item);
			rootItem.setChildren(list);
			*/
		}
		if (snippetsLibraries == null){
			snippetsLibraries = new ArrayList<SnippetsLibrary>();
		}
		//List<IGroupItem> list = new ArrayList<IGroupItem>(snippetsLibraries);
		rootItem.setSnippetsLibraries(snippetsLibraries);
	}
	
	/**
	 * �X�j�y�b�g����������O���[�v���擾����B
	 * �X�j�y�b�g���O���[�v�ɏ������Ă��Ȃ��ꍇ��null��Ԃ��B
	 * 
	 * @param snippet
	 * @return
	 */
	public IGroupItem getGroupItem(ISnippet snippet){
		for(SnippetsLibrary library:snippetsLibraries){
			IGroupItem groupItem = getGroupItem(library, snippet);
			if (groupItem != null){
				return groupItem;
			}
			// �O���[�v�ɂ͏������Ă��炸�A���C�u�����ɏ������Ă���ꍇ
			if (library.getSnippets().contains(snippet)){
				return library;
			}
		}
		return null;
	}
	
	private IGroupItem getGroupItem(IGroupItem groupItem, ISnippet snippet){
		for(IGroupItem item:groupItem.getChildren()){
			if (item.getSnippets().contains(snippet)){
				return item;
			}
			IGroupItem ret = getGroupItem(item, snippet);
			if (ret != null){
				return ret;
			}
		}
		return null;
	}
	
	public ISnippet getSnippetByHashcode(int hashcode){
		for(SnippetsLibrary library:snippetsLibraries){
			for(ISnippet snippet:library.getSnippets()){
				if (snippet.hashCode() == hashcode){
					return snippet;
				}
			}
		}
		return null;
	}
	
	/*
	public List<IGroupItem> getLibraryList() {
		return libraryList;
	}

	public void setLibraryList(List<IGroupItem> libraryList) {
		this.libraryList = libraryList;
	}
	*/

	public RootGroupItem getRootItem() {
		return rootItem;
	}

	public void setRootItem(RootGroupItem rootItem) {
		this.rootItem = rootItem;
	}

	public ISnippetWindow getSnippetsWindow() {
		return snippetsWindow;
	}

	public void setSnippetsWindow(ISnippetWindow snippetsWindow) {
		this.snippetsWindow = snippetsWindow;
	}

	public List<SnippetsLibrary> getSnippetsLibraries() {
		return snippetsLibraries;
	}
	
	/**
	 * �X�j�y�b�g���C�u����ID����A�X�j�y�b�g���C�u�������擾����B
	 * 
	 * ���݂��Ȃ��ꍇ��null��Ԃ��B
	 * @param libraryId
	 * @return
	 */
	public SnippetsLibrary getSnippetsLibrary(long libraryId){
		for(SnippetsLibrary library:snippetsLibraries){
			if (library.getId() == libraryId){
				return library;
			}
		}
		return null;
	}

	public void setSnippetsLibraries(List<SnippetsLibrary> snippetsLibraries) {
		this.snippetsLibraries = snippetsLibraries;
	}

	public SnippetsLibrary getCurrentSnippetsLibrary() {
		return currentSnippetsLibrary;
	}

	public void setCurrentSnippetsLibrary(SnippetsLibrary currentSnippetsLibrary) {
		this.currentSnippetsLibrary = currentSnippetsLibrary;
	}

	
}
