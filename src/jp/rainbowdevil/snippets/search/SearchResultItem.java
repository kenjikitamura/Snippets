package jp.rainbowdevil.snippets.search;

/**
 * �������ʃN���X
 * 
 * �����G���W���Ō������ꂽ���ʂP����\���N���X�ŁA�A�C�R����^�C�g���Ȃǂ�ێ�����B
 * 
 * @author kitamura
 *
 */
public class SearchResultItem {
	
	private String title;
	private String body;
	private ISearchEngine searchEngine;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public ISearchEngine getSearchEngine() {
		return searchEngine;
	}
	public void setSearchEngine(ISearchEngine searchEngine) {
		this.searchEngine = searchEngine;
	}

	public void openSearchResultItem(){
		searchEngine.openSearchResultItem(this);
	}
}
