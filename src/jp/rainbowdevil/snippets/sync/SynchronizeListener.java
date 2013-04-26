package jp.rainbowdevil.snippets.sync;

public interface SynchronizeListener {
	public void error(String message, Throwable e);
	public void updateProgress(int current, int max);
	public void complete();
}
