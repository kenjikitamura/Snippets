package jp.rainbowdevil.snippets.ui.windows;

import jp.rainbowdevil.snippets.model.ISnippet;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;

/**
 * スニペットをドラッグ＆ドロップしてグループ間を移動する用のDragSourceListener
 * 
 * 現在は仮実装として、TextTransferを使用し、スニペットのHashcodeをドラッグ＆ドロップすることで移動する。
 * 正しくはスニペット用のTransferを作成する必要がある。
 * 
 * @author kitamura
 *
 */
public class SnippetDragSourceListener implements DragSourceListener {
	
	protected static org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(SnippetDragSourceListener.class);
	
	private TableViewer tableViewer;
	
	public SnippetDragSourceListener(TableViewer tableViewer){
		this.tableViewer = tableViewer;
	}

	@Override
	public void dragFinished(DragSourceEvent event) {
		log.debug("dragFinish "+event.data);
	}

	@Override
	public void dragSetData(DragSourceEvent event) {
		IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
		ISnippet snippet = (ISnippet)selection.getFirstElement();
		event.data = String.valueOf(snippet.hashCode());
	}

	@Override
	public void dragStart(DragSourceEvent event) {
		log.debug("dragStart "+event.data);
	}

}
