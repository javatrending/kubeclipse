package org.keclipse.rcp.perspective;


import java.util.List;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * Base perspective of all the views here
 * @author vibhu.pratap
 *
 */
public abstract class KubeEclipseBasePerspective implements IPerspectiveFactory {

	
	/**
	 * Create the initial layout of the page here
	 */
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		layout.setFixed(true);
		
		List<String> views = this.getViews();
		if (views.size()  <= 0) {
			return;
		}
		float aspectratio = (float) (1.0/views.size());
		float init = aspectratio;
		for (int i= 0;i < views.size();i++) {
			layout.addView(views.get(i), IPageLayout.LEFT, init, editorArea);
			init = init + (float)aspectratio;
		}
	}

	
	/**
	 * Send the table views to be made as part of the perspective
	 * @return
	 */
	public abstract List<String> getViews();
}
