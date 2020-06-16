package iut.group42b.boardgames.util;

import java.time.Duration;

public class Chronometer extends Thread {

	/* Variables */
	private final ChronometerListener listener;
	private long start;
	private boolean running;

	/**
	 * Constructor Chronometer.
	 *
	 * @param listener
	 */
	public Chronometer(ChronometerListener listener) {
		this.listener = listener;
	}

	@Override
	public void run() {
		this.reset();

		while (this.running) {
			this.listener.onChronometerSecond(this.toString());

			try {
				Thread.sleep(1000);
			} catch (InterruptedException exception) {
			}
		}
	}

	/**
	 * Reset the timer at now.
	 */
	public void reset() {
		this.start = System.currentTimeMillis();
	}

	/**
	 * Get the Time.
	 *
	 * @return long.
	 */
	public long getTime() {
		return System.currentTimeMillis() - this.start;
	}

	/**
	 * Set running state.
	 *
	 * @param running boolean.
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public String toString() {
		Duration duration = Duration.ofMillis(this.getTime());

		return String.format("%s:%02d:%02d", duration.toHours(), duration.toMinutes() % 60, (duration.toMillis() / 1000) % 60 % 60);
	}

	public interface ChronometerListener {

		void onChronometerSecond(String text);

	}
}