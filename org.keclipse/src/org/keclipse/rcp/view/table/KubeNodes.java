package org.keclipse.rcp.view.table;

import java.util.HashMap;
import java.util.Map;

import org.keclipse.utils.IKubeEclipseConstants;

import io.kubernetes.client.models.V1Node;

/**
 * Get the nodes associated with the KubeConfig
 * @author vibhu.pratap
 *
 */
public class KubeNodes extends AbstractTableView {

	public static final String ID = KubeNodes.class.getName();
	@Override
	public Map<Integer, String> getProperties(Object element) {
		Map<Integer,String> mmap = new HashMap<Integer,String>();
		V1Node pod = (V1Node) element;
		mmap.put(0, pod.getMetadata().getName());
		mmap.put(1, pod.getMetadata().getCreationTimestamp().toString());
		mmap.put(2, pod.getSpec().getPodCIDR());
		return mmap;
	}
	
	/**
	 * Only these column names needed
	 */
	@Override
	public String[] getTitles() {
		String[] titles = { "Name","CreationTime","PodCidr"};
		return titles;
	}
	
	@Override
	public String[] getWideTitles() {
		String[] wide = { "Name" ,"CreationTime"};
		return wide;
	}
	@Override
	public String getObjectName() {
		return IKubeEclipseConstants.IKubeObjects.NODE;
	}

}
