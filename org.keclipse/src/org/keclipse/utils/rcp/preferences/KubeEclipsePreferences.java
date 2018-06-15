package org.keclipse.utils.rcp.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.keclipse.Activator;
import org.keclipse.utils.IKubeEclipseConstants;

/**
 * Add Preferences for Kubernetes Eclipse
 * @author vibhu.pratap
 *
 */
public class KubeEclipsePreferences extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	/**
	 * Constructor
	 */
	public KubeEclipsePreferences() {
		super(GRID);

	}

	/**
	 * Create a Field editors associated with KuberEclipse preferences
	 */
	public void createFieldEditors() {
		addField(new FileFieldEditor(IKubeEclipseConstants.KUBECONFIG, "&Kube Config :", getFieldEditorParent()));

	}

	/**
	 * Get the preference store
	 */
	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}
}