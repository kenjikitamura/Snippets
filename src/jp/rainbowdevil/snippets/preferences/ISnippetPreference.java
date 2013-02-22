package jp.rainbowdevil.snippets.preferences;

import java.io.IOException;

public interface ISnippetPreference {
	
	/** 最後に新しいスニペットを作成する際につけた番号
	 *  新しいスニペットを作成する場合は、この番号に１を足したものを番号として設定する。 */
	public static final String NEW_SNIPPETS_NUMBER = "NEW_SNIPPETS_NUMBER";
	
	/** 前回終了時の画面サイズ(高さ) */
	public static final String LAST_WINDOW_HEIGHT = "LAST_WINDOW_HEIGHT";
	
	/** 前回終了時の画面サイズ(幅) */
	public static final String LAST_WINDOW_WIDTH = "LAST_WINDOW_WIDTH";
	
	/** 前回終了時の画面位置(X) */
	public static final String LAST_WINDOW_X = "LAST_WINDOW_X";
	
	/** 前回終了時の画面位置(Y) */
	public static final String LAST_WINDOW_Y = "LAST_WINDOW_Y"; 
	
	public void save() throws IOException;
	public boolean saveQuietly();
	
	public void load() throws IOException;
	public String getString(String key, String defaultValue);
	public int getInt(String key, int defaultValue);
	public void setValue(String key, String value);
	public void setValue(String key, int value);
}
