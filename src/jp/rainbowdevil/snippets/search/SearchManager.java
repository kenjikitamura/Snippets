package jp.rainbowdevil.snippets.search;

import java.util.ArrayList;
import java.util.List;

import jp.rainbowdevil.snippets.SnippetManager;
import jp.rainbowdevil.snippets.search.snippets.SnippetSearchEngine;
import jp.rainbowdevil.snippets.search.snippets.TestSearchEngine;
import jp.rainbowdevil.snippets.ui.ISnippetWindow;

/**
 * 検索機能管理クラス
 * @author kitamura
 *
 */
public class SearchManager {
	
	private List<ISearchEngine> searchEngines;
	//private SnippetManager snippetManager;
	
	public SearchManager(ISnippetWindow snippetWindow){
		searchEngines = new ArrayList<ISearchEngine>();
		SnippetSearchEngine snippetSearchEngine = new SnippetSearchEngine();
		snippetSearchEngine.setSnippetWindow(snippetWindow);
		searchEngines.add(snippetSearchEngine);
		searchEngines.add(new TestSearchEngine());
	}
	
	public void search(String keyword, ISearchResultListener listener){
		for(ISearchEngine engine:searchEngines){
			engine.search(keyword, listener);
		}
	}
}
