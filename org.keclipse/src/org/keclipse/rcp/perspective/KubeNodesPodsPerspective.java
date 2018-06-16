package org.keclipse.rcp.perspective;



import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.keclipse.rcp.view.table.KubeNodes;
import org.keclipse.rcp.view.table.KubePods;



/**
 * Create a Kubernetes Nodes & Pods Perspective
 * @author vibhu.pratap
 *
 */
public class KubeNodesPodsPerspective extends KubeEclipseBasePerspective {

	public static final String ID = KubeNodesPodsPerspective.class.getName();
	
	/**
	 * List of views that will be part of that perspective
	 */
	public List<String> getViews() {
		return Stream.of(KubeNodes.ID, KubePods.ID).collect(Collectors.toList());
	}
	
}