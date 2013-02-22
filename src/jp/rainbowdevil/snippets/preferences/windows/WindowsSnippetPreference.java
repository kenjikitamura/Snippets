package jp.rainbowdevil.snippets.preferences.windows;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.jface.preference.PreferenceStore;

import jp.rainbowdevil.snippets.preferences.ISnippetPreference;

/**
 * Windows用設定保存クラス
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
	}


	@Override
	/**
	 * 設定をファイルに保存する。
	 * 保存に失敗した場合は例外を投げる。
	 */
	public void save() throws IOException {
		preferenceStore.save();
	}
	
	@Override
	/**
	 * 設定をファイルに保存する。
	 * 例外は投げず、保存に成功するとtrue、失敗するとfalseを返す。
	 */
	public boolean saveQuietly() {
		try {
			save();
			return true;
		} catch (IOException e) {
			log.error("設定ファイルの保存に失敗。",e);
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

}
