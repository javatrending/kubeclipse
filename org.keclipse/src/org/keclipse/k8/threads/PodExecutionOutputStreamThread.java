package org.keclipse.k8.threads;

/**
 * 
 * @author vibhu.pratap
 *
 */
public class PodExecutionOutputStreamThread extends PodExecutionThread {

	public PodExecutionOutputStreamThread(final Process proc) {
		super(proc, ThreadEnumeration.OUTPUTTHREAD);
	}

}