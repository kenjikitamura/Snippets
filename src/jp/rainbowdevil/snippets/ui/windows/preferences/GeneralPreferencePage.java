package jp.rainbowdevil.snippets.ui.windows.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Text;

public class GeneralPreferencePage extends FieldEditorPreferencePage{

	public GeneralPreferencePage(){
		setTitle("ページ２");
        setMessage("ページ２のメッセージ");
	}
/*
	@Override
	protected Control createContents(Composite parent) {
		Composite c = new Composite(parent, SWT.NONE);
        c.setLayout(new GridLayout(2, true));
        new Label(c, SWT.NONE).setText("パラメータ１：");
        text = new Text(c, SWT.SINGLE | SWT.BORDER);
        return c;
	}
*/
	@Override
	protected void createFieldEditors() {
		//addField(new DirectoryFieldEditor("PATH", "&Directory preference:",
		//        getFieldEditorParent()));
		    addField(new BooleanFieldEditor("BOOLEAN_VALUE",
		        "&An example of a boolean preference", getFieldEditorParent()));

		    addField(new RadioGroupFieldEditor("CHOICE",
		        "An example of a multiple-choice preference", 1,
		        new String[][] { { "&Choice 1", "choice1" },
		            { "C&hoice 2", "choice2" } }, getFieldEditorParent()));
		    addField(new StringFieldEditor("MySTRING1", "A &text preference:",
		        getFieldEditorParent()));
		    addField(new StringFieldEditor("MySTRING2", "A &text preference:",
		        getFieldEditorParent()));
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		//super.propertyChange(event);
	}
}
