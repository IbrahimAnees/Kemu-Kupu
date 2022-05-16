package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import javafx.animation.PauseTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The PracticeModuleController class is the controller class for the PracticeModule.fxml scene.
 * The class implements all functionality required for starting a practice quiz, including
 * showing hints and checking for correct spelling of Te Reo words.
 */

public class PracticeModuleController {
	@FXML private TextField textBox; //text field for user to input answer
	@FXML private Text changeLabel; //label to display which word the user is currently spelling
	@FXML private Text message; //label to display encouraging message
	@FXML private Text hint; //label to show a hint
	@FXML private Label correct; //label to show is the user was correct/incorrect
	@FXML private Label themeLabel; // label to show current theme
	@FXML private Label previousLabel; // label to show previous word

	// All ImageViews required for button animations in PracticeModuleController

	@FXML private ImageView settingsNormal;	@FXML private ImageView settingsClicked;	@FXML private ImageView settingsHover;
	@FXML private ImageView homeHover;		@FXML private ImageView homeClicked;
	@FXML private ImageView replayHover;	@FXML private ImageView replayClicked;
	@FXML private ImageView dkHover;		@FXML private ImageView dkClicked;
	@FXML private ImageView submitHover;	@FXML private ImageView submitClicked;
	@FXML private ImageView hintClicked;	@FXML private ImageView hintHover;
	@FXML private ImageView aMacronHover; 	@FXML private ImageView aMacronClicked; 	@FXML private ImageView aMacronNormal;
	@FXML private ImageView eMacronHover; 	@FXML private ImageView eMacronClicked; 	@FXML private ImageView eMacronNormal;
	@FXML private ImageView iMacronHover; 	@FXML private ImageView iMacronClicked; 	@FXML private ImageView iMacronNormal;
	@FXML private ImageView oMacronHover; 	@FXML private ImageView oMacronClicked; 	@FXML private ImageView oMacronNormal;
	@FXML private ImageView uMacronHover; 	@FXML private ImageView uMacronClicked; 	@FXML private ImageView uMacronNormal;

	// All buttons used in PracticeModuleController
	@FXML private Button aMacronButton;
	@FXML private Button eMacronButton;
	@FXML private Button iMacronButton;
	@FXML private Button oMacronButton;
	@FXML private Button uMacronButton;
	@FXML private Button settingsButton;
	@FXML private Button submitButton;
	@FXML private Button hintButton;
	@FXML private Button skipButton;
	@FXML private Button replayButton;

	// Stage, scene and root for PracticeModuleController
	private Stage primaryStage;
	private Scene scene;
	private Parent root;


	private static int wordNum; //variable to keep track of which word the user is spelling
	private static ArrayList<String> words; //list to store words to be spelt
	private static ArrayList<String> mastered;
	private static ArrayList<String> faulted;
	private static ArrayList<String> failed;
	private static int attempt; //variable to keep track if it is the user's first/second attempt
	private static boolean hintShown; // Boolean variable to control whether the hint has already been shown or not

	private static BooleanProperty showMacron = new SimpleBooleanProperty(true); // Boolean variable to control hiding macrons


	/**
	 * Method that shows/hides macrons
	 * @param value : Boolean variable to control hiding/showing macrons
	 */
	public static void setShowMacron(boolean value) {
		showMacron.set(value);
	}

	/**
	 * Method to disable all buttons when bash process is running (for error handling)
	 */
	public void disableAllButtons() {
		replayButton.setDisable(true);
		submitButton.setDisable(true);
		skipButton.setDisable(true);
		hintButton.setDisable(true);
	}

	/**
	 * Method to enable all buttons after bash process ends (for error handling)
	 */
	public void enableAllButtons() {
		replayButton.setDisable(false);
		skipButton.setDisable(false);
		hintButton.setDisable(false);
		if (textBox.getText().isEmpty()) {
			//if empty, disable the submit button to prevent accidental submits
			submitButton.setDisable(true);
		} else {
			submitButton.setDisable(false);

		}
	}

