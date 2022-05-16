package application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class is a controller for the reward screen scene
 *
 * Words that have been spelt in the previous quiz are listed here
 * as well as the option to save a user name to the scorebaord
 */
public class RewardScreenController {

	@FXML Text scoreText; // Text that displays 'score:'
	@FXML Label masteredLabel; // Label that dispalys mastered words
	@FXML Label faultedLabel; // Label that displays faulted words
	@FXML Label failedLabel; // Label that displays failed words
	@FXML Label realScoreText; // Label that displays the real score
	@FXML Text timeText; // Text that displays the time taken to complete the quiz
	@FXML TextField nameInput; // TextField for the user to enter a name
	@FXML Button submitButton; // Submit button
	@FXML Button goToLeaderboard; // Go to leaderboard button
	@FXML Label submittedLabel; // Label that shows that the user has submitted their score
	
	// All ImageViews required for button animations in RewardScreenController
	@FXML private ImageView settingsHover;	@FXML private ImageView settingsNormal;		@FXML private ImageView settingsClicked;
	@FXML private ImageView saveHover; 		@FXML private ImageView saveClicked;
	@FXML private ImageView homeHover;		@FXML private ImageView homeClicked;
	@FXML private ImageView playAgainHover;
	@FXML private ImageView buttonImage;
	@FXML private ImageView gLeaderboardHover;

	// Stage, scene and root of the scene
	private Stage primaryStage;
	private Scene scene;
	private Parent root;

	private int realSeconds = 0; //variable to store the time taken to complete the quiz in seconds
	double quizScore = 0; //number of words spelt correctly
	private static String theme; // theme of the quiz

	/**
	 * Method that sets the current theme of the quiz
	 * @param currentTheme : String containing the current theme
	 */
	public static void setTheme(String currentTheme) {
		theme = currentTheme;
	}

	/**
	The "initialize" method runs everytime this class is called
	 */
	public void initialize() {
		//implement a live listener to detect when the text field is empty
		nameInput.textProperty().addListener((obs, oldValue, newValue) -> {
			if (nameInput.getText().isEmpty()) {
				//if empty, disable the submit button to prevent accidental submits
				submitButton.setDisable(true);
			} else {
				submitButton.setDisable(false);
			}
		});
	}

	/**
	The "displayTime" method shows the time taken to complete the quiz

	@params time : String that contains the time taken
	 */
	public void displayTime(String time) {
		// replacing all unnecessary characters in time with a space
		String temp = time.replaceAll("[^0-9]+", " ");
		// splitting time into an array based on space
		String[] minutesSeconds = temp.trim().split(" ");
		realSeconds = (Integer.parseInt(minutesSeconds[0]))*60 + Integer.parseInt(minutesSeconds[1]);

		//calculate the final score after factoring in words correct and time
		int totalScore = (int)((quizScore / (double) realSeconds) * 1000.00);
		realScoreText.setText("Final Score: " + totalScore);
	}

	/**
	 * Method that displays the words the user mastered, faulted and failed on the reward screen
	 * @param mastered : ArrayList of mastered words
	 * @param faulted : ArrayList of faulted words
	 * @param failed : ArrayList of failed words
	 */
	public void displayWords(ArrayList<String> mastered, ArrayList<String> faulted, ArrayList<String> failed) {
		for (String word : mastered) { // Displaying mastered words
			masteredLabel.setText(masteredLabel.getText() + word + "\n");
		}

		for (String word : faulted) { // Displaying faulted words
			faultedLabel.setText(faultedLabel.getText() + word + "\n");
		}

		for (String word : failed) { // Displaying failed words
			failedLabel.setText(failedLabel.getText() +  word + "\n");
		}
	}

