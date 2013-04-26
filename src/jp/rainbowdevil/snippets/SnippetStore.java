package jp.rainbowdevil.snippets;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.CycleStrategy;
import org.simpleframework.xml.strategy.Strategy;

import jp.rainbowdevil.snippets.model.PersistenceRoot;
import jp.rainbowdevil.snippets.model.SnippetsLibrary;

/**
 * ライブラリの永続化を行うクラス
 * @author kkitamura
 *
 */
public class SnippetStore {
	protected static Logger log = Logger.getLogger(SnippetStore.class);
	
	/** ローカルデータベースのファイル名 */
	public static final String FILENAME = "library.xml";
	
	public boolean saveLocalDatabase(List<SnippetsLibrary> libraries) throws IOException{
		Strategy strategy = new CycleStrategy("id", "ref");
		Serializer serializer = new Persister(strategy);
		File result = new File(FILENAME);
		
		PersistenceRoot root = new PersistenceRoot();
		root.setLibraries(libraries);
		try {
			serializer.write(root, result);
			log.debug("書き込み完了");
			return true;
		} catch (IOException e){
			throw e;
		} catch (Exception e) {
			log.error("スニペットライブラリの保存に失敗",e);
			return false;
		}
	}
	
	public List<SnippetsLibrary> loadLocalDatabase() throws IOException{
		Strategy strategy = new CycleStrategy("id", "ref");
		Serializer serializer = new Persister(strategy);
		File result = new File(FILENAME);
		
		try {
			PersistenceRoot root = serializer.read(PersistenceRoot.class, result);
			return root.getLibraries();
		} catch (IOException e){
			throw e;
		} catch (Exception e) {
			log.error("スニペットライブラリの読み込みに失敗",e);
		}
		return null;
	}
	
	

}
