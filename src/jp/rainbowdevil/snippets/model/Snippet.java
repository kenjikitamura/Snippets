package jp.rainbowdevil.snippets.model;

public class Snippet implements ISnippet{
	private String title;
	private String text;
	
	@Override
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
