package jp.rainbowdevil.snippets.sync;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.rainbowdevil.snippets.model.SnippetsLibrary;
import jp.rainbowdevil.snippets.sync.ServerConnection.Method;

import org.eclipse.swt.internal.Library;

import net.arnx.jsonic.JSON;

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
		return connection.getInputStream(strUrl, proxyServer, proxyPort);
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
