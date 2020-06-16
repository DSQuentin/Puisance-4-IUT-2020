package iut.group42b.boardgames.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CPU {

	private static final ProcessBuilder processBuilder = new ProcessBuilder();

	/**
	 * This function work only for windows PC.
	 * I do somes research and found @see <a href="https://mkyong.com/java/how-to-execute-shell-command-from-java/">this</a> tutorial.
	 *
	 * @return the pourcentage of CPU utilisation.
	 */
	public static int getLoadPercentageWindows() {
		System.out.println("CALLED");
		processBuilder.command("cmd.exe", "/c", "wmic cpu get loadpercentage"); // commant send via cmd
		int finalPoucentage = 0;
		try {

			Process process = processBuilder.start();

			StringBuilder output = new StringBuilder();
			// getting the result of the command
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line).append("\n");
			}

			int exitVal = process.waitFor();
			if (exitVal == 0) {

				// Removing unnecessary strings https://stackoverflow.com/questions/8694984/remove-part-of-string-in-java/31383680
				finalPoucentage = Integer.parseInt(output.toString().replaceAll("LoadPercentage", "").trim());
			} else {
				System.out.println("faild");

			}

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return -1;
		}

		return finalPoucentage;
	}
}
