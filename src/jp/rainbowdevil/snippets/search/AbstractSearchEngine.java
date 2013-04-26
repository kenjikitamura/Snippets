package jp.rainbowdevil.snippets.search;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 検索エンジンの基底クラス
 * @author kkitamura
 *
 */
abstract public class AbstractSearchEngine implements ISearchEngine {
	
	protected ExecutorService executorService = Executors.newCachedThreadPool();

}
