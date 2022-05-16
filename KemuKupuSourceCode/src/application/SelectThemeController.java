package application;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * The SelectThemeController class is the controller class for the SelectTheme.fxml scene.
 * The class implements all functionality required for selecting a theme, and then starting
 * either a new quiz or a practice quiz.
 */

public class SelectThemeController {

	// ImageViews for button animations in SelectThemeController
	@FXML private ImageView settingsHover;	@FXML private ImageView settingsNormal;	@FXML private ImageView settingsClicked;
	@FXML private ImageView homeHover;	@FXML private ImageView homeClicked;
	@FXML private ImageView dotw1H;
	@FXML private ImageView dotw2H;
	@FXML private ImageView moty1H;
	@FXML private ImageView moty2H;
	@FXML private ImageView coloursH;
	@FXML private ImageView babiesH;
	@FXML private ImageView weatherH;
	@FXML private ImageView compassPointsH;
	@FXML private ImageView feelingsH;
	@FXML private ImageView workH;
	@FXML private ImageView engineeringH;
	@FXML private ImageView softwareH;
	@FXML private ImageView uniLifeH;

	//stage, scene and root for SelectThemeController
	private Stage primaryStage;
	private Scene scene;
	private Parent root;

	private static String module; // String that stores the module type (new quiz or practice)

	/**
	 * Method to switch to the main menu
	 * @param event : mouse click
	 * @throws IOException
	 */
	public void switchToMain(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("FXML/Main.fxml"));
		primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Method to switch to new quiz
	 * @param event : mouse click
	 * @throws IOException
	 */
	private void switchToNewQuiz(ActionEvent event, String theme) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/NewQuiz.fxml"));
		root = loader.load();	
		
		NewQuizController newQuiz1 = loader.getController();
		newQuiz1.startTimer(); // starting the timer for a new quiz
		newQuiz1.setTheme(theme); // setting the theme to display in new quiz

		String selectedTheme = theme.replace(" ", "");
		RewardScreenController.setTheme(selectedTheme);
		LeaderboardController.setTheme(theme);

