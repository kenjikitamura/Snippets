package jp.rainbowdevil.snippets.sync;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.rainbowdevil.snippets.SnippetManager;
import jp.rainbowdevil.snippets.model.ISnippet;
import jp.rainbowdevil.snippets.model.Snippet;
import jp.rainbowdevil.snippets.model.SnippetsLibrary;
import jp.rainbowdevil.snippets.preferences.ISnippetPreference;
import jp.rainbowdevil.snippets.preferences.PreferencesBuilder;
import jp.rainbowdevil.snippets.sync.ServerConnection.Method;
import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;

/**
 * スニペットの同期を行う。
 * @author kkitamura
 *
 */
public class SynchronizeManager {
	protected static org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(SynchronizeManager.class);
	
	private String authenticationToken;
	private boolean useProxy = true;
	private String proxyServer = "proxy.mei.co.jp";
	private int proxyPort = 8080; 
	
	private String BASE_URL = "http://rainbowdevil.jp:4005/api";
	private String PATH_LOGIN = "/session";
	private String PATH_USER_LIBRARIES = "/libraries";
	private String PATH_LIBRARY_SNIPETTS = "/libraries/{?}/snippets";
	private String PATH_UPDATE_SNIPPET = "/snippets/";
	
	/**
	 * ログインを行う。
	 * 
	 * @param email
	 * @param password
	 * @return
	 * @throws IOException
	 */
	public boolean login(String email, String password) throws IOException{
		ServerConnection connection =createServerConnection(Method.POST);

		String strUrl = BASE_URL + PATH_LOGIN;
		
		connection.addParameter("email", email);
		connection.addParameter("password", password);

		InputStream inputStream = open(connection, strUrl);
		
		AuthencationResultEntity entity = JSON.decode(inputStream,AuthencationResultEntity.class);
        
        log.debug("結果 id="+entity.getId()+" email="+entity.getEmail()+" authenticationToken="+entity.getAuthenticationToken());
        authenticationToken = entity.getAuthenticationToken();
		return true;
	}
	
