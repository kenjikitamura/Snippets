package jp.rainbowdevil.snippets.search;

/**
 * 検索エンジンのインターフェイス
 * 
 * 検索を非同期で実行し、その結果をISearchResultListener経由で通知する。
 * 非同期で検索するため、検索を停止する場合はstopを呼び出す。
 * 
 * スニペット検索、Javadoc検索、Google検索など多数の検索エンジンを予定
 * @author kkitamura
 *
 */
public interface ISearchEngine {
	
	/**
	 * 
	 * @param keyword
	 * @param listener
	 */
	public void search(String keyword, ISearchResultListener listener);
	public void stop();
	
	/**
	 * 検索結果を開く処理を実行する。
	 * @param item
	 */
	public void openSearchResultItem(SearchResultItem item);

}
