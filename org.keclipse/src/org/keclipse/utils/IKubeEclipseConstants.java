package org.keclipse.utils;

/**
 * Constants used throughout the system
 * 
 * @author vibhu.pratap
 *
 */
public interface IKubeEclipseConstants {

	/**
	 * Preference Constants
	 */

	public static final String EMPTYSTRING = "";
	public static final String KUBECONFIG = "KubeConfig";

	/**
	 * Kubernetes objects
	 * @author vibhu.pratap
	 *
	 */
	public interface IKubeObjects {
		public static final String CONFIGMAP = "ConfigMap";
		public static final String POD = "Pod";
		public static final String SYSTEMDEF = "SystemDef";
		public static final String SERVICE = "Service";
		public static final String ENDPOINT = "Endpoint";
		public static final String PERSISTENTVOLUME = "PersistentVolume";
		public static final String PERSISTENTVOLUMECLAIM = "PersistentVolumeClaim";
		public static final String NODE = "Node";
	}
}