	/**
	 * サーバと同期を行う。
	 * @param manager
	 * @return
	 * @throws IOException 
	 */
	public boolean synchronize(SnippetManager manager, SynchronizeListener listener) {
		log.debug("サーバ同期開始");
		
		// ライブラリ一覧取得 サーバから取得したライブラリをサーバライブラリとする
		if (authenticationToken == null){
			log.debug("最初にログインが必要です。");
			throw new IllegalStateException("Need login.");
		}
		
//---------------------------------------
// ダウンロードフェーズ!
//
// for サーバライブラリ毎のループ{
//   if ローカルのライブラリとバージョンをチェックし、ローカルが古い{
//      サーバライブラリのスニペット一覧取得
//      for サーバスニペット毎のループ{
//        if サーバスニペットとローカルスニペットのバージョンをチェックし、ローカルが古い{
//          if ローカルスニペットの編集済みフラグが立っている{
//			  編集が競合!!
//              → 編集済みローカルスニペットを別名に変更し、新規作成スニペットとする
//                 サーバスニペットを保存
//			}
//          サーバスニペットでローカルスニペットを上書き
//        }else if サーバスニペットがローカルに無い{
//          サーバスニペットをローカルに保存する。
//        }
//      }
//      ローカルライブラリのバージョンを、サーバライブラリのバージョンに更新
//   }else if ローカルライブラリにサーバライブラリが無い{
//     サーバライブラリをローカルに保存し、スニペットも全部ダウンロードして保存する。
//   }
// }

// ここまでで、サーバの更新がすべてダウンロードできている。
//---------------------------------------
//  アップロードフェーズ！
//  for ローカルライブラリ毎のループ{
//    if ローカルライブラリの変更済みフラグがたっている場合{
//      for ローカルスニペット
//        if ローカルスニペットの変更済みフラグが立っている場合{ 
//          変更済みローカルスニペットをアップロード 更新 or 新規作成
//        } else if ローカルスニペットの削除済みフラグが立っている場合
//          サーバスニペットを削除
//        }
//      }
//      ローカルライブラリの変更済みフラグを削除
//    }else if ローカルライブラリの削除済みフラグが立っている場合
//      サーバライブラリを削除
//    }else if ローカルライブラリが新規作成されている場合
//      ローカルライブラリをアップロード
//      すべてのスニペットをアップロード
//    }
//  }
		
		// ダウンロードフェーズ
		List<SnippetsLibrary> serverLibraries = null;
		try {
			serverLibraries = getUserLibraries();
			log.debug("サーバライブラリ一覧取得 件数="+serverLibraries.size());
		} catch (IOException e) {
			if (listener != null){
				listener.error("サーバからライブラリダウンロード時にエラー発生",e);
				return false;
			}
		}
		List<SnippetsLibrary> localLibraries = manager.getSnippetsLibraries();
		for (SnippetsLibrary library:localLibraries){
			log.debug("ローカルライブラリ title="+library.getTitle()+" id="+library.getId()+" update="+library.getUpdateCount()+" isDirty="+library.isDirty());
		}
		for (SnippetsLibrary serverLibrary:serverLibraries){
			log.debug("サーバライブラリ同期 タイトル="+serverLibrary.getTitle()+" id="+serverLibrary.getId()+" update="+serverLibrary.getUpdateCount());
			SnippetsLibrary localLibrary = manager.getSnippetsLibrary(serverLibrary.getId());
			log.debug("id="+serverLibrary.getId()+"のローカルライブラリ="+localLibrary);
			
			// ローカルとサーバで同じライブラリが存在
			if (localLibrary != null){
				
				// 変更無し
				if (localLibrary.getUpdateCount() == serverLibrary.getUpdateCount() && !localLibrary.isDirty()){
					log.debug("ローカルライブラリ タイトル="+localLibrary.getTitle()+"は変更無し。");
					continue;
				}
				
				// サーバで更新
				if (localLibrary.getUpdateCount() < serverLibrary.getUpdateCount()){
					log.debug("ローカルライブラリ タイトル="+localLibrary.getTitle()+"はサーバ上で変更されている");
					try {
						syncServerLibrary(localLibrary, serverLibrary);
					} catch (IOException e) {
						if (listener != null){
							listener.error("同期中にエラー", e);
							return false;
						}
					}
				}
			}else{
				// サーバに新規作成ライブラリがある
				log.debug("サーバに新規作成ライブラリがある serverLibrary id="+serverLibrary.getId());
				List<ISnippet> snippets;
				try {
					snippets = downloadSnippets(serverLibrary);
					serverLibrary.setSnippets(snippets);  // スニペットダウンロード
					manager.getSnippetsLibraries().add(serverLibrary);
				} catch (IOException e) {
					log.debug("サーバライブラリ新規ダウンロードで、スニペット一覧ダウンロード時にエラー",e);
					if (listener != null){
						listener.error("スニペット一覧ダウンロード時にエラー", e);
					}
				}
			}
		}
		
		// アップロードフェーズ
		log.debug("アップロード!");
		for(SnippetsLibrary localLibrary:localLibraries){
			log.debug("ライブラリ title="+localLibrary.getTitle()+" isDirty="+localLibrary.isDirty());
			//if (!localLibrary.isDirty()){
			//	continue;
			//}
			
			for (ISnippet localSnippet:localLibrary.getSnippets()){
				log.debug("  スニペット title="+localSnippet.getTitle()+" isDirty="+localSnippet.isDirty());
				if (!localSnippet.isDirty()){
					continue;
				}
				
				try {
					if (localSnippet.getId() == -1){
						// 新規作成スニペットをアップロード
						createSnippetToServer(localSnippet);
					}else{
						// 更新されたスニペットをアップロード
						updateSnippetToServer(localSnippet);
					}
				} catch (JSONException e) {
					log.error("スニペット更新時にエラー",e);
					listener.error("スニペット更新時にエラー", e);
					return false;
				} catch (IOException e) {
					log.error("スニペット更新時にエラー",e);
					listener.error("スニペット更新時にエラー", e);
					return false;
				}
			}
		}
		
		listener.complete();
		return true;
	}
	
