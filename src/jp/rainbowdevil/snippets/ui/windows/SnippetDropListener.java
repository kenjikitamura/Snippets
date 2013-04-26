package jp.rainbowdevil.snippets.ui.windows;

import jp.rainbowdevil.snippets.model.IGroupItem;
import jp.rainbowdevil.snippets.model.ISnippet;
import jp.rainbowdevil.snippets.ui.ISnippetWindow;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;

/**
 * スニペットドラッグ＆ドロップ用DropListener
 * 
 * @author kitamura
 *
 */
public class SnippetDropListener extends ViewerDropAdapter{
	protected static org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(SnippetDropListener.class);
	private TreeViewer treeViewer;
	private ISnippetWindow iSnippetWindow;
	
	public SnippetDropListener(ISnippetWindow iSnippetWindow, TreeViewer treeViewer){
		super(treeViewer);
		this.treeViewer = treeViewer;
		this.iSnippetWindow = iSnippetWindow;
	}
	
	@Override
	public void drop(DropTargetEvent event) {
		int location = this.determineLocation(event);
	    //String target = (String) determineTarget(event);
	    IGroupItem groupItem = (IGroupItem)determineTarget(event);
	    String translatedLocation ="";
	    switch (location){
	    case 1 :
	      translatedLocation = "Dropped before the target ";
	      break;
	    case 2 :
	      translatedLocation = "Dropped after the target ";
	      break;
	    case 3 :
	      translatedLocation = "Dropped on the target ";
	      break;
	    case 4 :
	      translatedLocation = "Dropped into nothing ";
	      break;
	    }
	    
	    String hashcode = (String)event.data;
	    ISnippet snippet = iSnippetWindow.getSnippetManager().getSnippetByHashcode(Integer.parseInt(hashcode));
	    //ISnippet snippet = (ISnippet)event.data;
	    IGroupItem groupItem2 = iSnippetWindow.getSnippetManager().getGroupItem(snippet);
	    if (groupItem2 != null){
	    	groupItem2.getSnippets().remove(snippet);
	    }
	    groupItem.addSnippet(snippet);
	    iSnippetWindow.refresh();
	    log.debug("Drop実行 target="+groupItem+" translatedLocation="+translatedLocation+" data="+event.data+" グループ("+groupItem2+")からグループ("+groupItem+")へ移動");
	    
		super.drop(event);
	}

	@Override
	public boolean performDrop(Object object) {
		
		return false;
	}

	@Override
	public boolean validateDrop(Object target, int operation, TransferData transferType) {
		return true;
	}

}
