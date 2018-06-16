package org.keclipse.rcp;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * A class encapsulating the Kubernetes scrollable dialog
 * @author vibhu.pratap
 *
 */
public class KubeScrollableDialog extends TitleAreaDialog {

	private String title;
	private String text;
	private String scrollableText;
	Text scrollable;

	/**
	 * 
	 * @param event
	 * @param title
	 * @param text
	 * @param scrollableText
	 */
	public KubeScrollableDialog(ExecutionEvent event, String title, String text, String scrollableText) {
		super(HandlerUtil.getActiveShell(event));
		this.title = title;
		this.text = text;
		this.scrollableText = scrollableText;
	}
	/**
	 * 
	 * @param parentShell
	 * @param title
	 * @param text
	 * @param scrollableText
	 */
	public KubeScrollableDialog(Shell parentShell, String title, String text, String scrollableText) {
		super(parentShell);
		this.title = title;
		this.text = text;
		this.scrollableText = scrollableText;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent); // Let the dialog create the parent composite

		GridData gridData = new GridData();
		// gridData.grabExcessHorizontalSpace = false;
		// gridData.horizontalAlignment = GridData.FILL;
		// gridData.grabExcessVerticalSpace = false; // Layout vertically, too!
		// gridData.verticalAlignment = GridData.FILL;

		scrollable = new Text(composite, SWT.BORDER | SWT.V_SCROLL);
		scrollable.setLayoutData(gridData);
		scrollable.setText(scrollableText);

		return composite;
	}

	@Override
	public void create() {
		super.create();

		// This is not necessary; the dialog will become bigger as the text grows but at
		// the same time, the user will be able to see all (or at least more) of the
		// error message at once getShell ().setSize (300, 300);
		setTitle(title);
		setMessage(text, IMessageProvider.INFORMATION);
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button okButton = createButton(parent, OK, "OK", true);
		okButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				close();
			}
		});
	}

	@Override
	protected boolean isResizable() {
		return true; // Allow the user to change the dialog size!
	}

	@Override
	protected void okPressed() {
		String mystr = scrollable.getText().toString();
		if (!mystr.equals(scrollableText)) {
			System.out.println(mystr);
		}
		super.okPressed();
	}
}