	/**
	 * サーバライブラリでローカルライブラリを更新する。
	 * @param localLibrary
	 * @param serverLibrary
	 * @throws IOException 
	 */
	private void syncServerLibrary(SnippetsLibrary localLibrary, SnippetsLibrary serverLibrary) throws IOException{
		log.debug("ライブラリのスニペット同期開始 title="+localLibrary.getTitle()+" id="+localLibrary.getId()+" update="+localLibrary.getUpdateCount());
		serverLibrary.setSnippets(downloadSnippets(serverLibrary));
		List<ISnippet> serverSnippets = serverLibrary.getSnippets();
		log.debug("サーバスニペット一覧 size="+serverSnippets.size());
		
		for(ISnippet serverSnippet:serverSnippets){
			ISnippet localSnippet = localLibrary.getSnippet(serverSnippet.getId());
			log.debug("serverSnippet="+serverSnippet+" localSnippet="+localSnippet);
			
			// ローカルが変更されておらず、サーバが新しい場合
			if (localSnippet != null && !localSnippet.isDirty() && localSnippet.getUpdateCount() < serverSnippet.getUpdateCount()){
				log.debug("ローカルで変更されておらず、サーバが新しい Snippet="+localSnippet.getTitle()+" body="+localSnippet.getBody());
				// サーバスニペットでローカルスニペットを更新
				localLibrary.updateSnippet(serverSnippet);
			}else
			
			// ローカルに存在せず、サーバが新しい場合
			if (localSnippet == null){
				log.debug("ローカルに存在せず、サーバが新しい Snippet="+serverSnippet.getTitle());
				localLibrary.updateSnippet(serverSnippet);
			} else
			
			// ローカルが変更されており、サーバも新しい場合
			if (localSnippet.isDirty() && localSnippet.getUpdateCount() < serverSnippet.getUpdateCount()){
				log.warn("コンフリクト！ローカルのスニペットを新規作成に変更");
				resolveConflict(localLibrary, localSnippet);
				localLibrary.updateSnippet(serverSnippet);
								
			}
		}
		localLibrary.setUpdateCount(serverLibrary.getUpdateCount());
	}
	
	/**
	 * コンフリクトを解決する。
	 * @param snippet
	 */
	private void resolveConflict(SnippetsLibrary localLibrary, ISnippet snippet){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		// IDを-1にし、isDirtyをtrueにすることで新規作成となる
		snippet.setId(-1);
		snippet.setDirty(true);
		snippet.setTitle(snippet.getTitle()+" コンフリクト("+sdf.format(new Date())+")");
	}
	
	/**
	 * サーバからライブラリに含まれるすべてのスニペットを取得する。
	 * @param library
	 * @return
	 * @throws IOException
	 */
	private List<ISnippet> downloadSnippets(SnippetsLibrary library) throws IOException{
		log.debug("ライブラリ(id="+ library.getId() +")のスニペット一覧取得開始。");
		ServerConnection connection = createServerConnection(Method.GET);
		String url = BASE_URL + PATH_LIBRARY_SNIPETTS;
		url = replaceParameter(url, library.getId());
		InputStream inputStream = open(connection, url);
		List<ISnippet> list = JSON.decode(inputStream, (new ArrayList<Snippet>(){}).getClass().getGenericSuperclass());
		log.debug("ライブラリ(id="+ library.getId() +")のスニペット一覧取得完了。件数="+list.size());
		for(ISnippet snippet:list){
			snippet.setSnippetsLibrary(library);
			log.debug("スニペットダウンロード title="+snippet.getTitle());
		}
		return list;
	}
	
