package jp.rainbowdevil.snippets.search.snippets;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;

import jp.rainbowdevil.snippets.SnippetManager;
import jp.rainbowdevil.snippets.model.IGroupItem;
import jp.rainbowdevil.snippets.model.ISnippet;
import jp.rainbowdevil.snippets.model.Snippet;
import jp.rainbowdevil.snippets.model.SnippetsLibrary;
import jp.rainbowdevil.snippets.search.AbstractSearchEngine;
import jp.rainbowdevil.snippets.search.ISearchResultListener;
import jp.rainbowdevil.snippets.search.SearchResultItem;
import jp.rainbowdevil.snippets.ui.ISnippetWindow;
import jp.rainbowdevil.snippets.ui.windows.WindowsSnippetWindow;

/**
 * スニペット検索エンジン
 * 
 */
public class SnippetSearchEngine extends AbstractSearchEngine{
	protected static Logger log = Logger.getLogger( SnippetSearchEngine.class );
	//private SnippetManager snippetManager;
	private ISnippetWindow snippetWindow;
	
	@Override
	public void search(String keyword, ISearchResultListener listener) {
		if (keyword == null || keyword.trim().length() == 0){
			return;
		}
		String lowerCaseKeyword = keyword.toLowerCase();
		List<SnippetsLibrary> libraries = snippetWindow.getSnippetManager().getSnippetsLibraries();
		for(SnippetsLibrary library:libraries){
			List<ISnippet> list = library.getSnippets();
			for(ISnippet snippet:list){
				//log.debug("スニペット検索 title="+snippet.getTitle().toLowerCase()+" body="+ snippet.getBody().toLowerCase()+" contains="+lowerCaseKeyword);
				if (snippet.getTitle().toLowerCase().contains(lowerCaseKeyword) ||
					snippet.getBody().toLowerCase().contains(lowerCaseKeyword)	){
					SnippetSearchEngineResult item = new SnippetSearchEngineResult();
					item.setBody(snippet.getBody());
					item.setTitle(snippet.getTitle());
					item.setSnippet(snippet);
					item.setSearchEngine(this);
					listener.notifyResult(item);
				}
			}
		}
		/*
		SearchResultItem item;
		item = new SearchResultItem();
		item.setBody("ほげbody");
		item.setTitle("ほげtitle");
		listener.notifyResult(item);
		
		item = new SearchResultItem();
		item.setBody("ほげbody2");
		item.setTitle("ほげtitle2");
		listener.notifyResult(item);
		*/
		listener.finished();
	}
	
	@Override
	public void openSearchResultItem(SearchResultItem item) {
		log.debug("選択 "+item.getTitle());
		SnippetSearchEngineResult snippetSearchEngineResult = (SnippetSearchEngineResult)item;
		
		IGroupItem groupItem = snippetWindow.getSnippetManager().getGroupItem(snippetSearchEngineResult.getSnippet());
		if (groupItem != null){
			snippetWindow.selectCurrentGroupItem(groupItem);
		}
		snippetWindow.selectCurrentSnippet(snippetSearchEngineResult.getSnippet());
	}

	@Override
	public void stop() {

	}

	public ISnippetWindow getSnippetWindow() {
		return snippetWindow;
	}

	public void setSnippetWindow(ISnippetWindow snippetWindow) {
		this.snippetWindow = snippetWindow;
	}

}