package org.keclipse.k8.access;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.keclipse.k8.threads.PodExecutionCommand;
import org.keclipse.utils.IKubeEclipseConstants;

import com.google.common.io.ByteStreams;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.Exec;
import io.kubernetes.client.PodLogs;
import io.kubernetes.client.PortForward;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1ConfigMap;
import io.kubernetes.client.models.V1ConfigMapList;
import io.kubernetes.client.models.V1Endpoints;
import io.kubernetes.client.models.V1EndpointsList;
import io.kubernetes.client.models.V1Node;
import io.kubernetes.client.models.V1NodeList;
import io.kubernetes.client.models.V1PersistentVolume;
import io.kubernetes.client.models.V1PersistentVolumeClaim;
import io.kubernetes.client.models.V1PersistentVolumeClaimList;
import io.kubernetes.client.models.V1PersistentVolumeList;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodList;
import io.kubernetes.client.models.V1Service;
import io.kubernetes.client.models.V1ServiceList;
import io.kubernetes.client.util.Config;

public class KubeAccess {

	/**
	 * File Name
	 */
	private static String _kubeConfigFileName = null;
	/**
	 * Api Client for initialization
	 */
	private ApiClient _kubeApiClient = null;
	/**
	 * Created once access it set
	 */
	private CoreV1Api _api;
	/** List of Pods **/
	private List<String> _podnames = new ArrayList<String>();

	public List<String> getPodNames() {
		return _podnames;
	}


	/**
	 * Get the Exec associated with this ApiClient
	 * 
	 * @return
	 */
	public Exec getExec() {
		return new Exec(_kubeApiClient);
	}

	public PortForward getPortForward() {
		return new PortForward(_kubeApiClient);
	}

	/**
	 * Create KubeAccess from the externally passed kubeconfig file
	 * 
	 * @param filename
	 *            : Kubeconfig file
	 */
	public KubeAccess(String filename) {
		this.setKubeConfig(filename);
		for (V1Pod vi : this.getPodList())
			_podnames.add(vi.getMetadata().getName());
	}

	/**
	 * Check if the pod
	 * 
	 * @param podname
	 * @return
	 */
	public boolean hasPod(String podname) {
		return _podnames.contains(podname);
	}

	/**
	 * Set the KubeConfig file.
	 * 
	 * @param filename
	 */
	public void setKubeConfig(String filename) {
		try {
			_kubeApiClient = Config.fromConfig(filename);
			// Increasing the TimeOut
			_kubeApiClient.getHttpClient().setReadTimeout(60, TimeUnit.SECONDS);
			Configuration.setDefaultApiClient(_kubeApiClient);
			_api = new CoreV1Api();
			_kubeConfigFileName = filename;
		} catch (IOException e) {
			e.printStackTrace();
			_kubeApiClient = null;
		}
	}

	public String getKubeConfigFile() {
		return _kubeConfigFileName;
	}

	public CoreV1Api getAPI() {
		return _api;
	}

	public ApiClient getApiClient() {
		return _kubeApiClient;
	}

	/**
	 * Default KubeAccess for internal testing.
	 */
	@SuppressWarnings("unused")
	private KubeAccess() {
		this.setKubeConfig(null);
	}