		// switching to new quiz scene
		primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		NewQuizController.newSpellingQuiz();;
		newQuiz1.displayFirstBlank();
	}

	/**
	 * Method to switch to practice quiz
	 * @param event : mouse click
	 * @throws IOException
	 */
	private void switchToPracticeModule(ActionEvent event, String theme) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/PracticeModule.fxml"));
		root = loader.load();	

		PracticeModuleController practiceQuiz1 = loader.getController();
		PracticeModuleController.newPracticeQuiz();
		practiceQuiz1.displayFirstBlank(); // setting the number of letters for the first word
		practiceQuiz1.setTheme(theme); // setting the theme to display in practice quiz

		//switching to practice quiz scene
		primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Method that shows the settings popup
	 * @param event : mouse click
	 * @throws IOException
	 */
	public void showSettings(MouseEvent event) throws IOException {
		SettingsController.showSettings(event);
	}

	/**
	 * Method that generates one random word
	 * @param filename : file to get the word from
	 * @return : random word generated
	 * @throws FileNotFoundException
	 */
	public String getRandomWord(String filename) throws FileNotFoundException {
		File f = new File("./resources/words/" + filename);
		String randomWord = null;
		Random r = new Random();
		int i = 0;
		Scanner sc = new Scanner(f);
		while (sc.hasNext()) {
			++i;
			String line = sc.nextLine();
			if (r.nextInt(i) == 0)
				randomWord = line;
		}
		sc.close();
		return randomWord;
	}

	/**
	 * Function that generates three random words and puts it into a text file
	 * @param filename : file to get the words from
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void generateWords(String filename) throws IOException, InterruptedException {
		String word1 = getRandomWord(filename); // getting the first random word
		String word2 = getRandomWord(filename); // getting the second random word
		// ensuring the first two words are not the same
		while (word2.equals(word1)) { 
			word2 = getRandomWord(filename);
		}

		String word3 = getRandomWord(filename); // getting the third random word
		// ensuring the third word is not duplicate
		while ((word3.equals(word1)) || (word3.equals(word2))) { 
			word3 = getRandomWord(filename);
		}

		String word4 = getRandomWord(filename); // getting the fourth random word
		// ensuring the fourth word is not duplicate
		while ((word4.equals(word1)) || (word4.equals(word2)) || (word4.equals(word3))) { 
			word4 = getRandomWord(filename);
		}

		String word5 = getRandomWord(filename); // getting the fifth random word
		// ensuring the fifth word is not duplicate
		while ((word5.equals(word1)) || (word5.equals(word2)) || (word5.equals(word3)) || (word5.equals(word4))) {
			word5 = getRandomWord(filename);
		}
		
		// creating a new file to store the words
		new File("./resources/tempFiles").mkdirs(); 
		File randomWords = new File("./resources/tempFiles/.randomWords.txt");
		randomWords.createNewFile();

		//writing words into the file
		Writer output;
		output = new BufferedWriter(new FileWriter("./resources/tempFiles/.randomWords.txt", true));
		output.append(word1);
		output.append("\n" + word2);
		output.append("\n" + word3);
		output.append("\n" + word4);
		output.append("\n" + word5);
		output.close();
		BashProcess.sayMaoriWord(word1); //speaking the first word
	}

	/**
	 * Methods to select a theme. Switches to new/practice quiz with the selected theme.
	 * @param event : mouse click
	 * @throws IOException
	 * @throws InterruptedException
	 */
	
	// Method for days of the week 1
	public void daysOfTheWeek1(ActionEvent event) throws IOException, InterruptedException {
		generateWords("Days Of The Week 1.txt");
		if (module.equals("quiz")) {
			switchToNewQuiz(event, "Days Of The Week 1");
		} else {
			switchToPracticeModule(event, "Days Of The Week 1");
		}
	}
	
	// Method for days of the week 2
	public void daysOfTheWeek2(ActionEvent event) throws IOException, InterruptedException {
		generateWords("Days Of The Week 2.txt");
		if (module.equals("quiz")) {
			switchToNewQuiz(event, "Days Of The Week 2");
		} else {
			switchToPracticeModule(event, "Days Of The Week 2");
		}
	}
	
	// Method for months of the year 1
	public void monthsOfTheYear1(ActionEvent event) throws IOException, InterruptedException {
		generateWords("Months Of The Year 1.txt");
		if (module.equals("quiz")) {
			switchToNewQuiz(event, "Months Of The Year 1");
		} else {
			switchToPracticeModule(event, "Months Of The Year 1");
		}
	}
	
	// Method for months of the year 2
	public void monthsOfTheYear2(ActionEvent event) throws IOException, InterruptedException {
		generateWords("Months Of The Year 2.txt");
		if (module.equals("quiz")) {
			switchToNewQuiz(event, "Months Of The Year 2");
		} else {
			switchToPracticeModule(event, "Months Of The Year 2");
		}
	}
	
	// Method for colours
	public void colours(ActionEvent event) throws IOException, InterruptedException {
		generateWords("Colours.txt");
		if (module.equals("quiz")) {
			switchToNewQuiz(event, "Colours");
		} else {
			switchToPracticeModule(event, "Colours");
		}
	}
	
	// Method for babies
	public void babies(ActionEvent event) throws IOException, InterruptedException {
		generateWords("Babies.txt");
		if (module.equals("quiz")) {
			switchToNewQuiz(event, "Babies");
		} else {
			switchToPracticeModule(event, "Babies");
		}
	}
	
	// Method for weather
	public void weather(ActionEvent event) throws IOException, InterruptedException {
		generateWords("Weather.txt");
		if (module.equals("quiz")) {
			switchToNewQuiz(event, "Weather");
		} else {
			switchToPracticeModule(event, "Weather");
		}
	}
	
	// Method for compass points
	public void compassPoints(ActionEvent event) throws IOException, InterruptedException {
		generateWords("Compass Points.txt");
		if (module.equals("quiz")) {
			switchToNewQuiz(event, "Compass Points");
		} else {
			switchToPracticeModule(event, "Compass Points");
		}
	}
	
	// Method for feelings
	public void feelings(ActionEvent event) throws IOException, InterruptedException {
		generateWords("Feelings.txt");
		if (module.equals("quiz")) {
			switchToNewQuiz(event, "Feelings");
		} else {
			switchToPracticeModule(event, "Feelings");
		}
	}
	
	// Method for work
	public void work(ActionEvent event) throws IOException, InterruptedException {
		generateWords("Work.txt");
		if (module.equals("quiz")) {
			switchToNewQuiz(event, "Work");
		} else {
			switchToPracticeModule(event, "Work");
		}
	}
	
	// Method for engineering
	public void engineering(ActionEvent event) throws IOException, InterruptedException {
		generateWords("Engineering.txt");
		if (module.equals("quiz")) {
			switchToNewQuiz(event, "Engineering");
		} else {
			switchToPracticeModule(event, "Engineering");
		}
	}
	
	// Method for software
	public void software(ActionEvent event) throws IOException, InterruptedException {
		generateWords("Software.txt");
		if (module.equals("quiz")) {
			switchToNewQuiz(event, "Software");
		} else {
			switchToPracticeModule(event, "Software");
		}
	}
	
	// Method for uni life
	public void uniLife(ActionEvent event) throws IOException, InterruptedException {
		generateWords("Uni Life.txt");
		if (module.equals("quiz")) {
			switchToNewQuiz(event, "Uni Life");
		} else {
			switchToPracticeModule(event, "Uni Life");
		}
	}

	/**
	 * Method that sets the module type (new quiz or practice)
	 * @param mod : module type (String)
	 */
	public static void setModule(String mod) {
		module = mod;
	}


	// ---------------- BUTTON ANIMATION METHODS ----------------
	// The following methods are used for displaying different
	// images when buttons are hovered over or clicked
	
	// Settings button animations
	public void showSettingsGlow() {settingsHover.setVisible(true);}
	public void hideSettingsGlow() {settingsHover.setVisible(false);}
	public void showSettingsClicked() {settingsClicked.setVisible(true);}
	public void hideSettingsClicked() {settingsClicked.setVisible(false);}

	// Home button animations
	public void showHomeGlow() {homeHover.setVisible(true);}
	public void hideHomeGlow() {homeHover.setVisible(false);}
	public void showHomeClicked() {homeClicked.setVisible(true);}
	public void hideHomeClicked() {homeClicked.setVisible(false);}

	// Button animations for each theme
	public void showDotw1() { dotw1H.setVisible(true); }
	public void hideDotw1() { dotw1H.setVisible(false); }

	public void showDotw2() { dotw2H.setVisible(true); }
	public void hideDotw2() { dotw2H.setVisible(false); }

	public void showMoty1() { moty1H.setVisible(true); }
	public void hideMoty1() { moty1H.setVisible(false); }

	public void showMoty2() { moty2H.setVisible(true); }
	public void hideMoty2() { moty2H.setVisible(false); }

	public void showColours() { coloursH.setVisible(true); }
	public void hideColours() { coloursH.setVisible(false); }

	public void showBabies() { babiesH.setVisible(true); }
	public void hideBabies() { babiesH.setVisible(false); }

	public void showFeelings() { feelingsH.setVisible(true); }
	public void hideFeelings() { feelingsH.setVisible(false); }

	public void showUniLife() { uniLifeH.setVisible(true); }
	public void hideUniLife() { uniLifeH.setVisible(false); }

	public void showSoftware() { softwareH.setVisible(true); }
	public void hideSoftware() { softwareH.setVisible(false); }

	public void showEngineering() { engineeringH.setVisible(true); }
	public void hideEngineering() { engineeringH.setVisible(false); }

	public void showCompassPoints() { compassPointsH.setVisible(true); }
	public void hideCompassPoints() { compassPointsH.setVisible(false); }

	public void showWork() { workH.setVisible(true); }
	public void hideWork() { workH.setVisible(false); }

	public void showWeather() { weatherH.setVisible(true); }
	public void hideWeather() { weatherH.setVisible(false); }
}