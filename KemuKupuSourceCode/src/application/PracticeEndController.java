package application;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * This class is a controller for the practice end scene
 *
 * Words that have been spelt in the practice quiz are listed here
 */

public class PracticeEndController {

	@FXML Label masteredLabel; // label to display mastered words
	@FXML Label faultedLabel; // label to display faulted words
	@FXML Label failedLabel; // label to display failed words
	
	// ImageViews for button animations in PracticeEndController
	@FXML private ImageView settingsHover;
	@FXML private ImageView settingsNormal;
	@FXML private ImageView settingsClicked;
	@FXML private ImageView homeHover;
	@FXML private ImageView homeClicked;
	@FXML private ImageView playAgainHover;

	// stage, scene and root of PracticeEndController
	private Stage primaryStage;
	private Scene scene;
	private Parent root;

	/**
	 * Method that displays the words the user mastered, faulted and failed on the reward screen
	 * @param mastered : ArrayList of mastered words
	 * @param faulted : ArrayList of faulted words
	 * @param failed : ArrayList of failed words
	 */
	public void displayWords(ArrayList<String> mastered, ArrayList<String> faulted, ArrayList<String> failed) {
		for (String word : mastered) {
			masteredLabel.setText(masteredLabel.getText() + word + "\n"); //displaying mastered words
		}

		for (String word : faulted) {
			faultedLabel.setText(faultedLabel.getText() + word + "\n"); //displaying faulted words
		}

		for (String word : failed) {
			failedLabel.setText(failedLabel.getText() +  word + "\n"); //displaying failed words
		}
	}

	/**
	 * Method to switch to the select theme module for a practice quiz
	 * @param event : mouse click
	 * @throws IOException
	 */
	public void switchToSelectTheme(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("FXML/SelectTheme.fxml"));
		primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Method to switch to the main scene
	 * @param event : mouse click
	 */
	public void switchToMain(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("FXML/Main.fxml"));
		primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Method to show the settings popup
	 * @param event : mouse click
	 * @throws IOException
	 */
	public void showSettings(MouseEvent event) throws IOException {
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
}