	/**
	 * Method that displays the score and the time taken
	 * @param score : int of user's score
	 * @param timeDisplay : String containing the time taken by the user
	 */
	public void displayScore(double score, String timeDisplay) {
		quizScore = score;
		
		// replacing all unnecessary characters in timeDisplay with a space
		String temp = timeDisplay.replaceAll("[^0-9]+", " ");
		// splitting timeDisplay into an array based on space
		String[] minutesSeconds = temp.trim().split(" ");
 
		// Seeting the time label to display correctly formatted time
		timeText.setText("Time: " + minutesSeconds[0] + "m " + minutesSeconds[1] + "s");
		
		// Setting the score label to diplay correctly formatted score
		if ((score % 1) == 0) {
			scoreText.setText("Score: " + String.format("%.0f", score) + "/5");
		}
		else {
			scoreText.setText("Score: " + String.format("%.1f", score) + "/5");
		}
	}

	/**
	The "onSubmitName" takes the current name in the text field and stores it along with
	the user's score in a text file. The text file is then sorted by score.

	@params: a button click action event
	 */
	public void onSubmitName(ActionEvent event) throws IOException, InterruptedException {
		String name = nameInput.getText();

		//calculate the points of the user (score/time) x 1000
		int totalScore = (int)((quizScore / (double) realSeconds) * 1000.00);

		String finalScore = Integer.toString(totalScore);

		// insert '0' for sorting correctness in the text file
		if (totalScore < 10) {
			finalScore = "00" + Integer.toString(totalScore);
		} else if (totalScore < 100) {
			finalScore = "0" + Integer.toString(totalScore);
		}

		//write into the text file for the specific theme
		String fileDestination = "./resources/Leaderboard/" + theme + ".txt";
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileDestination, true)));
		writer.println(quizScore + "  " + finalScore + "   " + name);
		writer.flush();
		writer.close();

		//disable the submit button
		submitButton.setVisible(false);
		saveHover.setVisible(false);
		saveClicked.setVisible(false);
		buttonImage.setVisible(false);
		nameInput.setVisible(false);
		submittedLabel.setVisible(true);

		BashProcess.sortTxtFile(theme); //sort the file based on their scores
	}

	/**
	 * Method that goes to the select theme scene
	 * @param event : mouse click
	 * @throws IOException
	 */
	public void switchToSelectTheme(ActionEvent event) throws IOException {
		// Setting disabled buttons back to visible for the next time the user goes to rewards		
		submitButton.setVisible(true);
		saveHover.setVisible(true);
		saveClicked.setVisible(true);
		buttonImage.setVisible(true);
		submittedLabel.setVisible(false);

		// Switching to select theme scene
		root = FXMLLoader.load(getClass().getResource("FXML/SelectTheme.fxml"));
		primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Method that goes to the main scene
	 * @param event : mouse click
	 * @throws IOException
	 */
	public void switchToMain(ActionEvent event) throws IOException {
		// Setting disabled buttons back to visible for the next time the user goes to rewards		
		submitButton.setVisible(true);
		saveHover.setVisible(true);
		saveClicked.setVisible(true);
		buttonImage.setVisible(true);
		submittedLabel.setVisible(false);

		// Switching to main menu
		root = FXMLLoader.load(getClass().getResource("FXML/Main.fxml"));
		primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Method that switches to the currently selected leaderboard scene
	 * @param event : mouse click
	 * @throws IOException
	 */
	public void switchToLeaderBoard(ActionEvent event) throws IOException {
		// Setting disabled buttons back to visible for the next time the user goes to rewards
		submitButton.setVisible(true);
		saveHover.setVisible(true);
		saveClicked.setVisible(true);
		buttonImage.setVisible(true);
		submittedLabel.setVisible(false);
		
		// Switching to the leaderboard scene
		root = FXMLLoader.load(getClass().getResource("FXML/Leaderboard.fxml"));
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
		//open the settings tab
		SettingsController.showSettings(event);
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

	// Play again button animations
	public void showPlayAgainHover() {playAgainHover.setVisible(true);}
	public void hidePlayAgainHover() {playAgainHover.setVisible(false);}

	// Go to leaderboard button animations
	public void showGLeaderboardHover() {gLeaderboardHover.setVisible(true);}
	public void hideGLeaderboardHover() {gLeaderboardHover.setVisible(false);}

	// Save button animations
	public void showSaveGlow() {saveHover.setVisible(true);}
	public void hideSaveGlow() {saveHover.setVisible(false);}
	public void showSaveClicked() {saveClicked.setVisible(true);}
}