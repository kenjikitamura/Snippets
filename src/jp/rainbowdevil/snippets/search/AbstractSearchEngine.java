package jp.rainbowdevil.snippets.search;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * �����G���W���̊��N���X
 * @author kkitamura
 *
 */
abstract public class AbstractSearchEngine implements ISearchEngine {
	
	protected ExecutorService executorService = Executors.newCachedThreadPool();

}
