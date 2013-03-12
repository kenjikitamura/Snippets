package jp.rainbowdevil.snippets.preferences.windows;

import java.io.IOException;

import jp.rainbowdevil.snippets.preferences.ISnippetPreference;

import org.apache.log4j.Logger;
import org.eclipse.jface.preference.PreferenceStore;

/**
 * Windows�p�ݒ�ۑ��N���X
 * @author kkitamura
 *
 */
public class WindowsSnippetPreference implements ISnippetPreference{
	protected static Logger log = Logger
			.getLogger(WindowsSnippetPreference.class);
	
	private PreferenceStore preferenceStore;
	
	private static final String FILENAME = "snippets.pref";
	
	public WindowsSnippetPreference() {
		preferenceStore = new PreferenceStore(FILENAME);
		initDefaultValue();
	}
	
	private void initDefaultValue(){
		// �f���p�����l
		if (!preferenceStore.contains(ISnippetPreference.ACCOUNT_EMAIL)){
			preferenceStore.setValue(ISnippetPreference.ACCOUNT_EMAIL, "demo@example.com");
			preferenceStore.setValue(ISnippetPreference.ACCOUNT_PASSWORD, "demo");
		}
		
		// �����l
		if (!preferenceStore.contains(ISnippetPreference.CONNECTION_USE_PROXY)){
			preferenceStore.setValue(ISnippetPreference.CONNECTION_PROXY_SERVER, "proxy.mei.co.jp");
			preferenceStore.setValue(ISnippetPreference.CONNECTION_PROXY_PORT, 8080);
		}
	}

	@Override
	/**
	 * �ݒ���t�@�C���ɕۑ�����B
	 * �ۑ��Ɏ��s�����ꍇ�͗�O�𓊂���B
	 */
	public void save() throws IOException {
		preferenceStore.save();
	}
	
	@Override
	/**
	 * �ݒ���t�@�C���ɕۑ�����B
	 * ��O�͓������A�ۑ��ɐ��������true�A���s�����false��Ԃ��B
	 */
	public boolean saveQuietly() {
		try {
			save();
			return true;
		} catch (IOException e) {
			log.error("�ݒ�t�@�C���̕ۑ��Ɏ��s�B",e);
			return false;
		}
	}

	@Override
	public void load() throws IOException {
		preferenceStore.load();		
	}

	@Override
	public String getString(String key, String defaultValue) {
		if (!preferenceStore.contains(key)){
			return defaultValue;
		}
		return preferenceStore.getString(key);
	}

	@Override
	public int getInt(String key, int defaultValue) {
		if (!preferenceStore.contains(key)){
			return defaultValue;
		}
		return preferenceStore.getInt(key);
	}

	@Override
	public void setValue(String key, String value) {
		preferenceStore.putValue(key, value);
	}

	@Override
	public void setValue(String key, int value) {
		preferenceStore.putValue(key, String.valueOf(value));		
	}
	
	@Override
	public boolean getBoolean(String key) {
 		return preferenceStore.getBoolean(key);
	}

	/**
	 * PreferenceStore���擾����B
	 * PreferenceDialog�p
	 * @return
	 */
	public PreferenceStore getPreferenceStore(){
		return preferenceStore;
	}
}
