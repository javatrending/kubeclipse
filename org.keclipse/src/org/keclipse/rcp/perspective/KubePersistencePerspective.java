package org.keclipse.rcp.perspective;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.keclipse.rcp.view.table.KubePersistentVolumeClaims;
import org.keclipse.rcp.view.table.KubePersistentVolumes;

/**
 * Create a Kubernetes PV & PVC Perspective
 * 
 * @author vibhu.pratap
 *
 */
public class KubePersistencePerspective extends KubeEclipseBasePerspective {

	public static final String ID = KubePersistencePerspective.class.getName();

	/**
	 * List of views that will be part of that perspective
	 */
	public List<String> getViews() {
		return Stream.of(KubePersistentVolumes.ID, KubePersistentVolumeClaims.ID).collect(Collectors.toList());
	}

}