	/**
	 * Get all the pods
	 * 
	 * @return
	 */
	public List<V1Pod> getPodList() {
		try {
			V1PodList list = _api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null);
			return list.getItems();
		} catch (Exception ex) {
			ex.printStackTrace();
			return Collections.emptyList();
		}
	}

	/**
	 * Get all the Services
	 * 
	 * @return
	 */
	public List<V1Service> getServiceList() {
		try {
			V1ServiceList list = _api.listServiceForAllNamespaces(null, null, null, null, null, null, null, null, null);
			return list.getItems();
		} catch (Exception ex) {
			ex.printStackTrace();
			return Collections.emptyList();
		}
	}

	/**
	 * Get all the ConfigMaps
	 * 
	 * @return
	 */
	public List<V1ConfigMap> getConfigMapList() {
		try {
			V1ConfigMapList list = _api.listConfigMapForAllNamespaces(null, null, null, null, null, null, null, null,
					null);
			return list.getItems();
		} catch (Exception ex) {
			ex.printStackTrace();
			return Collections.emptyList();
		}
	}

	/**
	 * Get all the ConfigMaps
	 * 
	 * @return
	 */
	public List<V1Endpoints> getEndpointList() {
		try {
			V1EndpointsList list = _api.listEndpointsForAllNamespaces(null, null, null, null, null, null, null, null,
					null);
			return list.getItems();
		} catch (Exception ex) {
			ex.printStackTrace();
			return Collections.emptyList();
		}
	}

	public List<V1PersistentVolume> getPersistentVolumeList() {
		try {
			V1PersistentVolumeList list = _api.listPersistentVolume(null, null, null, null, null, null, null, null,
					null);
			return list.getItems();
		} catch (Exception ex) {
			ex.printStackTrace();
			return Collections.emptyList();
		}
	}

	public List<V1PersistentVolumeClaim> getPersistentVolumeClaimList() {
		try {
			V1PersistentVolumeClaimList list = _api.listPersistentVolumeClaimForAllNamespaces(null, null, null, null,
					null, null, null, null, null);
			return list.getItems();
		} catch (Exception ex) {
			ex.printStackTrace();
			return Collections.emptyList();
		}
	}

	/**
	 * Get all the Nodes
	 * 
	 * @return
	 */
	public List<V1Node> getNodeList() {
		try {
			V1NodeList list = _api.listNode(null, null, null, null, null, null, null, null, null);
			return list.getItems();
		} catch (Exception ex) {
			ex.printStackTrace();
			return Collections.emptyList();
		}
	}

	public V1Pod getPodByName(String name) {
		return getPodList().stream().filter(x -> name.equals(x.getMetadata().getName())).findAny().orElse(null);
	}

	/**
	 * Dump Logs for the POD
	 * 
	 * @param pod
	 */
	public void dumpLogs(V1Pod pod) {
		PodLogs logs = new PodLogs(this.getApiClient());
		InputStream is;
		System.out.println("*********");
		try {
			is = logs.streamNamespacedPodLog(pod);
			ByteStreams.copy(is, System.out);
		} catch (ApiException | IOException e) {
			e.printStackTrace();
		}
		System.out.println("*********");
	}

	/**
	 * Get the Logs associated with a particular pod
	 * 
	 * @param pod
	 * @param apiClient
	 * @return StringBuffer for the logs.
	 */
	public static String getLogs(V1Pod pod, ApiClient apiClient) {
		PodLogs logs = new PodLogs(apiClient);
		String finalString = null;
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			ByteStreams.copy(logs.streamNamespacedPodLog(pod), stream);
			finalString = new String(stream.toByteArray());
		} catch (ApiException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return finalString;
	}

	/**
	 * Get the process associated with the pods and the commands you are sending
	 * 
	 * @param vipod
	 * @param cmds
	 * @return
	 */
	public Process getProcess(V1Pod vipod, String... cmds) throws Exception {

		String podName = vipod.getMetadata().getName();
		String namespace = vipod.getMetadata().getNamespace();
		List<String> commands = Arrays.asList(cmds);
		Exec exec = new Exec();
		boolean tty = System.console() != null;
		return (exec.exec(namespace, podName,
				commands.isEmpty() ? new String[] { "sh" } : commands.toArray(new String[commands.size()]), true, tty));

	}

	/**
	 * Execute a command on the pod
	 * 
	 * @param vipod
	 *            : The pod on which the command needs to be run
	 * @param cmds
	 *            : List of commands that need to be run on the pod
	 */
	public String execOnPod(V1Pod vipod, String... cmds) {
		try {
			Process proc = this.getProcess(vipod, cmds);
			PodExecutionCommand pexec = new PodExecutionCommand(proc);
			return pexec.getResult();
		} catch (Exception ex) {
			ex.printStackTrace();
			return IKubeEclipseConstants.EMPTYSTRING;
		}
	}

	/**
	 * Execute the kubernetes command from the command line
	 * 
	 * @param executable
	 *            : Executable path to kubectl
	 * @param arguments
	 *            : arguments to be passed in
	 * @return String
	 */
	public String execute(String executable, String arguments) {
		try {
			String cmd = executable + " --kubeconfig=" + this.getKubeConfigFile() + arguments;
			String s = null;
			StringBuffer retString = new StringBuffer();
			try {
				Process p = Runtime.getRuntime().exec(cmd);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
				while ((s = stdInput.readLine()) != null) {
					retString.append(s).append("\n");
				}
				return retString.toString();
			} catch (IOException e) {
				System.out.println("exception happened - here's what I know: ");
				e.printStackTrace();
				return IKubeEclipseConstants.EMPTYSTRING;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return IKubeEclipseConstants.EMPTYSTRING;
		}
	}

}
