package jp.rainbowdevil.snippets.ui.windows;

import jp.rainbowdevil.snippets.model.ISnippet;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;

/**
 * �X�j�y�b�g���h���b�O���h���b�v���ăO���[�v�Ԃ��ړ�����p��DragSourceListener
 * 
 * ���݂͉������Ƃ��āATextTransfer���g�p���A�X�j�y�b�g��Hashcode���h���b�O���h���b�v���邱�Ƃňړ�����B
 * �������̓X�j�y�b�g�p��Transfer���쐬����K�v������B
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
