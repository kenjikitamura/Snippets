package jp.rainbowdevil.snippets.sync;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.Proxy.Type;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ServerConnection {
	
	protected static org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(ServerConnection.class);
	
	private Map<String, String> params;
	
	enum Method{
		GET,
		POST,
		PUT,
		DELETE
	}
	
	private Method method;
	
	public ServerConnection(Method method){
		params = new HashMap<String, String>();
		this.method = method;
	}
	
	public InputStream getInputStream(String strUrl, String proxyServer, int proxyPort) throws IOException {
		
		if (method == Method.GET){
			if (params != null) {
				StringBuilder sb = new StringBuilder();
				for (String key : params.keySet()) {
					if (sb.length() != 0) {
						sb.append("&");
					}else{
						sb.append("?");
					}
					sb.append(key + "=" + params.get(key));
				}
				strUrl = strUrl + sb.toString();
			}			 
		}
		
		log.debug("サーバ接続URL = "+strUrl);
		URL url = new URL(strUrl);
		HttpURLConnection connection = null;
		if (proxyServer != null) {
			Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress(
					proxyServer, proxyPort));
			connection = (HttpURLConnection)url.openConnection(proxy);
		} else {
			connection = (HttpURLConnection)url.openConnection();
		}
		
		connection.setRequestProperty("User-Agent",
				"Rainbowdevil Snippet client library");

		if (method == Method.POST || method == Method.PUT) {
			
			if (method == Method.PUT){
				connection.setRequestMethod("PUT");
			}
			// POST
			connection.setDoOutput(true);		
			connection.setRequestProperty("Content-Type", "multipart/form-data;");

			StringBuilder sb = new StringBuilder();
			OutputStream os = connection.getOutputStream();

			if (params != null) {
				for (String key : params.keySet()) {
					if (sb.length() != 0) {
						sb.append("&");
					}
					String value = params.get(key);
					if (value == null){
						continue;
					}
					sb.append(URLEncoder.encode(key,"UTF-8") + "=" + URLEncoder.encode(value,"UTF-8"));
				}
			}
			PrintStream ps = new PrintStream(os,true,"UTF-8");
			ps.print(sb.toString());
			ps.close();
			log.debug(method+"パラメータ書き込み "+sb.toString());
		}
		
		InputStream is = connection.getInputStream();
		return is;
	}
	
	public void addParameter(String key, String value){
		params.put(key, value);
	}
	
	public InputStream getInputStream(String url) throws IOException{
		return getInputStream(url, null, 0);
	}
}
