package jp.rainbowdevil.snippets.search;

/**
 * �������ʒʒm���X�i
 * 
 * �����G���W�����猟�����ʂ⌟���I���̒ʒm���󂯂郊�X�i
 * 
 * @author kitamura
 *
 */
public interface ISearchResultListener {
	
	/**
	 * �������ʂ̒ʒm
	 * @param item
	 */
	public void notifyResult(SearchResultItem item);
	
	/**
	 * �����I���̒ʒm
	 */
	public void finished();

}
