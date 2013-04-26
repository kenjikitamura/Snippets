package jp.rainbowdevil.snippets.search;

/**
 * 検索結果クラス
 * 
 * 検索エンジンで検索された結果１件を表すクラスで、アイコンやタイトルなどを保持する。
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
