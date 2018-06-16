package org.keclipse.rcp.view.table;

import java.util.HashMap;
import java.util.Map;

import org.keclipse.utils.IKubeEclipseConstants;

import io.kubernetes.client.models.V1PersistentVolumeClaim;


public class KubePersistentVolumeClaims extends AbstractTableView {


	@Override
	public Map<Integer, String> getProperties(Object element) {
		Map<Integer,String> mmap = new HashMap<Integer,String>();
		V1PersistentVolumeClaim pod = (V1PersistentVolumeClaim) element;
		mmap.put(0, pod.getMetadata().getName());
		mmap.put(1, pod.getSpec().getVolumeName());
		mmap.put(2, String.join(", ",pod.getSpec().getAccessModes()));
		mmap.put(3, pod.getStatus().getCapacity().toString());
		return mmap;
	}
	
	@Override
	public String[] getTitles() {
		String[] titles = { "Name", "VolumeName","AccesMode","Capacity"};
		return titles;
	}
	
	@Override
	public String[] getWideTitles() {
		String[] wide = { "Name","VolumeName","Capacity" };
		return wide;
	}
	
	@Override
	public String getObjectName() {
	// TODO Auto-generated method stub
		return IKubeEclipseConstants.IKubeObjects.PERSISTENTVOLUMECLAIM;
	}
	
}
