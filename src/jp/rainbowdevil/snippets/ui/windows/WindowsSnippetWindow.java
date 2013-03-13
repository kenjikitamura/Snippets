package jp.rainbowdevil.snippets.ui.windows;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jp.rainbowdevil.snippets.SnippetManager;
import jp.rainbowdevil.snippets.model.IGroupItem;
import jp.rainbowdevil.snippets.model.ISnippet;
import jp.rainbowdevil.snippets.model.SnippetsLibrary;
import jp.rainbowdevil.snippets.preferences.ISnippetPreference;
import jp.rainbowdevil.snippets.preferences.PreferencesBuilder;
import jp.rainbowdevil.snippets.sync.SynchronizeListener;
import jp.rainbowdevil.snippets.sync.SynchronizeManager;
import jp.rainbowdevil.snippets.ui.ISnippetWindow;
import jp.rainbowdevil.snippets.ui.windows.action.CreateSnippetAction;
import jp.rainbowdevil.snippets.ui.windows.syntax.PmpeLineStyleListener;
import jp.rainbowdevil.snippets.ui.windows.syntax.SyntaxData;
import jp.rainbowdevil.snippets.ui.windows.syntax.SyntaxManager;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.FocusCellOwnerDrawHighlighter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.TreeViewerEditor;
import org.eclipse.jface.viewers.TreeViewerFocusCellManager;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.markdown4j.Markdown4jProcessor;

/**
 * Snippets ���C���E�C���h�E
 * @author kitamura
 *
 */
public class WindowsSnippetWindow extends ApplicationWindow implements ISnippetWindow{
	
	protected static Logger log = Logger.getLogger( WindowsSnippetWindow.class ); 	
	
	/** �X�j�y�b�g�Ǘ��N���X */
	private SnippetManager snippetManager;
	
	/** �X�j�y�b�g���C�u������\������TreeViewer */
	private TreeViewer libraryTreeViewer;
	
	/** �X�j�y�b�g�ꗗ��\������TableViewer */
	private TableViewer snippetsTableViewer;
	
	/** �X�j�y�b�g��\������StypedText */
	private StyledText styledText;
	
	private Button editInspectorButton;
	private Button hideInspectorButton;
	
	/** ��ʂ��\������SashForm */
	private SashForm topSashForm;
	private SashForm rightSashForm;
	
	/** �X�j�y�b�g�^�C�g���ҏWText */
	private Text snippetTitleText;	
	
	/** �X�j�y�b�g�m�[�g�ҏWText */
	private Text snippetNoteText;
	
	/** �X�j�y�b�g���p�ҏWText */
	private Text snippetRelatedUrlText;
	
	/** �X�j�y�b�g��ҕҏWText */
	private Text snippetAuthorText;
	
	/** ���ݕҏW���̃X�j�y�b�g */
	private ISnippet currentSnippet;
	
	/** ���݊J���Ă���O���[�v */
	private IGroupItem currentGroupItem;
	
	/** �����Ǘ��N���X */
	private SynchronizeManager synchronizeManager;
	
	/** ���������pScheduledExecutorService */
	private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
	
	/** �Ō�ɕҏW���Ă��牽�b�Ŏ����������s���� */
	private long AUTO_SYNC_TIMER = 30 * 1000;
	
	/** �Ō�ɕҏW�����^�C���X�^���v */
	private long lastModifyTimestamp;
	
	/**
	 * �R���X�g���N�^
	 */
	public WindowsSnippetWindow() {
		super(null);
		snippetManager = new SnippetManager();
		snippetManager.setSnippetsWindow(this);
		synchronizeManager = new SynchronizeManager();
		try {
			log.debug("�N�����ɃX�j�y�b�g���C�u�����ǂݍ��݊J�n");
			snippetManager.loadSnippetLibraryFromLocalDatabase();
			log.debug("�N�����ɃX�j�y�b�g���C�u�����ǂݍ��݊���");
		} catch (IOException e) {
			log.error("�N�����̃X�j�y�b�g���C�u�����̓ǂݍ��݂Ɏ��s",e);
		}
	}
	
