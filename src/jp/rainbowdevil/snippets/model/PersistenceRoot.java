package jp.rainbowdevil.snippets.model;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class PersistenceRoot {
	
	@ElementList
	private List<SnippetsLibrary> libraries;

	public List<SnippetsLibrary> getLibraries() {
		return libraries;
	}

	public void setLibraries(List<SnippetsLibrary> libraries) {
		this.libraries = libraries;
	}

}
