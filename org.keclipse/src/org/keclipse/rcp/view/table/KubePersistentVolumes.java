package org.keclipse.rcp.view.table;

import java.util.HashMap;
import java.util.Map;

import org.keclipse.utils.IKubeEclipseConstants;

import io.kubernetes.client.models.V1PersistentVolume;

public class KubePersistentVolumes extends AbstractTableView {

	public static final String ID = KubePersistentVolumes.class.getName();
	@Override
	public Map<Integer, String> getProperties(Object element) {
		Map<Integer, String> mmap = new HashMap<Integer, String>();
		V1PersistentVolume pod = (V1PersistentVolume) element;
		mmap.put(0, pod.getMetadata().getName());
		mmap.put(1, pod.getSpec().getClaimRef().getName());
		mmap.put(2, pod.getSpec().getClaimRef().getNamespace());
		mmap.put(3, pod.getSpec().getCapacity().toString());
		return mmap;
	}

	@Override
	public String[] getTitles() {
		String[] titles = { "Name", "Claim", "Namespace", "Capacity" };
		return titles;
	}

	@Override
	public String[] getWideTitles() {
		String[] wide = { "Name", "Claim", "Capacity" };
		return wide;
	}

	@Override
	public String getObjectName() {
		return IKubeEclipseConstants.IKubeObjects.PERSISTENTVOLUME;
	}

}