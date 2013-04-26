package jp.rainbowdevil.snippets.ui.windows;

import java.text.SimpleDateFormat;

import jp.rainbowdevil.snippets.model.ISnippet;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

public class SnippetsTableLabelProvider implements ITableLabelProvider {
	
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	
	@Override
	public void removeListener(ILabelProviderListener arg0) {
						
	}
	
	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getColumnText(Object object, int column) {
		ISnippet snippet = (ISnippet)object;
		switch(column){
		case 0:
			return snippet.getTitle();
		case 1:
			if (snippet.getUpdateDate() != null){
				return simpleDateFormat.format(snippet.getUpdateDate());
			}else{
				return "";
			}
		default:
			return snippet.getTitle();
		}

	}
	
	@Override
	public Image getColumnImage(Object arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