	/*
	The "newPractQuiz" is called from the Select Theme Controller class in order
	to initialize the given fields before the quiz begins
	 */
	public static void newPracticeQuiz() throws IOException {
		//this function is used to initialise the start of a new spelling quiz

		wordNum = 0; //initialise to get the first word in the array list
		words = new ArrayList<>();
		mastered = new ArrayList<>();
		faulted = new ArrayList<>();
		failed = new ArrayList<>();
		attempt = 1; //set to first attempt
		hintShown = false;
		getWords(); //get 5 words from the selected theme
	}

	/**
	 * Method that controls macron inputs
	 */
	public void initialize() {
		//add a listener to the text field to input the macrons
		// ACKNOWLEDGEMENT | Code adapted from: https://stackoverflow.com/questions/40757911/javafx-adding-actionlistener-to-button 
		try {
			textBox.textProperty().addListener((obs, oldValue, newValue) -> {
				if (newValue.contains("*a") || newValue.contains("*e") || newValue.contains("*i") || newValue.contains("*o") || newValue.contains("*u") ||
						newValue.contains("*A") || newValue.contains("*E") || newValue.contains("*I") || newValue.contains("*O") || newValue.contains("*U")	) {
					textBox.setText(textBox.getText().replace("*a", "ā"));
					textBox.setText(textBox.getText().replace("*e", "ē"));
					textBox.setText(textBox.getText().replace("*i", "ī"));
					textBox.setText(textBox.getText().replace("*o", "ō"));
					textBox.setText(textBox.getText().replace("*u", "ū"));
					textBox.setText(textBox.getText().replace("*A", "Ā"));
					textBox.setText(textBox.getText().replace("*E", "Ē"));
					textBox.setText(textBox.getText().replace("*I", "Ī"));
					textBox.setText(textBox.getText().replace("*O", "Ō"));
					textBox.setText(textBox.getText().replace("*U", "Ū"));
				}
				//check if text field is empty, disable the submit button to prevent accidental submits
				if (textBox.getText().isEmpty()) {
					//if empty, disable the submit button to prevent accidental submits
					submitButton.setDisable(true);

				} else {
					submitButton.setDisable(false);

				}
			});
		} catch (Exception e) {

		}
		//add a listener to check to show/timer the macron buttons
		showMacron.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (showMacron.getValue().equals(true)) {
					showMacronButtons();
				} 
				else{
					hideMacronButtons();
				}

			}
		});
		//run when initialising the scene, show/hide macron buttons depending on the checkbox
		if (showMacron.getValue().equals(true)) {
			showMacronButtons();
		} 
		else{
			hideMacronButtons();
		}
	}

	/**
	 * Method that hides macron buttons when the user chooses to hide macrons
	 */
	public void hideMacronButtons() {
		aMacronButton.setVisible(false);		aMacronNormal.setVisible(false);
		eMacronButton.setVisible(false);		eMacronNormal.setVisible(false);
		iMacronButton.setVisible(false);		iMacronNormal.setVisible(false);
		oMacronButton.setVisible(false);		oMacronNormal.setVisible(false);
		uMacronButton.setVisible(false);		uMacronNormal.setVisible(false);
	}

	/**
	 * Method that shows macron buttons when the user choses to show macrons
	 */
	public void showMacronButtons() {
		aMacronButton.setVisible(true);		aMacronNormal.setVisible(true);
		eMacronButton.setVisible(true);		eMacronNormal.setVisible(true);
		iMacronButton.setVisible(true);		iMacronNormal.setVisible(true);
		oMacronButton.setVisible(true);		oMacronNormal.setVisible(true);
		uMacronButton.setVisible(true);		uMacronNormal.setVisible(true);
	}

	public void setTheme(String theme) {
		themeLabel.setText(theme);
	}

	/**
	The "deleteDirectory" function deletes the directory containing the words from the last quiz
	 */
	public static void deleteDirectory(File file) {
		for (File subfile : file.listFiles()) {
			if (subfile.isDirectory()) {
				deleteDirectory(subfile);
			}
			subfile.delete();
		}
	}	

	/**
	 * Method to switch to the main menu
	 * @param event : mouse click
	 * @throws IOException
	 */
	public void switchToMain(ActionEvent event) throws IOException {
		// deleting all temp files
		File file = new File("./resources/tempFiles");
		deleteDirectory(file);
		file.delete();

		//return to the main menu
		root = FXMLLoader.load(getClass().getResource("FXML/Main.fxml"));
		primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Method to switch to practice end
	 * @param event : mouse click
	 * @throws IOException
	 */
	private void switchToPracticeEnd(ActionEvent event) throws IOException {
		// deleting all temp files
		File file = new File("./resources/tempFiles");
		deleteDirectory(file);
		file.delete();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/PracticeEnd.fxml"));
		root = loader.load();	

		PracticeEndController practiceEndInstance = loader.getController();
		practiceEndInstance.displayWords(mastered, faulted, failed); // displaying words on reward screen

		//switch to practice end at the end of the quiz
		primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	The "getWords" method gets 5 random words from the selected theme
	 */
	public static void getWords() throws IOException {
		// Storing the five words from the temp file into string variables
		String word1 = Files.lines(Paths.get("./resources/tempFiles/.randomWords.txt")).skip(0).findFirst().get();
		String word2 = Files.lines(Paths.get("./resources/tempFiles/.randomWords.txt")).skip(1).findFirst().get();
		String word3 = Files.lines(Paths.get("./resources/tempFiles/.randomWords.txt")).skip(2).findFirst().get();
		String word4 = Files.lines(Paths.get("./resources/tempFiles/.randomWords.txt")).skip(3).findFirst().get();
		String word5 = Files.lines(Paths.get("./resources/tempFiles/.randomWords.txt")).skip(4).findFirst().get();

		// Adding words to the 'words' ArrayList
		words.add(word1);
		words.add(word2);
		words.add(word3);
		words.add(word4);
		words.add(word5);
	}

	/**
	 * Helper method for showCorrectText.
	 * This method shows the text "correct" for a short duration
	 */
	public void correct() {
		PauseTransition wait = new PauseTransition(Duration.seconds(2));
		correct.setText("Correct!");
		disableAllButtons();

		correct.setTextFill(Color.rgb(50, 221, 82)); // setting text colour to green
		wait.setOnFinished((e) -> {
			correct.setText(" ");
			enableAllButtons();
		});
		wait.play();
	}

	/**
	 * Helper method for showCorrectText.
	 * This method shows the text "incorrect" for a short duration
	 */
	public void incorrect() {
		PauseTransition wait = new PauseTransition(Duration.seconds(2));
		correct.setText("Incorrect!");
		disableAllButtons();
		correct.setTextFill(Color.rgb(176, 18, 18)); // setting text colour to red
		wait.setOnFinished((e) -> {
			correct.setText(" ");
			enableAllButtons();
		});
		wait.play();
	}

	/**
	 * Helper method for showCorrectText.
	 * This method shows the text "word skipped" for a short duration
	 */
	public void skip() {
		PauseTransition wait = new PauseTransition(Duration.seconds(2));
		correct.setText("Word skipped!"); // setting text colour to orange
		disableAllButtons();
		correct.setTextFill(Color.rgb(201, 140, 42));
		wait.setOnFinished((e) -> {
			correct.setText(" ");
			enableAllButtons();
		});
		wait.play();
	}

	/**
	 * Helper method for showCorrectText.
	 * This method shows the text "incorrect, try again" for a short duration
	 */
	public void incorrectFirstAttempt() {
		PauseTransition wait = new PauseTransition(Duration.seconds(3));
		correct.setText("Incorrect, try again!");
		disableAllButtons();
		correct.setTextFill(Color.rgb(176, 18, 18)); // setting text colour to red
		wait.setOnFinished((e) -> {
			correct.setText(" ");
			enableAllButtons();
		});
		wait.play();
	}

	/**
	 * Method that displays a brief text reflecting the actions done during the quiz
	 * @param s : input on what text to display
	 */
	public void showCorrectText(String textToBeShown) {
		//this function displays a brief text reflecting the actions done

		if (textToBeShown.equals("correct")) {
			//if word was spelt correctly
			correct();
		}
		else if (textToBeShown.equals("incorrect")) {
			//if word was spelt incorrectly on the second attempt
			incorrect();
		}
		else if (textToBeShown.equals("skip")) {
			//if word was skipped
			skip();
		}
		else {
			//if word was spelt incorrectly on the first attempt
			incorrectFirstAttempt();
		}
	}

	/**
	 * Method that replays the current word
	 * @param event : mouse click
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void onReplayWordButton(ActionEvent event) throws IOException, InterruptedException {
		//reply the word when button is pressed
		BashProcess.sayMaoriWord(words.get(wordNum));
		// disable other buttons for 2 seconds (for error handling)
		PauseTransition wait = new PauseTransition(Duration.seconds(2));
		disableAllButtons();
		wait.setOnFinished((e) -> {
			enableAllButtons();
		});
		wait.play();
	}

	/**
	 * Method that shows hint for the current word
	 * @param event : mouse click
	 * @throws IOException
	 */
	public void onShowHintButton(ActionEvent event) throws IOException {
		//show the hint on button pressed
		if (!hintShown) {
			String hint1 = words.get(wordNum);
			revealLetters(hint1);
		}
	}

	/**
	 * Method that skips the current word
	 * @param event : mouse click
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void onDontKnowButton(ActionEvent event) throws IOException, InterruptedException {
		//function when the 'dont know' button is pressed
		failed.add(words.get(wordNum));
		textBox.clear();
		wordNum++; //go to the next word
		message.setText(" ");
		showCorrectText("skip");
		previousLabel.setText(words.get(wordNum-1)); // show previous word
		attempt = 1;
		hintShown = false;

		if (wordNum == 5) {
			//if the last word is skipped, go the the reward screen
			switchToPracticeEnd(event);
		} else {
			String nextWord = words.get(wordNum);
			BashProcess.sayMaoriWord(nextWord);
			//			System.out.println("2nd word: " + nextWord);
			displayMessage();
			hint.setText(convertToBlank(words.get(wordNum)));

			int wordNumber = wordNum + 1; //word number to be displayed on screen
			changeLabel.setText("Word " + wordNumber + " of 5");
		}
	}

	/**
	The "onSubmitButton" is called when the submit button is pressed. It checks
	if the word is spelt correctly. If it is, then move on to the next word. If they have
	spelt the word incorrectly on their first attempt they get another chance

	@param event : submit button on click
	 **/
	public void onSubmitButton(ActionEvent event) throws IOException, InterruptedException {
		//function for when the submit button is pressed

		String word = words.get(wordNum); //get the current word

		String spellingAttempt = textBox.getText();
		message.setText(" ");

		if (spellingAttempt.equalsIgnoreCase(word)) {
			//if the word is spelt correctly
			textBox.clear();
			hintShown = false;

			if (wordNum != 4) {
				hint.setText(convertToBlank(words.get(wordNum + 1)));
			}

			if (attempt ==2 ) {
				faulted.add(words.get(wordNum));
				//add 0.5 to the score if the word is spelt correctly on the second attempt
			} else {
				mastered.add(words.get(wordNum));
				//add 1 to score if spelt correctly first try
			}		
			wordNum++;

			if (wordNum < 5) {
				//if last word is not yet reached
				word = words.get(wordNum);
				showCorrectText("correct");
				previousLabel.setText(words.get(wordNum-1)); // show previous word
				//say the next word
				BashProcess.speakEnglishAndMaori("Correct!", word);
				//				System.out.println("2nd word: " + word);
			} else if (wordNum == 5) {
				//if last word
				showCorrectText("correct");
				BashProcess.speakEnglishWord("Correct");
				previousLabel.setText(words.get(wordNum-1)); // show previous word
			}
			int number = wordNum + 1;
			changeLabel.setText("Word " + number + " of 5");
			attempt = 1;
		} else {
			//word is spelt incorrectly
			if (attempt == 2) {
				failed.add(words.get(wordNum));
				//second attempt
				textBox.clear();
				displayMessage();
				hintShown = false;

				//move on to the next word
				wordNum++;
				int wordNumber = wordNum + 1;
				changeLabel.setText("Word " + wordNumber + " of 5");

				if (wordNum != 5) {
					hint.setText(convertToBlank(words.get(wordNum)));
				}

				if (wordNum < 5) {
					//if last word is not yet reached
					word = words.get(wordNum);
					//say the next word
					BashProcess.speakEnglishAndMaori("Incorrect!", word);
					showCorrectText("incorrect");
					previousLabel.setText(words.get(wordNum-1)); // show previous word
					//					System.out.println("2nd word: " + word);
				} else if (wordNum == 5) {
					//if last word
					BashProcess.speakEnglishWord("Incorrect");
					showCorrectText("incorrect");
					previousLabel.setText(words.get(wordNum-1)); // show previous word
				}
				attempt = 1; //reset the attempt variable

			} else {
				//first attempt
				if (!hintShown) {
					revealLetters(word); //show hint
				}
				BashProcess.speakEnglishAndMaori("Incorrect, try again!", word); //replay the word
				showCorrectText("incorrectTryAgain");
				attempt++;
			}		

		}

		if (wordNum == 5) {
			//if last word is complete
			switchToPracticeEnd(event);
		}
	}

	/**
	The "convertToBlank" method shows the number of letters in the word that is to be spelt

	@params: word: the current word
	@return: String: the number of letters in the word using "_"
	 **/
	public static String convertToBlank(String word) {
		String hintWord = "";
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == ' ') {
				hintWord+= "  ";
			} else if (word.charAt(i) == '-') {
				hintWord+= " -";
			} else {
				hintWord+= " _";
			}
		}
		return hintWord;
	}

	/**
	 * Method that displays the first hint
	 */
	public void displayFirstBlank() {
		//initialized for the first word to be spelt 
		hint.setText(convertToBlank(words.get(0)));
	}

	/**
	 * Method that displays random letters as hints for the user
	 * @param word : String containing the current word
	 */
	public void revealLetters(String word) {
		String blank = convertToBlank(word);
		int wordLength = word.length();
		Random rand = new Random(); // generating random number to use as index for hint
		int max = wordLength - 1;
		int firstRandomNum = Integer.MAX_VALUE;

		int charRevealNum = 3;

		if (wordLength < 5) { // revealing one character if the word is less than 5 letters
			charRevealNum = 1;
		} else if (wordLength < 9) { // revealing two characters if the word is 5-8 letters
			charRevealNum = 2;
		}

		for (int i = 0; i < charRevealNum; i++) {			
			int randomNum = rand.nextInt(max);

			while (firstRandomNum == randomNum) {
				randomNum = rand.nextInt(max);
			}
			firstRandomNum = randomNum;
			char ch = word.charAt(randomNum);
			blank = blank.substring(0, randomNum*2) + " " + ch + blank.substring(randomNum*2 + 2);
		}
		hint.setText(blank); // setting the hint on the hint text
		hintShown = true;
	}

	/*
	The "displayMessage" displays a random encouraging message when the user gets a word wrong
	or they decide to skip the word
	 */
	public void displayMessage() {
		// generating a random number to display a random message
		Random random = new Random();
		int randomNumber = random.nextInt(10) + 1;

		// Selecting a message to display based on the random number generated
		switch (randomNumber) {
		case 1: message.setText("Don't give up!"); break;
		case 2: message.setText("Nothing is impossible!"); break;
		case 3: message.setText("You'll get it right next time!"); break;
		case 4: message.setText("Don't stress, you got this!"); break;
		case 5: message.setText("You learn from your mistakes!"); break;
		case 6: message.setText("This is tough, but you're tougher!"); break;
		case 7: message.setText("Believe in yourself!"); break;
		case 8: message.setText("The best way to success is to keep trying!"); break;
		case 9: message.setText("Being wrong is better than not trying!"); break;
		default: message.setText("Mountains are there to be climbed!"); break;
		}

	}

	/**
	 * Method that shows the settings popup
	 * @param event : mouse click
	 * @throws IOException
	 */
	public void showSettings(MouseEvent event) throws IOException {
		SettingsController.showSettings(event);
	}

	public void aMacron(ActionEvent event) { textBox.setText(textBox.getText() + "ā"); }
	public void eMacron(ActionEvent event) { textBox.setText(textBox.getText() + "ē"); }
	public void iMacron(ActionEvent event) { textBox.setText(textBox.getText() + "ī"); }
	public void oMacron(ActionEvent event) { textBox.setText(textBox.getText() + "ō"); }
	public void uMacron(ActionEvent event) { textBox.setText(textBox.getText() + "ū"); }

	// ---------------- BUTTON ANIMATION METHODS ----------------
	// The following methods are used for displaying different
	// images when buttons are hovered over or clicked

	// Settings button animation
	public void showSettingsGlow() {settingsHover.setVisible(true);}
	public void hideSettingsGlow() {settingsHover.setVisible(false);}
	public void showSettingsClicked() {settingsClicked.setVisible(true);}
	public void hideSettingsClicked() {settingsClicked.setVisible(false);}

	// Home button animation
	public void showHomeGlow() {homeHover.setVisible(true);}
	public void hideHomeGlow() {homeHover.setVisible(false);}
	public void showHomeClicked() {homeClicked.setVisible(true);}
	public void hideHomeClicked() {homeClicked.setVisible(false);}

	// Don't know button animation
	public void showDkGlow() {dkHover.setVisible(true);}
	public void hideDkGlow() {dkHover.setVisible(false);}
	public void showDkClicked() {dkClicked.setVisible(true);}
	public void hideDkClicked() {dkClicked.setVisible(false);}

	// Submit button animation
	public void showSubmitGlow() {submitHover.setVisible(true);}
	public void hideSubmitGlow() {submitHover.setVisible(false);}
	public void showSubmitClicked() {submitClicked.setVisible(true);}
	public void hideSubmitClicked() {submitClicked.setVisible(false);}


	// Replay button animation
	public void showReplayGlow() {replayHover.setVisible(true);}
	public void hideReplayGlow() {replayHover.setVisible(false);}
	public void showReplayClicked() {replayClicked.setVisible(true);}
	public void hideReplayClicked() {replayClicked.setVisible(false);}

	// Show hint button animation
	public void showHintGlow() {hintHover.setVisible(true);}
	public void hideHintGlow() {hintHover.setVisible(false);}
	public void showHintClicked() {hintClicked.setVisible(true);}
	public void hideHintClicked() {hintClicked.setVisible(false);}

	// 'a' macron button animation
	public void showAGlow() { aMacronHover.setVisible(true); }
	public void hideAGlow() { aMacronHover.setVisible(false); }
	public void showAClicked() { aMacronClicked.setVisible(true); }
	public void hideAClicked() { aMacronClicked.setVisible(false); }

	// 'e' macron button animation
	public void showEGlow() { eMacronHover.setVisible(true); }
	public void hideEGlow() { eMacronHover.setVisible(false); }
	public void showEClicked() { eMacronClicked.setVisible(true); }
	public void hideEClicked() { eMacronClicked.setVisible(false); }

	// 'i' macron button animation
	public void showIGlow() { iMacronHover.setVisible(true); }
	public void hideIGlow() { iMacronHover.setVisible(false); }
	public void showIClicked() { iMacronClicked.setVisible(true); }
	public void hideIClicked() { iMacronClicked.setVisible(false); }

	// 'o' macron button animation
	public void showOGlow() { oMacronHover.setVisible(true); }
	public void hideOGlow() { oMacronHover.setVisible(false); }
	public void showOClicked() { oMacronClicked.setVisible(true); }
	public void hideOClicked() { oMacronClicked.setVisible(false); }

	// 'u' macron button animation
	public void showUGlow() { uMacronHover.setVisible(true); }
	public void hideUGlow() { uMacronHover.setVisible(false); }
	public void showUClicked() { uMacronClicked.setVisible(true); }
	public void hideUClicked() { uMacronClicked.setVisible(false); }
}
