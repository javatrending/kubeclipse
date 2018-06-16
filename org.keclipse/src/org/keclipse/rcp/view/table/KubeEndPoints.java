package org.keclipse.rcp.view.table;

import java.util.HashMap;
import java.util.Map;

import org.keclipse.utils.IKubeEclipseConstants;

import io.kubernetes.client.models.V1Endpoints;

/**
 * Table View for KubeEndpoints
 * @author vibhu.pratap
 *
 */
public class KubeEndPoints extends AbstractTableView {

	@Override
	public Map<Integer, String> getProperties(Object element) {
		Map<Integer, String> mmap = new HashMap<Integer, String>();
		V1Endpoints pod = (V1Endpoints) element;
		mmap.put(0, pod.getMetadata().getName());
		if (pod.getMetadata().getLabels() != null) {
			mmap.put(1, pod.getMetadata().getLabels().toString());
		} else {
			mmap.put(1, "--");
		}
		return mmap;
	}

	@Override
	public String[] getTitles() {
		String[] titles = { "Name", "Labels" };
		return titles;
	}

	@Override
	public String[] getWideTitles() {
		String[] wide = { "Name", "Labels" };
		return wide;
	}

	@Override
	public String getObjectName() {
		return IKubeEclipseConstants.IKubeObjects.ENDPOINT;
	}

}
