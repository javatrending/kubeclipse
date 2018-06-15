package org.keclipse.rcp;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * List Label provider for the table viewer
 * 
 * @author vibhu.pratap
 *
 */
public class ListLabelProvider extends LabelProvider implements ITableLabelProvider {

	/**
	 * No Column Image by default
	 */
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	/**
	 * Will be overriden for non availble Column
	 */
	@Override
	public String getColumnText(Object element, int columnIndex) {
		return "Unsupported";
	}
}