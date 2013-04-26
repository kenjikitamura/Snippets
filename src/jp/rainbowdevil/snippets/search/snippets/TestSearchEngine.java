package jp.rainbowdevil.snippets.search.snippets;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;

import jp.rainbowdevil.snippets.search.AbstractSearchEngine;
import jp.rainbowdevil.snippets.search.ISearchResultListener;
import jp.rainbowdevil.snippets.search.SearchResultItem;

public class TestSearchEngine extends AbstractSearchEngine {
	protected static Logger log = Logger.getLogger(TestSearchEngine.class);

	@Override
	public void search(String keyword, ISearchResultListener listener) {
		SearchResultItem item;
		item = new SearchResultItem();
		item.setBody("ほげbody");
		item.setTitle("ほげtitle");
		item.setSearchEngine(this);
		listener.notifyResult(item);
		
		item = new SearchResultItem();
		item.setSearchEngine(this);
		item.setBody("ほげbody2");
		item.setTitle("ほげtitle2");
		listener.notifyResult(item);
		
		listener.finished();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void openSearchResultItem(SearchResultItem item) {
		try {
			java.awt.Desktop.getDesktop().browse(new URI("http://www.google.com"));
		} catch (IOException e) {
			log.error(e);
		} catch (URISyntaxException e) {
			log.error(e);
		}
	}

}
