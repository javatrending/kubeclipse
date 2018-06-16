package org.keclipse.rcp.view.table;

import java.util.HashMap;
import java.util.Map;

import org.keclipse.utils.IKubeEclipseConstants;

import io.kubernetes.client.models.V1Service;

/**
 * Tableview for KubeServices
 * @author vibhu.pratap
 *
 */
public class KubeServices extends AbstractTableView {

	public static final String ID = KubeServices.class.getName();
	
	@Override
	public Map<Integer, String> getProperties(Object element) {
		Map<Integer, String> mmap = new HashMap<Integer, String>();
		V1Service pod = (V1Service) element;
		mmap.put(0, pod.getMetadata().getName());
		mmap.put(1, pod.getMetadata().getNamespace());
		mmap.put(2, pod.getSpec().getClusterIP());
		mmap.put(3, pod.getSpec().getType());
		return mmap;
	}

	@Override
	public String[] getTitles() {
		String[] titles = { "Name", "NameSpace", "ClusterIP", "Type" };
		return titles;
	}

	@Override
	public String[] getWideTitles() {
		String[] wide = { "Name" };
		return wide;
	}

	@Override
	public int getWideColumnLength() {
		return 250;
	}

	@Override
	public String getObjectName() {
		return IKubeEclipseConstants.IKubeObjects.SERVICE;
	}

}
