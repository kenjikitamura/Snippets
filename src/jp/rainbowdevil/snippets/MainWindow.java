package jp.rainbowdevil.snippets;


import jp.rainbowdevil.snippets.model.GroupItem;
import jp.rainbowdevil.snippets.model.ISnippet;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class MainWindow extends ApplicationWindow{
	
	private SnippetContext snippetContext;
	
	private TreeViewer libraryTreeViewer;
	private TableViewer snippetsTableViewer;
	private StyledText styledText;
	private SashForm topSashForm;
	private SashForm rightSashForm;
	
	public MainWindow(Shell shell) {
		super(shell);
		snippetContext = new SnippetContext();
	}
	
	@Override
	protected MenuManager createMenuManager() {
		// メニューバーを作成する。
		MenuManager bar = super.createMenuManager();

		// ファイル(F)メニューを作成してメニューバーに追加する。
		MenuManager fileMenu = new MenuManager("ファイル(&F)");
		bar.add(fileMenu);
		return bar;
	}
	
	@Override
	protected Control createContents(Composite parent) {
		
		topSashForm = new SashForm(parent, SWT.HORIZONTAL);
		libraryTreeViewer = new TreeViewer(topSashForm);
		libraryTreeViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof GroupItem){
					GroupItem item = (GroupItem)element;
					return item.getTitle();
				}
				return "ERROR!";
			}
		});
		libraryTreeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				StructuredSelection selection = (StructuredSelection)event.getSelection();
				GroupItem groupItem = (GroupItem)selection.getFirstElement();
				snippetsTableViewer.setInput(groupItem.getSnippets());
				
			}
		});
		libraryTreeViewer.setContentProvider(new ITreeContentProvider() {
			
			@Override
			public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
			}
			
			@Override
			public void dispose() {
			}
			
			@Override
			public boolean hasChildren(Object object) {
				GroupItem item = (GroupItem)object;
				return item.hasChildren();
			}
			
			@Override
			public Object getParent(Object object) {
				GroupItem item = (GroupItem)object;
				return item.getParent();
			}
			
			@Override
			public Object[] getElements(Object object) {
				GroupItem item = (GroupItem)object;
				GroupItem[] array = item.getChildren().toArray(new GroupItem[item.getChildrenSize()]);
				return array;
			}
			
			@Override
			public Object[] getChildren(Object object) {
				return getElements(object);
			}
		});
		//Button leftButton = new Button(form, SWT.PUSH);
		//leftButton.setText("left");
		libraryTreeViewer.setInput(snippetContext.getRootItem());
		

		
		
		rightSashForm = new SashForm(topSashForm, SWT.VERTICAL);
		
		snippetsTableViewer = new TableViewer(rightSashForm);
		snippetsTableViewer.setLabelProvider(new ITableLabelProvider() {
			
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
			public String getColumnText(Object object, int arg1) {
				ISnippet snippt = (ISnippet)object;
				return snippt.getTitle();
			}
			
			@Override
			public Image getColumnImage(Object arg0, int arg1) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		
		snippetsTableViewer.setContentProvider(new ArrayContentProvider());
		snippetsTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				StructuredSelection selection = (StructuredSelection)event.getSelection();
				ISnippet snippet = (ISnippet)selection.getFirstElement();
				if (snippet != null){
					styledText.setText(snippet.getText());
				}
			}
		});
		
		styledText = new StyledText(rightSashForm, SWT.BORDER);
		//Button rightButton = new Button(rightSashForm, SWT.PUSH);
		//rightButton.setText("right");
		
		topSashForm.setWeights(new int[]{20, 80});
		
		topSashForm.setSize(1000,900);
		parent.pack();
		return parent;

	}
	
	/**
	 * SampleWindowのmainメソッド
	 * @param args 引数(ここでは無視する)
	 */
	public static void main(String[] args) {
		Shell shell = new Shell();
		MainWindow mainWindow = new MainWindow(shell);
		mainWindow.addMenuBar();
		mainWindow.addStatusLine();
		mainWindow.setBlockOnOpen(true);
		mainWindow.open();
		Display.getCurrent().dispose();
	}
}
