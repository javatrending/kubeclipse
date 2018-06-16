package org.keclipse.rcp.view.table;

import java.util.HashMap;
import java.util.Map;

import org.keclipse.utils.IKubeEclipseConstants;

import io.kubernetes.client.models.V1Pod;

/**
 * View for Kubernetes Pods
 * 
 * @author vibhu.pratap
 *
 */
public class KubePods extends AbstractTableView {

	public static final String ID = KubePods.class.getName();

	@Override
	public Map<Integer, String> getProperties(Object element) {
		Map<Integer, String> mmap = new HashMap<Integer, String>();
		V1Pod pod = (V1Pod) element;
		mmap.put(0, pod.getMetadata().getName());
		mmap.put(1, pod.getStatus().getPodIP());
		mmap.put(2, pod.getStatus().getPhase());
		mmap.put(3, pod.getMetadata().getNamespace());
		mmap.put(4, pod.getSpec().getNodeName());
		return mmap;
	}

	@Override
	public String[] getTitles() {
		String[] titles = { "Name", "IP", "Phase", "Namespace", "NodeName" };
		return titles;
	}

	@Override
	public String[] getWideTitles() {
		String[] wide = { "Name", "NodeName" };
		return wide;
	}

	@Override
	public String getObjectName() {
		return IKubeEclipseConstants.IKubeObjects.POD;
	}

}
