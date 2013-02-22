package jp.rainbowdevil.snippets.search.snippets;

import jp.rainbowdevil.snippets.model.ISnippet;
import jp.rainbowdevil.snippets.search.SearchResultItem;

public class SnippetSearchEngineResult extends SearchResultItem{
	private ISnippet snippet;

	public ISnippet getSnippet() {
		return snippet;
	}

	public void setSnippet(ISnippet snippet) {
		this.snippet = snippet;
	}

}
