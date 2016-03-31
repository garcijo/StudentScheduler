package view;

/*
 * Stopwatch class, somewhat accurate.
 * 
 * Contains the methods to start, stop, and restart the stopwatch.
 * 
 * Use getElapstedTime() to get the time.
 */

public class StopWatch {

	private long startTime;
	private long stopTime;
	private long pauseTime;

	private boolean isRunning = false;

	public StopWatch() {
		startTime = 0;
		stopTime = 0;
		pauseTime = 0;
		isRunning = false;
	}

	// Starts stopwatch.
	public void start() {
		this.startTime = System.currentTimeMillis();
		this.isRunning = true;
	}

	// Stops stopwatch.
	public void pause() {
		this.stopTime = System.currentTimeMillis();
		this.isRunning = false;
		pauseTime = getElapsedTime();
	}

	// stops stops stopwatch and resets value.
	public void reset() {
		pause();
		startTime = 0;
		stopTime = 0;
		pauseTime = 0;
	}

	// Elapsed Time.
	public long getElapsedTime() {
		long elapsed = pauseTime;

		if (isRunning) {
			elapsed += (System.currentTimeMillis() - startTime);
		} else {
			elapsed += (stopTime - startTime);
		}
		return elapsed;
	}

	public boolean getStatus() {
		return isRunning;
	}

	// Used for debugging. Time that is displayed is ~20ns off due pauseTime
	public String toString() {
		String t = "\n\tStartTime: " + startTime + "\n\tStopTime: " + stopTime
				+ "\n\tPauseTime: " + pauseTime + "\n";
		return t;
	}
}