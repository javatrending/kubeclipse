package org.keclipse.k8.threads;

/**
 * 
 * @author vibhu.pratap
 *
 */
public class PodExecutionCommand {
	private Process _process;
	private String _result;

	public PodExecutionCommand(Process proc) {
		_process = proc;
		this.getOutput();
	}

	/**
	 * Get the output string after executing the command
	 * 
	 * @return
	 */
	private void getOutput() {
		try {
			PodExecutionInputStreamThread in = new PodExecutionInputStreamThread(_process);
			PodExecutionOutputStreamThread out = new PodExecutionOutputStreamThread(_process);
			in.start();
			out.start();
			_process.waitFor();
			// wait for any last output; no need to wait for input thread
			out.join();
			_process.destroy();
			_result = out.getResults();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Get the result
	 * 
	 * @return
	 */
	public String getResult() {
		return _result;
	}
}
