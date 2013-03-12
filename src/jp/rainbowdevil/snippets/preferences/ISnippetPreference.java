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
	
	/** アカウント情報 パスワード */
	public static final String ACCOUNT_PASSWORD = "account.password";
	
	/** アカウント情報 メールアドレス */
	public static final String ACCOUNT_EMAIL = "account.email";
	
	/** アカウント情報 認証トークン */
	public static final String AUTHENTICATION_TOKEN = "account.authentication_token";
	
	public static final String CONNECTION_USE_PROXY = "connection.use_proxy";
	public static final String CONNECTION_PROXY_SERVER = "connection.proxy.server.address";
	public static final String CONNECTION_PROXY_PORT = "connection.proxy.server.port";

	public void save() throws IOException;
	public boolean saveQuietly();
	
	public void load() throws IOException;
	public String getString(String key, String defaultValue);
	public int getInt(String key, int defaultValue);
	public boolean getBoolean(String key);
	public void setValue(String key, String value);
	public void setValue(String key, int value);
}
