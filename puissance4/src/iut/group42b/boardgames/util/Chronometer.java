package iut.group42b.boardgames.util;

import java.time.Duration;

public class Chronometer extends Thread {

	/* Variables */
	private final ChronometerListener listener;
	private long start;
	private boolean running;

	public Chronometer(ChronometerListener listener) {
		this.listener = listener;
	}

	@Override
	public void run() {
		reset();

		while (running) {
			listener.onChronometerSecond(toString());

			try {
				Thread.sleep(1000);
			} catch (InterruptedException exception) {
			}
		}
	}

	public void reset() {
		this.start = System.currentTimeMillis();
	}

	public long getTime() {
		return System.currentTimeMillis() - start;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public String toString() {
		Duration duration = Duration.ofMillis(getTime());

		return String.format("%s:%02d:%02d", duration.toHours(), duration.toMinutes() % 60, (duration.toMillis() / 1000) % 60 % 60);
	}

	public interface ChronometerListener {

		void onChronometerSecond(String text);

	}
}