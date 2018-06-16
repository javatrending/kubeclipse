package org.keclipse.rcp.perspective;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.keclipse.rcp.view.table.KubeEndPoints;
import org.keclipse.rcp.view.table.KubeServices;



public class KubeServiceEndpointsPerspective extends KubeEclipseBasePerspective {

	public static final String ID = KubeServiceEndpointsPerspective.class.getName();

	/**
	 * List of views that will be part of that perspective
	 */
	public List<String> getViews() {
		return Stream.of(KubeServices.ID, KubeEndPoints.ID).collect(Collectors.toList());
	}

}