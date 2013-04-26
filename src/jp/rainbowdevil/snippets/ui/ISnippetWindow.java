package jp.rainbowdevil.snippets.ui;

import jp.rainbowdevil.snippets.SnippetManager;
import jp.rainbowdevil.snippets.model.IGroupItem;
import jp.rainbowdevil.snippets.model.ISnippet;
import jp.rainbowdevil.snippets.sync.SynchronizeManager;

/**
 * アプリケーションウインドウのinterface
 * 
 * 各OSごとにUIを作成する場合は、共通の処理を定義したこのinterfaceを実装する。
 * 
 * @author kkitamura
 *
 */
public interface ISnippetWindow {
	/** 
	 *   デフォルトアプリ名 
	 * 
	 * 実際に使用する場合は getApplicationTitle()を使用すること。 */
	public static String APP_NAME = "Snippets";
	
	/** アプリケーション名を取得する */
	public String getApplicationTitle();
	
	/** スニペットを選択状態にする。 */
	public void selectCurrentSnippet(ISnippet snippet);
	
	//public void selectCurrentSnippet

	/** グループを選択状態にする。 */
	public void selectCurrentGroupItem(IGroupItem groupItem);
	
	/** スニペットの状態を再描画する。 */
	//public void notifyDataChanged();
	
	/** SnippetManagerを取得する。 */
	public SnippetManager getSnippetManager();
	
	public SynchronizeManager getSynchronizeManager();
	
	/** アプリケーションを終了する。 */
	public void exit();
	
	/** スニペットの状態を再描画する。 */
	public void refresh();
	
	/** ウインドウタイトルを設定する。
	 * 
	 *  以下のMESSAGEの部分を指定する。
	 *  Snppets - MESSAGE */
	public void setWindowTitleMessage(String titleMessage);
}
