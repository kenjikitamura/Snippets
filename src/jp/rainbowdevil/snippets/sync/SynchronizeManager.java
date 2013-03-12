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
 * �X�j�y�b�g�̓������s���B
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
	 * ���O�C�����s���B
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
        
        log.debug("���� id="+entity.getId()+" email="+entity.getEmail()+" authenticationToken="+entity.getAuthenticationToken());
        authenticationToken = entity.getAuthenticationToken();
		return true;
	}
	
	/**
	 * �T�[�o�Ɠ������s���B
	 * @param manager
	 * @return
	 * @throws IOException 
	 */
	public boolean synchronize(SnippetManager manager, SynchronizeListener listener) {
		log.debug("�T�[�o�����J�n");
		
		// ���C�u�����ꗗ�擾 �T�[�o����擾�������C�u�������T�[�o���C�u�����Ƃ���
		if (authenticationToken == null){
			log.debug("�ŏ��Ƀ��O�C�����K�v�ł��B");
			throw new IllegalStateException("Need login.");
		}
		
//---------------------------------------
// �_�E�����[�h�t�F�[�Y!
//
// for �T�[�o���C�u�������̃��[�v{
//   if ���[�J���̃��C�u�����ƃo�[�W�������`�F�b�N���A���[�J�����Â�{
//      �T�[�o���C�u�����̃X�j�y�b�g�ꗗ�擾
//      for �T�[�o�X�j�y�b�g���̃��[�v{
//        if �T�[�o�X�j�y�b�g�ƃ��[�J���X�j�y�b�g�̃o�[�W�������`�F�b�N���A���[�J�����Â�{
//          if ���[�J���X�j�y�b�g�̕ҏW�ς݃t���O�������Ă���{
//			  �ҏW������!!
//              �� �ҏW�ς݃��[�J���X�j�y�b�g��ʖ��ɕύX���A�V�K�쐬�X�j�y�b�g�Ƃ���
//                 �T�[�o�X�j�y�b�g��ۑ�
//			}
//          �T�[�o�X�j�y�b�g�Ń��[�J���X�j�y�b�g���㏑��
//        }else if �T�[�o�X�j�y�b�g�����[�J���ɖ���{
//          �T�[�o�X�j�y�b�g�����[�J���ɕۑ�����B
//        }
//      }
//      ���[�J�����C�u�����̃o�[�W�������A�T�[�o���C�u�����̃o�[�W�����ɍX�V
//   }else if ���[�J�����C�u�����ɃT�[�o���C�u����������{
//     �T�[�o���C�u���������[�J���ɕۑ����A�X�j�y�b�g���S���_�E�����[�h���ĕۑ�����B
//   }
// }

// �����܂łŁA�T�[�o�̍X�V�����ׂă_�E�����[�h�ł��Ă���B
//---------------------------------------
//  �A�b�v���[�h�t�F�[�Y�I
//  for ���[�J�����C�u�������̃��[�v{
//    if ���[�J�����C�u�����̕ύX�ς݃t���O�������Ă���ꍇ{
//      for ���[�J���X�j�y�b�g
//        if ���[�J���X�j�y�b�g�̕ύX�ς݃t���O�������Ă���ꍇ{ 
//          �ύX�ς݃��[�J���X�j�y�b�g���A�b�v���[�h �X�V or �V�K�쐬
//        } else if ���[�J���X�j�y�b�g�̍폜�ς݃t���O�������Ă���ꍇ
//          �T�[�o�X�j�y�b�g���폜
//        }
//      }
//      ���[�J�����C�u�����̕ύX�ς݃t���O���폜
//    }else if ���[�J�����C�u�����̍폜�ς݃t���O�������Ă���ꍇ
//      �T�[�o���C�u�������폜
//    }else if ���[�J�����C�u�������V�K�쐬����Ă���ꍇ
//      ���[�J�����C�u�������A�b�v���[�h
//      ���ׂẴX�j�y�b�g���A�b�v���[�h
//    }
//  }
		
		// �_�E�����[�h�t�F�[�Y
		List<SnippetsLibrary> serverLibraries = null;
		try {
			serverLibraries = getUserLibraries();
			log.debug("�T�[�o���C�u�����ꗗ�擾 ����="+serverLibraries.size());
		} catch (IOException e) {
			if (listener != null){
				listener.error("�T�[�o���烉�C�u�����_�E�����[�h���ɃG���[����",e);
				return false;
			}
		}
		List<SnippetsLibrary> localLibraries = manager.getSnippetsLibraries();
		for (SnippetsLibrary library:localLibraries){
			log.debug("���[�J�����C�u���� title="+library.getTitle()+" id="+library.getId()+" update="+library.getUpdateCount()+" isDirty="+library.isDirty());
		}
		for (SnippetsLibrary serverLibrary:serverLibraries){
			log.debug("�T�[�o���C�u�������� �^�C�g��="+serverLibrary.getTitle()+" id="+serverLibrary.getId()+" update="+serverLibrary.getUpdateCount());
			SnippetsLibrary localLibrary = manager.getSnippetsLibrary(serverLibrary.getId());
			log.debug("id="+serverLibrary.getId()+"�̃��[�J�����C�u����="+localLibrary);
			
			// ���[�J���ƃT�[�o�œ������C�u����������
			if (localLibrary != null){
				
				// �ύX����
				if (localLibrary.getUpdateCount() == serverLibrary.getUpdateCount() && !localLibrary.isDirty()){
					log.debug("���[�J�����C�u���� �^�C�g��="+localLibrary.getTitle()+"�͕ύX�����B");
					continue;
				}
				
				// �T�[�o�ōX�V
				if (localLibrary.getUpdateCount() < serverLibrary.getUpdateCount()){
					log.debug("���[�J�����C�u���� �^�C�g��="+localLibrary.getTitle()+"�̓T�[�o��ŕύX����Ă���");
					try {
						syncServerLibrary(localLibrary, serverLibrary);
					} catch (IOException e) {
						if (listener != null){
							listener.error("�������ɃG���[", e);
							return false;
						}
					}
				}
			}else{
				// �T�[�o�ɐV�K�쐬���C�u����������
				log.debug("�T�[�o�ɐV�K�쐬���C�u���������� serverLibrary id="+serverLibrary.getId());
				List<ISnippet> snippets;
				try {
					snippets = downloadSnippets(serverLibrary);
					serverLibrary.setSnippets(snippets);  // �X�j�y�b�g�_�E�����[�h
					manager.getSnippetsLibraries().add(serverLibrary);
				} catch (IOException e) {
					log.debug("�T�[�o���C�u�����V�K�_�E�����[�h�ŁA�X�j�y�b�g�ꗗ�_�E�����[�h���ɃG���[",e);
					if (listener != null){
						listener.error("�X�j�y�b�g�ꗗ�_�E�����[�h���ɃG���[", e);
					}
				}
			}
		}
		
		// �A�b�v���[�h�t�F�[�Y
		log.debug("�A�b�v���[�h!");
		for(SnippetsLibrary localLibrary:localLibraries){
			log.debug("���C�u���� title="+localLibrary.getTitle()+" isDirty="+localLibrary.isDirty());
			//if (!localLibrary.isDirty()){
			//	continue;
			//}
			
			for (ISnippet localSnippet:localLibrary.getSnippets()){
				log.debug("  �X�j�y�b�g title="+localSnippet.getTitle()+" isDirty="+localSnippet.isDirty());
				if (!localSnippet.isDirty()){
					continue;
				}
				
				try {
					if (localSnippet.getId() == -1){
						// �V�K�쐬�X�j�y�b�g���A�b�v���[�h
						createSnippetToServer(localSnippet);
					}else{
						// �X�V���ꂽ�X�j�y�b�g���A�b�v���[�h
						updateSnippetToServer(localSnippet);
					}
				} catch (JSONException e) {
					log.error("�X�j�y�b�g�X�V���ɃG���[",e);
					listener.error("�X�j�y�b�g�X�V���ɃG���[", e);
					return false;
				} catch (IOException e) {
					log.error("�X�j�y�b�g�X�V���ɃG���[",e);
					listener.error("�X�j�y�b�g�X�V���ɃG���[", e);
					return false;
				}
			}
		}
		
		listener.complete();
		return true;
	}
	
	/**
	 * �T�[�o���C�u�����Ń��[�J�����C�u�������X�V����B
	 * @param localLibrary
	 * @param serverLibrary
	 * @throws IOException 
	 */
	private void syncServerLibrary(SnippetsLibrary localLibrary, SnippetsLibrary serverLibrary) throws IOException{
		log.debug("���C�u�����̃X�j�y�b�g�����J�n title="+localLibrary.getTitle()+" id="+localLibrary.getId()+" update="+localLibrary.getUpdateCount());
		serverLibrary.setSnippets(downloadSnippets(serverLibrary));
		List<ISnippet> serverSnippets = serverLibrary.getSnippets();
		log.debug("�T�[�o�X�j�y�b�g�ꗗ size="+serverSnippets.size());
		
		for(ISnippet serverSnippet:serverSnippets){
			ISnippet localSnippet = localLibrary.getSnippet(serverSnippet.getId());
			log.debug("serverSnippet="+serverSnippet+" localSnippet="+localSnippet);
			
			// ���[�J�����ύX����Ă��炸�A�T�[�o���V�����ꍇ
			if (localSnippet != null && !localSnippet.isDirty() && localSnippet.getUpdateCount() < serverSnippet.getUpdateCount()){
				log.debug("���[�J���ŕύX����Ă��炸�A�T�[�o���V���� Snippet="+localSnippet.getTitle()+" body="+localSnippet.getBody());
				// �T�[�o�X�j�y�b�g�Ń��[�J���X�j�y�b�g���X�V
				localLibrary.updateSnippet(serverSnippet);
			}else
			
			// ���[�J���ɑ��݂����A�T�[�o���V�����ꍇ
			if (localSnippet == null){
				log.debug("���[�J���ɑ��݂����A�T�[�o���V���� Snippet="+serverSnippet.getTitle());
				localLibrary.updateSnippet(serverSnippet);
			} else
			
			// ���[�J�����ύX����Ă���A�T�[�o���V�����ꍇ
			if (localSnippet.isDirty() && localSnippet.getUpdateCount() < serverSnippet.getUpdateCount()){
				log.warn("�R���t���N�g�I���[�J���̃X�j�y�b�g��V�K�쐬�ɕύX");
				resolveConflict(localLibrary, localSnippet);
				localLibrary.updateSnippet(serverSnippet);
								
			}
		}
		localLibrary.setUpdateCount(serverLibrary.getUpdateCount());
	}
	
	/**
	 * �R���t���N�g����������B
	 * @param snippet
	 */
	private void resolveConflict(SnippetsLibrary localLibrary, ISnippet snippet){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		// ID��-1�ɂ��AisDirty��true�ɂ��邱�ƂŐV�K�쐬�ƂȂ�
		snippet.setId(-1);
		snippet.setDirty(true);
		snippet.setTitle(snippet.getTitle()+" �R���t���N�g("+sdf.format(new Date())+")");
	}
	
	/**
	 * �T�[�o���烉�C�u�����Ɋ܂܂�邷�ׂẴX�j�y�b�g���擾����B
	 * @param library
	 * @return
	 * @throws IOException
	 */
	private List<ISnippet> downloadSnippets(SnippetsLibrary library) throws IOException{
		log.debug("���C�u����(id="+ library.getId() +")�̃X�j�y�b�g�ꗗ�擾�J�n�B");
		ServerConnection connection = createServerConnection(Method.GET);
		String url = BASE_URL + PATH_LIBRARY_SNIPETTS;
		url = replaceParameter(url, library.getId());
		InputStream inputStream = open(connection, url);
		List<ISnippet> list = JSON.decode(inputStream, (new ArrayList<Snippet>(){}).getClass().getGenericSuperclass());
		log.debug("���C�u����(id="+ library.getId() +")�̃X�j�y�b�g�ꗗ�擾�����B����="+list.size());
		for(ISnippet snippet:list){
			snippet.setSnippetsLibrary(library);
			log.debug("�X�j�y�b�g�_�E�����[�h title="+snippet.getTitle());
		}
		return list;
	}
	
	/**
	 * �T�[�o�X�j�y�b�g���A���[�J���̃X�j�y�b�g�ōX�V����B
	 * local -> UPDATE -> server
	 * 
	 * @param snippet
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	private boolean updateSnippetToServer(ISnippet snippet) throws IOException{
		log.debug("���[�J���X�j�y�b�g�̕ύX���A�b�v���[�h title="+snippet.getTitle());
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
	 * �V�K�쐬���[�J���X�j�y�b�g���T�[�o�ɃA�b�v���[�h����B
	 * local -> CREATE -> server
	 * 
	 * @param snippet
	 * @return
	 * @throws IOException
	 */
	private boolean createSnippetToServer(ISnippet snippet) throws IOException{
		log.debug("�V�K�쐬���[�J���X�j�y�b�g���A�b�v���[�h title="+snippet.getTitle());
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
		log.debug("�V�K�쐬�̃A�b�v���[�h���� �V�K�쐬�����X�j�y�b�g�̐V����ID��("+snippet.getId()+")");
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
	 * ���[�U�̃��C�u�����ꗗ���擾����B
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
