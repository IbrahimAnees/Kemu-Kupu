package application;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class creates various bash processes in order to use commands from
 * the command line
 */
public class BashProcess {

	/*
	The "sayMaoriWord" calls a bash process that used a text-to-speech in order
	to say the Maori word

	@params: a string of the Maori word to be said
	 */
	public static void sayMaoriWord(String maoriText) throws IOException, InterruptedException {
		//create a file to store the text to be spoken out
		File f = new File("./resources/tempFiles/.speech.scm");
		f.createNewFile();
		maoriText = maoriText.replace("-", " ");
		
		String textSpeed = Double.toString(SettingsController.getTextToSpeechSpeed());

		//write into the file
		PrintWriter writer = new PrintWriter("./resources/tempFiles/.speech.scm");
		writer.print("(voice_akl_mi_pk06_cg)");
		writer.append("\n" + "(Parameter.set 'Duration_Stretch " + textSpeed + ")");
		writer.append("\n" + "(SayText \"" + maoriText + "\")");
		writer.close();

		//call the bash command
		String command = "festival -b .speech.scm";
		ProcessBuilder process = new ProcessBuilder("bash", "-c", command);
		process = process.directory(new File("./resources/tempFiles/"));
		process.start();
	}

	/*
	The "speakEnglishWord" calls a bash process that used a text-to-speech in order
	to say the English word

	@params: a string of the English word to be said
	 */
	public static void speakEnglishWord(String englishText) throws IOException, InterruptedException {
		//calling the bash process
		String s = "echo \"" + englishText + "\" | festival --tts";
		ProcessBuilder process = new ProcessBuilder("bash", "-c", s);
		process.start();

	}


	/*
	The "speakEnglishAndMaori" calls a bash process that used a text-to-speech in order
	to say the English word followed by a Maori word

	@params: a string of the English word to be said, a string of the Maori word to be said,
	 */
	public static void speakEnglishAndMaori(String englishText, String maoriText) throws IOException, InterruptedException {
		//create a new file to be read out
		File f = new File("./resources/tempFiles/.speech.scm");
		f.createNewFile();
		String textSpeed = Double.toString(SettingsController.getTextToSpeechSpeed());

		maoriText = maoriText.replace("-", " ");

		//write into the file
		PrintWriter writer = new PrintWriter("./resources/tempFiles/.speech.scm");
		writer.print("(voice_akl_nz_jdt_diphone)");
		writer.append("\n" + "(SayText \"" + englishText + "\")");
		writer.append("\n" + "(voice_akl_mi_pk06_cg)");
		writer.append("\n" + "(Parameter.set 'Duration_Stretch " + textSpeed + ")");
		writer.append("\n" + "(SayText \"" + maoriText + "\")");
		writer.close();

		//call the bash command
		String command = "festival -b .speech.scm";
		ProcessBuilder process = new ProcessBuilder("bash", "-c", command);
		process = process.directory(new File("./resources/tempFiles/"));
		process.start();
	}


	/*
	The "sayMaoriEnglishSpeech" calls a bash process that used a text-to-speech in order
	to say the English word followed by a Maori word

	@params: a string of the Maori word to be said, a string of the English word to be said
	 */
	public static void sayMaoriEnglishSpeech(String englishText, String maoriText) throws IOException, InterruptedException {
		//create a new file to be read out
		File f = new File("./resources/tempFiles/.speech.scm");
		f.createNewFile();

		maoriText = maoriText.replace("-", " ");

		//write into the file
		PrintWriter writer = new PrintWriter("./resources/tempFiles/.speech.scm");
		writer.print("(voice_akl_mi_pk06_cg)");
		writer.append("\n" + "(SayText \"" + maoriText + "\")");
		writer.append("\n" + "(voice_akl_nz_jdt_diphone)");
		writer.append("\n" + "(SayText \"" + englishText + "\")");
		writer.close();

		//call the bash command
		String command = "festival -b .speech.scm";
		ProcessBuilder process = new ProcessBuilder("bash", "-c", command);
		process = process.directory(new File("./resources/tempFiles/"));
		process.start();
	}

	/*
	The "sortTxtFile" calls a bash process that used a sorts a given text file
	in reverse order (high to low)

	@params: a theme relating to a text file to be sorted
	 */
	public static void sortTxtFile(String theme) throws IOException, InterruptedException {
		//select the file for the corect theme
		String fileDestination = "./Leaderboard/" + theme + ".txt";
		String command = "sort -ruo " + fileDestination + " " + fileDestination;
		ProcessBuilder process = new ProcessBuilder("bash", "-c", command);
		process = process.directory(new File("./resources"));
		process.start();
	}

	/*
	The "sortTxtFile" calls a bash process that clears a given text file

	@params: a theme relating to a text file to be cleared
	 */
	public static void clearFile(String theme) throws IOException {
		//clear the file if a theme has been selected
		if (theme != null) {
			String currentTheme = theme.replace(" ", ""); //remove spaces from any theme
			String fileDestination = "./resources/Leaderboard/" + currentTheme + ".txt";
			String command = "cat /dev/null >" + fileDestination;
			ProcessBuilder process = new ProcessBuilder("bash", "-c", command);
			process.start();
		}
	}


}