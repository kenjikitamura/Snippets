package jp.rainbowdevil.snippets.ui.windows;

import jp.rainbowdevil.snippets.model.IGroupItem;
import jp.rainbowdevil.snippets.model.RootGroupItem;
import jp.rainbowdevil.snippets.model.SnippetsLibrary;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class SnippetsTreeContentProvider implements ITreeContentProvider {
	@Override
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
	}
	
	@Override
	public void dispose() {
	}
	
	@Override
	public boolean hasChildren(Object object) {
		if (object instanceof IGroupItem){
			IGroupItem item = (IGroupItem)object;
			return item.hasChildren();	
		}
		if (object instanceof RootGroupItem){
			RootGroupItem item = (RootGroupItem)object;
			return !item.getSnippetsLibraries().isEmpty();	
		}
		return false;
	}
	
	@Override
	public Object getParent(Object object) {
		if (object instanceof IGroupItem){
			IGroupItem item = (IGroupItem)object;
			return item.getParent();
		}
		return null;
	}
	
	@Override
	public Object[] getElements(Object object) {
		if (object instanceof IGroupItem){
			IGroupItem item = (IGroupItem)object;
			IGroupItem[] array = item.getChildren().toArray(new IGroupItem[item.getChildrenSize()]);
			return array;
		}
		if (object instanceof RootGroupItem){
			RootGroupItem item = (RootGroupItem)object;
			SnippetsLibrary[] array = item.getSnippetsLibraries().toArray(new SnippetsLibrary[item.getSnippetsLibraries().size()]);
			return array;
		}
		return null;
	}
	
	@Override
	public Object[] getChildren(Object object) {
		return getElements(object);
	}
}
