package jp.rainbowdevil.snippets.model;

import java.util.ArrayList;
import java.util.List;

public class RootGroupItem{
	private List<SnippetsLibrary> snippetsLibraries;
	
	public RootGroupItem(){
		snippetsLibraries = new ArrayList<SnippetsLibrary>();
	}

	public List<SnippetsLibrary> getSnippetsLibraries() {
		return snippetsLibraries;
	}

	public void setSnippetsLibraries(List<SnippetsLibrary> snippetsLibraries) {
		this.snippetsLibraries = snippetsLibraries;
	}

}
