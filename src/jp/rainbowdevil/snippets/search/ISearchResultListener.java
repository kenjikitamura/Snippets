package jp.rainbowdevil.snippets.search;

/**
 * 検索結果通知リスナ
 * 
 * 検索エンジンから検索結果や検索終了の通知を受けるリスナ
 * 
 * @author kitamura
 *
 */
public interface ISearchResultListener {
	
	/**
	 * 検索結果の通知
	 * @param item
	 */
	public void notifyResult(SearchResultItem item);
	
	/**
	 * 検索終了の通知
	 */
	public void finished();

}
