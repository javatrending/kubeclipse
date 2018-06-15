package org.keclipse.utils.rcp;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.keclipse.Activator;
import org.keclipse.utils.IKubeEclipseConstants;

/**
 * Generic Utility class
 *
 * @author vibhu.pratap
 *
 */
public class RCPUtils {

	/**
	 * Get the Workbench window associated with the Workbench
	 * 
	 * @return The WorkBenchWindow
	 */
	public static IWorkbenchWindow getWorkbenchWindow() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	}

	/**
	 * Get the active page associated with the workbench window
	 * 
	 * @return The Workbench Page
	 */
	public static IWorkbenchPage getActivePage() {
		return getWorkbenchWindow().getActivePage();
	}

	/**
	 * Get the Shell associated with the UI
	 * 
	 * @return The Associated Shell
	 */
	public static Shell getShell() {
		return getWorkbenchWindow().getShell();
	}

	public static void openMessageBox(String message) {
		openMessageBox(null, message);
	}
	/**
	 * Open a Message Box with a header and a message
	 * @param header : Header for the Messagebox
	 * @param message : Message for the MessageBox
	 */
	public static void openMessageBox(String header, String message) {
		if (header == null) {
			MessageDialog.openInformation(getShell(), "Message", message);
		} else {
			MessageDialog.openInformation(getShell(), header, message);
		}
	}

	/*
	 * Generic function to get the data from the preference store given a key
	 */
	public static String getPreference(String key) {
		String val;
		try {
			val = Activator.getDefault().getPreferenceStore().getString(key);
		} catch (Exception ex) {
			val = null;
		}
		return val;
	}
	/**
	 * Get the preference for KubeConfig
	 * @return
	 */
	public static String getKubeConfig() {
		return getPreference(IKubeEclipseConstants.KUBECONFIG);
	}

	/**
	 * Get the Kubernetes scrollable dialog
	 * @param details
	 * @param shell
	 * @param heading
	 * @param subheading
	 * @return
	 */
	public static KubeScrollableDialog openScrollableDialog(String details, Shell shell, String heading,
			String subheading) {
		KubeScrollableDialog sd = new KubeScrollableDialog(shell, heading, subheading, details);
		sd.open();
		return sd;
	}
}
