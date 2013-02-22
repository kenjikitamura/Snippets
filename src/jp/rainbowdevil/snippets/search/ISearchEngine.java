package jp.rainbowdevil.snippets.search;

/**
 * �����G���W���̃C���^�[�t�F�C�X
 * 
 * ������񓯊��Ŏ��s���A���̌��ʂ�ISearchResultListener�o�R�Œʒm����B
 * �񓯊��Ō������邽�߁A�������~����ꍇ��stop���Ăяo���B
 * 
 * �X�j�y�b�g�����AJavadoc�����AGoogle�����ȂǑ����̌����G���W����\��
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
	 * �������ʂ��J�����������s����B
	 * @param item
	 */
	public void openSearchResultItem(SearchResultItem item);

}
