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
	
	/** 現在有効なカレントスニペットライブラリ
	 *  新規スニペットの追加はこのカレントスニペットライブラリに対して行う。 */
	private SnippetsLibrary currentSnippetsLibrary;
	
	/** 現在選択されているグループ */
	private IGroupItem currentGroupItem;
	
	public SnippetManager(){
		//snippetsLibraries = TestData.getTestData();		
		rootItem = new RootGroupItem();
	}
	
	/**
	 * 新しいスニペットをカレントスニペットライブラリに追加する。
	 * @throws SnippetsException 
	 */
	public void createNewSnippet() throws SnippetsException{
		if (currentSnippetsLibrary == null){
			throw new SnippetsException("スニペットライブラリが選択されていません。");
		}
		ISnippet snippet = SnippetsBuilder.createNewSnippet();
		snippet.setSnippetsLibrary(currentSnippetsLibrary);
		currentSnippetsLibrary.addSnippet(snippet);
		snippetsWindow.selectCurrentSnippet(snippet);
		snippetsWindow.refresh();
	}
	
	/**
	 * スニペットライブラリをローカルファイルに保存する。
	 * @throws IOException
	 */
	public void saveSnippetLibraryToLocalDatabase() throws IOException{
		SnippetStore snippetStore = new SnippetStore();
		snippetStore.saveLocalDatabase(snippetsLibraries);
	}
	
	/**
	 * スニペットライブラリをローカルファイルから読み込む。
	 * @throws IOException
	 */
	public void loadSnippetLibraryFromLocalDatabase() throws IOException{
		SnippetStore snippetStore = new SnippetStore();
		try{
			snippetsLibraries = snippetStore.loadLocalDatabase();			
		}catch(FileNotFoundException e){
			log.error("ローカルスニペットライブラリデータベースファイルが見つからなかった。",e);
			/*
			List<IGroupItem> list = new ArrayList<IGroupItem>();
			IGroupItem item = new SnippetsLibrary();
			item.setTitle("マイライブラリ");
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
	 * スニペットが所属するグループを取得する。
	 * スニペットがグループに所属していない場合はnullを返す。
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
			// グループには所属しておらず、ライブラリに所属している場合
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
	 * スニペットライブラリIDから、スニペットライブラリを取得する。
	 * 
	 * 存在しない場合はnullを返す。
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