	/**
	 * サーバスニペットを、ローカルのスニペットで更新する。
	 * local -> UPDATE -> server
	 * 
	 * @param snippet
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	private boolean updateSnippetToServer(ISnippet snippet) throws IOException{
		log.debug("ローカルスニペットの変更をアップロード title="+snippet.getTitle());
		ServerConnection connection = createServerConnection(Method.PUT);
		String url = BASE_URL + PATH_UPDATE_SNIPPET+snippet.getId();
		
		connection.addParameter("author", snippet.getAuthor());
		connection.addParameter("body", snippet.getBody());
		connection.addParameter("notes", snippet.getNotes());
		connection.addParameter("related_url", snippet.getRelatedUrl());
		connection.addParameter("title", snippet.getTitle());
		connection.addParameter("is_deleted", snippet.isDeleted() ? "1":"0");
		
		InputStream inputStream = open(connection, url);
		ISnippet updatedSnippet = JSON.decode(inputStream, Snippet.class);
		snippet.setUpdateCount(updatedSnippet.getUpdateCount());
		snippet.setDirty(false);
		return true;		
	}
	
	/**
	 * 新規作成ローカルスニペットをサーバにアップロードする。
	 * local -> CREATE -> server
	 * 
	 * @param snippet
	 * @return
	 * @throws IOException
	 */
	private boolean createSnippetToServer(ISnippet snippet) throws IOException{
		log.debug("新規作成ローカルスニペットをアップロード title="+snippet.getTitle());
		ServerConnection connection = createServerConnection(Method.POST);
		String url = BASE_URL + PATH_UPDATE_SNIPPET;
		
		connection.addParameter("author", snippet.getAuthor());
		connection.addParameter("body", snippet.getBody());
		connection.addParameter("notes", snippet.getNotes());
		connection.addParameter("related_url", snippet.getRelatedUrl());
		connection.addParameter("title", snippet.getTitle());
		connection.addParameter("is_deleted", snippet.isDeleted() ? "1":"0");
		connection.addParameter("library_id", String.valueOf(snippet.getSnippetsLibrary().getId()));
		
		InputStream inputStream = open(connection, url);
		ISnippet updatedSnippet = JSON.decode(inputStream, Snippet.class);
		snippet.setId(updatedSnippet.getId());
		snippet.setUpdateCount(updatedSnippet.getUpdateCount());
		snippet.setDirty(false);
		log.debug("新規作成のアップロード完了 新規作成したスニペットの新しいIDは("+snippet.getId()+")");
		return true;
	}
	
	private String replaceParameter(String text,Object... args){
		String ret = text;
		for(Object arg:args){
			ret = ret.replace("{?}", arg.toString());
		}
		return ret;
	}
	
	/**
	 * ユーザのライブラリ一覧を取得する。
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("serial")
	public List<SnippetsLibrary> getUserLibraries() throws IOException{
		ServerConnection connection = createServerConnection(Method.GET);
		InputStream inputStream = open(connection, BASE_URL + PATH_USER_LIBRARIES);
		List<SnippetsLibrary> list = JSON.decode(inputStream, (new ArrayList<SnippetsLibrary>(){}).getClass().getGenericSuperclass());
		return list;
	}
	
	private ServerConnection createServerConnection(Method method){
		ServerConnection connection = new ServerConnection(method);
		connection.addParameter("authentication_token", authenticationToken);
		return connection;
	}
	
	private InputStream open(ServerConnection connection, String strUrl) throws IOException{
		ISnippetPreference preference = PreferencesBuilder.getSnippetPreference();
		boolean useProxy = preference.getBoolean(ISnippetPreference.CONNECTION_USE_PROXY);
		if (useProxy){
			String proxyServerAddress = preference.getString(ISnippetPreference.CONNECTION_PROXY_SERVER, null);
			int proxyServerPort = preference.getInt(ISnippetPreference.CONNECTION_PROXY_PORT, 0);
			return connection.getInputStream(strUrl, proxyServer, proxyPort);
		}else{		
			return connection.getInputStream(strUrl);
		}
	}
	
	private String toString(InputStream inputStream) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while((line = br.readLine()) != null){
			sb.append(line);
			sb.append("\n");
		}
		return sb.toString();
	}

}
