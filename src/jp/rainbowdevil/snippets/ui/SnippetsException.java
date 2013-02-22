package jp.rainbowdevil.snippets.ui;

public class SnippetsException extends Exception {
	public SnippetsException(String msg){
		super(msg);
	}
	public SnippetsException(String msg, Throwable e) {
		super(msg, e);
	}
}