	@Override
	protected MenuManager createMenuManager() {
		return new SnippetsMemuBuilder().createMenuManager(this);
	}
	
	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);
		//toolBarManager.add(new ExitAction(this));
		toolBarManager.add(new CreateSnippetAction(this, "�V�K�쐬"));
		return toolBarManager;
	}
	
	@Override
	protected Control createContents(Composite parent) {
		topSashForm = new SashForm(parent, SWT.HORIZONTAL);
		libraryTreeViewer = new TreeViewer(topSashForm);
		libraryTreeViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof IGroupItem){
					IGroupItem item = (IGroupItem)element;
					return item.getTitle();
				}
				return "ERROR!";
			}
		});

		libraryTreeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				StructuredSelection selection = (StructuredSelection)event.getSelection();
				IGroupItem groupItem = (IGroupItem)selection.getFirstElement();
				if (groupItem == null){
					return ;
				}
				setWindowTitleMessage(groupItem.getTitle());
				snippetsTableViewer.setInput(groupItem.getSnippets());
				
				if (groupItem instanceof SnippetsLibrary){
					snippetManager.setCurrentSnippetsLibrary((SnippetsLibrary)groupItem);
				}
				
			}
		});
		libraryTreeViewer.setContentProvider(new SnippetsTreeContentProvider());
		libraryTreeViewer.setInput(snippetManager.getRootItem());
		libraryTreeViewer.getTree().setMenu( new TreeContextMenuBuilder(this).createContextMenu(libraryTreeViewer.getTree()));
		libraryTreeViewer.getTree().setHeaderVisible(true);
		TreeViewerFocusCellManager focusCellManager = new TreeViewerFocusCellManager(libraryTreeViewer,new FocusCellOwnerDrawHighlighter(libraryTreeViewer));
		ColumnViewerEditorActivationStrategy actSupport = new ColumnViewerEditorActivationStrategy(libraryTreeViewer) {
			protected boolean isEditorActivationEvent(
					ColumnViewerEditorActivationEvent event) {
				return event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL
						|| event.eventType == ColumnViewerEditorActivationEvent.MOUSE_DOUBLE_CLICK_SELECTION
						|| (event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED && event.keyCode == SWT.CR)
						|| event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC;
			}
		};
		
		TreeViewerEditor.create(libraryTreeViewer, focusCellManager, actSupport, ColumnViewerEditor.TABBING_HORIZONTAL
				| ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR
				| ColumnViewerEditor.TABBING_VERTICAL | ColumnViewerEditor.KEYBOARD_ACTIVATION);
		
		
		int dropOperations = DND.DROP_COPY | DND.DROP_MOVE;
	    Transfer[] dropTransferTypes = new Transfer[]{TextTransfer.getInstance()};
	    libraryTreeViewer.addDropSupport(dropOperations, dropTransferTypes, new SnippetDropListener(this, libraryTreeViewer));
	    
		final TextCellEditor textCellEditor = new TextCellEditor(libraryTreeViewer.getTree());
		TreeViewerColumn column = new TreeViewerColumn(libraryTreeViewer, SWT.NONE);
		column.getColumn().setWidth(130);
		column.getColumn().setMoveable(false);
		column.getColumn().setText("Name");
		column.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				IGroupItem item = (IGroupItem)element;
				return item.getTitle();
			}
		});
		column.setEditingSupport(new EditingSupport(libraryTreeViewer) {
			protected boolean canEdit(Object element) {
				return true;
			}
			protected CellEditor getCellEditor(Object element) {
				return textCellEditor;
			}
			protected Object getValue(Object element) {
				IGroupItem item = (IGroupItem)element;
				return item.getTitle();
			}
			protected void setValue(Object element, Object value) {
				IGroupItem item = (IGroupItem)element;
				item.setTitle(value.toString());
				libraryTreeViewer.update(element, null);
			}
		});
		
		TreeViewerColumn column2 = new TreeViewerColumn(libraryTreeViewer, SWT.NONE);
		column2.getColumn().setWidth(50);
		column2.getColumn().setMoveable(false);
		column2.getColumn().setText("Size");
		column2.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				IGroupItem item = (IGroupItem)element;
				return String.valueOf(item.getSnippetsSize());
			}
		});
		
		rightSashForm = new SashForm(topSashForm, SWT.VERTICAL);
		snippetsTableViewer = new TableViewer(rightSashForm);
		snippetsTableViewer.setLabelProvider(new SnippetsTableLabelProvider());
		TableColumn col1 = new TableColumn(snippetsTableViewer.getTable(), SWT.LEFT);
		col1.setText("�^�C�g��");
		col1.setWidth(400);
		
		TableColumn col2 = new TableColumn(snippetsTableViewer.getTable(), SWT.LEFT);
		col2.setText("�X�V��");
		col2.setWidth(100);
		
		snippetsTableViewer.getTable().setHeaderVisible(true);

		snippetsTableViewer.addFilter(new ViewerFilter() {					
			@Override
			public boolean select(Viewer viewer, Object parent, Object object) {
				ISnippet snippet = (ISnippet)object;				
				return !snippet.isDeleted();
			}
		});
		int operations = DND.DROP_COPY| DND.DROP_MOVE;
		Transfer[] transferTypes = new Transfer[]{TextTransfer.getInstance()};
		snippetsTableViewer.addDragSupport(operations, transferTypes, new SnippetDragSourceListener(snippetsTableViewer));
		snippetsTableViewer.setContentProvider(new ArrayContentProvider());
		snippetsTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				StructuredSelection selection = (StructuredSelection)event.getSelection();
				ISnippet snippet = (ISnippet)selection.getFirstElement();
				if (snippet != null){
					showSnippet(snippet);
					//selectCurrentSnippet(snippet);
				}
			}
		});
		
		Composite snippetEditorComposite = new Composite(rightSashForm, SWT.BORDER);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		snippetEditorComposite.setLayout(gridLayout);
		
		// �X�j�y�b�g�w�b�_�[
		Composite snippetHeaderComposite = new Composite(snippetEditorComposite, SWT.NULL);
		GridLayout snippetHeaderCompositeLayout = new GridLayout();
		snippetHeaderCompositeLayout.numColumns = 2;
		snippetHeaderCompositeLayout.marginHeight = 0;
		snippetHeaderCompositeLayout.marginLeft = 0;
		snippetHeaderCompositeLayout.marginRight = 0;
		snippetHeaderCompositeLayout.marginBottom = 0;
		snippetHeaderCompositeLayout.marginWidth = 0;
		snippetHeaderComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		snippetHeaderComposite.setLayout(snippetHeaderCompositeLayout);
		
		// �X�j�y�b�g�ύX���̍X�V���X�i
		ModifyListener snippetModifyListener = new ModifyListener() {			
			@Override
			public void modifyText(ModifyEvent arg0) {
				updateSnippet();
			}
		};
		
		// ���\���{�^��
		Composite inspectorComposite = new Composite(snippetHeaderComposite, SWT.NONE);
		GridData inspectorCompositeLayoutData = new GridData(GridData.FILL_HORIZONTAL);
		inspectorCompositeLayoutData.horizontalSpan = 2;
		inspectorComposite.setLayoutData(inspectorCompositeLayoutData);
		GridLayout inspectorCompositeLayout = new GridLayout();
		inspectorCompositeLayout.numColumns = 3;
		inspectorCompositeLayout.marginHeight = 0;
		inspectorCompositeLayout.marginLeft = 0;
		inspectorCompositeLayout.marginRight = 0;
		inspectorCompositeLayout.marginBottom = 0;
		inspectorCompositeLayout.marginWidth = 0;
		inspectorComposite.setLayout(inspectorCompositeLayout);
		
		// �X�j�y�b�g�^�C�g��
		snippetTitleText = new Text(inspectorComposite, SWT.BORDER);
		snippetTitleText.setText("");
		snippetTitleText.setEnabled(false);
		GridData snippetTitleTextLayoutData = new GridData(GridData.FILL_HORIZONTAL);
		snippetTitleTextLayoutData.horizontalSpan = 1;
		snippetTitleText.setLayoutData(snippetTitleTextLayoutData);
		snippetTitleText.addModifyListener(snippetModifyListener);
				
		
		
		
		
		
		
		editInspectorButton = new Button(inspectorComposite, SWT.NONE);
		editInspectorButton.setText("�ҏW");
		
		hideInspectorButton = new Button(inspectorComposite, SWT.NONE);
		hideInspectorButton.setText("��\��");
		
		
		styledText = new StyledText(snippetEditorComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		styledText.setEnabled(false);
		styledText.addModifyListener(snippetModifyListener);
		GridData styledTextLayoutData = new GridData(GridData.FILL_BOTH);
		styledText.setLayoutData(styledTextLayoutData);
		
		// �X�j�y�b�g�m�[�g
		Label snippetNoteLabel = new Label(snippetHeaderComposite, SWT.NULL);
		snippetNoteLabel.setText("Notes:");
		snippetNoteLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		snippetNoteText = new Text(snippetHeaderComposite, SWT.BORDER | SWT.MULTI|SWT.V_SCROLL);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 50;
		snippetNoteText.setLayoutData(gridData);
		snippetNoteText.addModifyListener(snippetModifyListener);
		snippetNoteText.setEnabled(false);
		
		// �X�j�y�b�g���pURL
		Label snippetRelatedUrlLabel = new Label(snippetHeaderComposite, SWT.NULL);
		snippetRelatedUrlLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		snippetRelatedUrlLabel.setText("Related URL:");
		snippetRelatedUrlText = new Text(snippetHeaderComposite, SWT.BORDER);
		snippetRelatedUrlText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		snippetRelatedUrlText.addModifyListener(snippetModifyListener);
		snippetRelatedUrlText.setEnabled(false);
		
		// �X�j�y�b�g��Җ�
		Label snippetAuthorLabel = new Label(snippetHeaderComposite, SWT.NULL);
		snippetAuthorLabel.setText("Author:");
		snippetAuthorLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		snippetAuthorText = new Text(snippetHeaderComposite, SWT.BORDER);
		snippetAuthorText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		snippetAuthorText.addModifyListener(snippetModifyListener);
		snippetAuthorText.setEnabled(false);
		
		rightSashForm.setWeights(new int[]{30,70});
		topSashForm.setWeights(new int[]{20, 80});
		
		updateSyntaxHighlight();
		
		Markdown4jProcessor markdown4jProcessor = new Markdown4jProcessor();
		try {
			String html = markdown4jProcessor.process("This is a **bold** text~~~~~~~\nif (a > 3) {\n  moveShip(5 * gravity, DOWN);\n}\n~~~~~~~");
			log.debug("html="+html);
		} catch (IOException e) {
			log.debug("�����Ɏ��s",e);
		}
		
		initAutoSyncTimer();

		return parent;
	}
	
	/**
	 * ���������^�C�}�[��������
	 * 1�����ƂɃ`�F�b�N����B
	 */
	private void initAutoSyncTimer(){
		executorService.scheduleWithFixedDelay(new Runnable() {			
			@Override
			public void run() {
				try{
					checkAutoSync();
				}catch(Exception e){
					log.error("�����������ɃG���[����",e);
				}
			}
		}, 1, 1, TimeUnit.MINUTES);
	}
	
	/**
	 * ���������`�F�b�N
	 */
	private void checkAutoSync(){
		long diff = System.currentTimeMillis() - lastModifyTimestamp;
		log.debug("���������`�F�b�N diff="+diff + " lastModifyTimestamp=" + lastModifyTimestamp);
		if (diff > AUTO_SYNC_TIMER && lastModifyTimestamp != 0){
			syncSnippets();
			lastModifyTimestamp = 0;
		}
	}
	
	/**
	 * �T�[�o�������s��
	 */
	private void syncSnippets(){
		try{
			ISnippetPreference preference = PreferencesBuilder.getSnippetPreference();
			String email = preference.getString(ISnippetPreference.ACCOUNT_EMAIL, null);
			String password = preference.getString(ISnippetPreference.ACCOUNT_PASSWORD, null);
			if (email != null){
				getSynchronizeManager().login(email, password);
			}else{
				setStatusAsync("�����������s : �A�J�E���g����o�^���Ă��������B");
				return;
			}
		}catch(IOException e){
			log.error("���O�C�����s",e);
			if (e.getMessage().contains("Server returned HTTP response code: 401")){
				setStatusAsync("�����������s : �F�؂Ɏ��s���܂����B"+e.getMessage());
			}else{
				setStatusAsync("�����������s : "+e.getClass().getName()+" : "+e.getMessage());
			}
			
			return;
		}
		getSynchronizeManager().synchronize(getSnippetManager(), new SynchronizeListener() {
			
			@Override
			public void updateProgress(int current, int max) {
				log.debug("������("+current+"/"+max+")");
			}
			
			@Override
			public void error(String message, Throwable e) {
				log.debug("�����G���[");
			}
				
			@Override
			public void complete() {
				log.debug("��������");
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						refresh();
					}
				});
			}
		});
	}
	
	private void setStatusAsync(final String text){
		Display.getDefault().asyncExec(new Runnable() {			
			@Override
			public void run() {
				setStatus(text);
			}
		});
	}
	
	
	@Override
	/**
	 * �O���[�v��I������B
	 */
	public void selectCurrentGroupItem(IGroupItem groupItem) {
		StructuredSelection selection = new StructuredSelection(groupItem);
		currentGroupItem = groupItem;
		libraryTreeViewer.setSelection(selection);
	}
	
	private void updateSnippet(){
		if (currentSnippet != null){
			currentSnippet.setTitle(snippetTitleText.getText());
			currentSnippet.setBody(styledText.getText());
			currentSnippet.setUpdateDate(new Date());
			currentSnippet.setAuthor(snippetAuthorText.getText());
			currentSnippet.setRelatedUrl(snippetRelatedUrlText.getText());
			currentSnippet.setNotes(snippetNoteText.getText());
			currentSnippet.setDirty(true);			
			snippetsTableViewer.refresh();
			
			// �Ō�ɍX�V�����������X�V
			lastModifyTimestamp = System.currentTimeMillis();
		}
	}
	
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Snippets");
		
		// ��ʈʒu�̕���
		ISnippetPreference snippetPreference = PreferencesBuilder.getSnippetPreference();
		int x = snippetPreference.getInt(ISnippetPreference.LAST_WINDOW_X, 0);
		int y = snippetPreference.getInt(ISnippetPreference.LAST_WINDOW_Y, 0);
		int width = snippetPreference.getInt(ISnippetPreference.LAST_WINDOW_WIDTH, 900);
		int height = snippetPreference.getInt(ISnippetPreference.LAST_WINDOW_HEIGHT, 600);
		shell.setBounds(x, y, width, height);
	}
	
	@Override
	protected void handleShellCloseEvent() {
		// �X�j�y�b�g���C�u�����̕ۑ�
		try {
			snippetManager.saveSnippetLibraryToLocalDatabase();
		} catch (IOException e) {
			log.error("�I�����̃X�j�y�b�g���C�u�����̕ۑ��Ɏ��s",e);
		}
		
		// ��ʈʒu�̕ۑ�
		ISnippetPreference snippetPreference = PreferencesBuilder.getSnippetPreference();
		snippetPreference.setValue(ISnippetPreference.LAST_WINDOW_WIDTH, getShell().getBounds().width);
		snippetPreference.setValue(ISnippetPreference.LAST_WINDOW_HEIGHT, getShell().getBounds().height);
		snippetPreference.setValue(ISnippetPreference.LAST_WINDOW_X, getShell().getBounds().x);
		snippetPreference.setValue(ISnippetPreference.LAST_WINDOW_Y, getShell().getBounds().y);
		snippetPreference.saveQuietly();
		
		// �����ۑ����~
		List<Runnable> list = executorService.shutdownNow();
		log.debug("ExecutorService shutdown. �c��Runnable="+list.size());
		
		super.handleShellCloseEvent();
	}
	/*
	@Override
	public void notifyDataChanged() {
		libraryTreeViewer.refresh();
		snippetsTableViewer.refresh();
	}
	*/
	@Override
	public void exit() {
		close();		
	}
	
	@Override
	public SnippetManager getSnippetManager() {	
		return snippetManager;
	}
	
	@Override
	public String getApplicationTitle() {
		return "Snippets";
	}
	
	@Override
	public void setWindowTitleMessage(String titleMessage) {
		getShell().setText(getApplicationTitle()+" - "+titleMessage);
	}
	
	private void showSnippet(ISnippet snippet){
		currentSnippet = snippet;
		
		if (snippet == null){
			setWindowTitleMessage("");
			styledText.setText("");
			styledText.setEnabled(false);
			snippetTitleText.setEnabled(false);
			snippetAuthorText.setText("");
			snippetAuthorText.setEnabled(false);
			snippetNoteText.setText("");
			snippetNoteText.setEnabled(false);
			snippetRelatedUrlText.setText("");
			snippetRelatedUrlText.setEnabled(false);
		}else{
			setWindowTitleMessage(snippet.getTitle());
			currentSnippet = null; // styledText��ύX�����currentSnippet�ɏ��������t���O�����̂ň�xnull��ݒ肷��B
			styledText.setEnabled(true);
			snippetTitleText.setEnabled(true);
			styledText.setText(snippet.getBody());
			snippetTitleText.setText(snippet.getTitle());
			snippetAuthorText.setText(snippet.getAuthor());
			snippetAuthorText.setEnabled(true);
			snippetNoteText.setText(snippet.getNotes());
			snippetNoteText.setEnabled(true);
			snippetRelatedUrlText.setText(snippet.getRelatedUrl());
			snippetRelatedUrlText.setEnabled(true);
			
			currentSnippet = snippet;
			
		}
	}
	
	@Override
	/**
	 * ���ݑI�𒆂̃X�j�y�b�g��ݒ肷��B
	 */
	public void selectCurrentSnippet(ISnippet snippet) {
		currentSnippet = snippet;
		IGroupItem item = snippetManager.getGroupItem(snippet);
		log.debug("�X�j�y�b�g��I����Ԃ� groupItem="+item);
		if (item != null){
			snippetsTableViewer.setInput(item.getSnippets());
			snippetsTableViewer.refresh();
		}
			
		// �X�j�y�b�g��I��
		ISelection selection = new StructuredSelection(snippet);
		snippetsTableViewer.setSelection(selection, true);
	}
	
	/**
	 * ���C�u�����̃O���[�v�c���[�Ō��ݑI�𒆂̃A�C�e�����擾����B
	 * @return
	 */
	public IGroupItem getCurrentSelectedTreeItem(){
		StructuredSelection selection = (StructuredSelection) libraryTreeViewer.getSelection();
		if (selection.isEmpty()){
			return null;
		}
		IGroupItem groupItem = (IGroupItem) selection.getFirstElement();
		return groupItem;
	}
	
	/**
	 * ���ݑI�𒆂̃X�j�y�b�g�ꗗ���擾����B
	 * �����I�����Ă��Ȃ���΁A�T�C�Y��0�̃��X�g��Ԃ��B
	 * @return
	 */
	public List<ISnippet> getCurrentSelectedSnipptes(){
		List<ISnippet> selectedList = new ArrayList<ISnippet>();
		StructuredSelection selection = (StructuredSelection)snippetsTableViewer.getSelection();
		
		if (!selection.isEmpty()){
			for(Object obj:selection.toList()){
				if (obj instanceof ISnippet){
					selectedList.add((ISnippet)obj);
				}
			}
		}
		return selectedList;
	}
	
	@Override
	/**
	 * �\�����X�V����
	 */
	public void refresh() {
		libraryTreeViewer.refresh();
		snippetsTableViewer.refresh();
		if (currentSnippet != null){
			showSnippet(currentSnippet);
		}
		//log.debug("refresh groupItem="+currentGroupItem+" snippet="+currentSnippet);
		//selectCurrentGroupItem(currentGroupItem);
		//selectCurrentSnippet(currentSnippet);
	}
	
	/**
	 * SampleWindow��main���\�b�h
	 * @param args ����(�����ł͖�������)
	 */
	public static void main(String[] args) {
		WindowsSnippetWindow mainWindow = new WindowsSnippetWindow();
		mainWindow.addMenuBar();
		mainWindow.addToolBar(SWT.FLAT);
		mainWindow.addStatusLine();
		mainWindow.setBlockOnOpen(true);
		mainWindow.open();
		Display.getCurrent().dispose();
	}
	
	
	private PmpeLineStyleListener lineStyleListener ;
	public void updateSyntaxHighlight(){
		SyntaxManager syntaxManager = new SyntaxManager();
		SyntaxData syntaxData = syntaxManager.getSyntaxData("java");

	    // Reset the line style listener
	    if (lineStyleListener != null) {
	      styledText.removeLineStyleListener(lineStyleListener);
	    }
	    lineStyleListener = new PmpeLineStyleListener(syntaxData);
	    styledText.addLineStyleListener(lineStyleListener);

	    // Redraw the contents to reflect the new syntax data
	    styledText.redraw();
	}
	
	public SynchronizeManager getSynchronizeManager(){
		return synchronizeManager;
	}
}
