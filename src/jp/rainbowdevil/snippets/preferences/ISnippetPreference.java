package jp.rainbowdevil.snippets.preferences;

import java.io.IOException;

public interface ISnippetPreference {
	
	/** �Ō�ɐV�����X�j�y�b�g���쐬����ۂɂ����ԍ�
	 *  �V�����X�j�y�b�g���쐬����ꍇ�́A���̔ԍ��ɂP�𑫂������̂�ԍ��Ƃ��Đݒ肷��B */
	public static final String NEW_SNIPPETS_NUMBER = "NEW_SNIPPETS_NUMBER";
	
	/** �O��I�����̉�ʃT�C�Y(����) */
	public static final String LAST_WINDOW_HEIGHT = "LAST_WINDOW_HEIGHT";
	
	/** �O��I�����̉�ʃT�C�Y(��) */
	public static final String LAST_WINDOW_WIDTH = "LAST_WINDOW_WIDTH";
	
	/** �O��I�����̉�ʈʒu(X) */
	public static final String LAST_WINDOW_X = "LAST_WINDOW_X";
	
	/** �O��I�����̉�ʈʒu(Y) */
	public static final String LAST_WINDOW_Y = "LAST_WINDOW_Y"; 
	
	public void save() throws IOException;
	public boolean saveQuietly();
	
	public void load() throws IOException;
	public String getString(String key, String defaultValue);
	public int getInt(String key, int defaultValue);
	public void setValue(String key, String value);
	public void setValue(String key, int value);
}