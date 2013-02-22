package jp.rainbowdevil.snippets.ui.windows;

import jp.rainbowdevil.snippets.model.IGroupItem;

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
		IGroupItem item = (IGroupItem)object;
		return item.hasChildren();
	}
	
	@Override
	public Object getParent(Object object) {
		IGroupItem item = (IGroupItem)object;
		return item.getParent();
	}
	
	@Override
	public Object[] getElements(Object object) {
		IGroupItem item = (IGroupItem)object;
		IGroupItem[] array = item.getChildren().toArray(new IGroupItem[item.getChildrenSize()]);
		return array;
	}
	
	@Override
	public Object[] getChildren(Object object) {
		return getElements(object);
	}
}
