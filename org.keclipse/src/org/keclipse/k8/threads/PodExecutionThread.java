package org.keclipse.k8.threads;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.google.common.io.ByteStreams;

/**
 * PodExecution Thread
 * 
 * @author vibhu.pratap
 *
 */
abstract public class PodExecutionThread extends Thread {

	/**
	 * The Process whose input stream needs to be attached
	 */
	protected Process _process;
	/**
	 * Thread Enumeration
	 */
	private ThreadEnumeration _threadEnumeration;
	protected String _results = null;

	/**
	 * Make it protected so that no one from outside can call this
	 * 
	 * @param proc
	 */
	protected PodExecutionThread(final Process proc) {
		_process = proc;
		setThreadEnumeration(ThreadEnumeration.UNKNOWN);
	}

	/**
	 * Create an exectution thread given an enumeration
	 * 
	 * @param proc
	 *            : The process
	 * @param threadEnum
	 */
	public PodExecutionThread(final Process proc, ThreadEnumeration threadEnum) {
		_process = proc;
		setThreadEnumeration(threadEnum);
	}

	private void setThreadEnumeration(ThreadEnumeration threadEnum) {
		_threadEnumeration = threadEnum;
	}

	public Process getProcess() {
		return _process;
	}

	@Override
	public void run() {
		try {
			switch (_threadEnumeration) {
			case INPUTTHREAD:
				ByteStreams.copy(System.in, getOutputStream());
				break;
			case OUTPUTTHREAD:
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ByteStreams.copy(getInputStream(), baos);
				this._results = new String(baos.toByteArray());
				break;
			default:
				break;
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Get the input stream connected to the process
	 * 
	 * @return
	 */
	public InputStream getInputStream() {
		return _process.getInputStream();
	}

	/**
	 * Get the output stream connected to the process
	 * 
	 * @return
	 */
	public OutputStream getOutputStream() {
		return _process.getOutputStream();
	}

	/**
	 * Get thread enumeration for this
	 * 
	 * @return
	 */
	public ThreadEnumeration getThreadEnumeration() {
		return _threadEnumeration;
	}

	public String getResults() {
		return _results;
	}

}
