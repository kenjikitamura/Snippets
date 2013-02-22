package jp.rainbowdevil.snippets.ui.windows;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import jp.rainbowdevil.snippets.SnippetManager;
import jp.rainbowdevil.snippets.search.ISearchResultListener;
import jp.rainbowdevil.snippets.search.SearchManager;
import jp.rainbowdevil.snippets.search.SearchResultItem;
import jp.rainbowdevil.snippets.ui.ISearchDialog;

public class SnippetSearchWindow extends ApplicationWindow implements ISearchDialog{
	
	protected static org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(SnippetSearchWindow.class);
	
	private Text searchText;
	
	private TableViewer resultTableViewer;
	
	private SearchManager searchManager;
	//private SnippetManager snippetManager;
	private WindowsSnippetWindow mainWindow;
	
	private List<SearchResultItem> currentSearchResultItemList;

	public SnippetSearchWindow(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	protected Control createContents(Composite parent) {
		
		// ウインドウ全体のComposite 
		Composite topComposite = new Composite(parent, SWT.BORDER);
		GridLayout topGridLayout = new GridLayout();
		topGridLayout.numColumns = 1;
		topComposite.setLayout(topGridLayout);
		
		// 検索キーワード入力部分
		searchText = new Text(topComposite, SWT.BORDER);
		searchText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		searchText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent event) {
				searchStart(searchText.getText());
			}
		});
		
		// 検索結果表示TableViewer
		resultTableViewer = new TableViewer(topComposite);
		resultTableViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
		resultTableViewer.setContentProvider(new ArrayContentProvider());
		resultTableViewer.setLabelProvider(new ITableLabelProvider() {
			@Override
			public void removeListener(ILabelProviderListener arg0) {

			}
			
			@Override
			public boolean isLabelProperty(Object arg0, String arg1) {
				return false;
			}
			
			@Override
			public void dispose() {
		
			}
			
			@Override
			public void addListener(ILabelProviderListener arg0) {
			}
			
			@Override
			public String getColumnText(Object object, int arg1) {
				SearchResultItem searchResultItem = (SearchResultItem)object;
				
				return searchResultItem.getTitle();
			}
			
			@Override
			public Image getColumnImage(Object arg0, int arg1) {
				return null;
			}
		});
		resultTableViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				StructuredSelection selection = (StructuredSelection) event.getSelection();
				SearchResultItem item = (SearchResultItem) selection.getFirstElement();
				item.openSearchResultItem();
				close();
			}
		});
		
		return parent;
	}
	
	private void searchStart(String keyword){
		searchManager = new SearchManager(mainWindow);
		currentSearchResultItemList = new ArrayList<SearchResultItem>();
		resultTableViewer.setInput(currentSearchResultItemList);
		
		searchManager.search(searchText.getText(), new ISearchResultListener() {
			@Override
			public void notifyResult(SearchResultItem item) {
				log.debug("検索結果 "+item.getTitle());
				currentSearchResultItemList.add(item);
				resultTableViewer.refresh();
			}
			
			@Override
			public void finished() {
				log.debug("検索終了");
			}
		});
	}
	
	@Override
	public void create() {
		setShellStyle(SWT.RESIZE);
		super.create();
	}
	
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.addShellListener(new ShellListener() {
			
			@Override
			public void shellIconified(ShellEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void shellDeiconified(ShellEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void shellDeactivated(ShellEvent arg0) {
				log.debug("deactivated");
				close();
			}
			
			@Override
			public void shellClosed(ShellEvent arg0) {
			}
			
			@Override
			public void shellActivated(ShellEvent arg0) {
				log.debug("activeated");
			}
		});
		
		int width = 600;
		int height = 200;
		//shell.setSize(width, height);
		Rectangle rect = shell.getDisplay().getBounds();
		shell.setBounds(rect.width / 2 - (width / 2), rect.height / 2 - (height / 2), width, height);
	}

	public WindowsSnippetWindow getMainWindow() {
		return mainWindow;
	}

	public void setMainWindow(WindowsSnippetWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

}
