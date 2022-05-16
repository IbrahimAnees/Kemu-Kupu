package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The MainController class is the controller class for the Main.fxml scene.
 * The class implements all functionality required the main page, including
 * the buttons to go to a new quiz, practice quiz, help section, and quit.
 */

public class MainController {

	@FXML private AnchorPane mainAnchorPane; // anchor pane for MainController
	
	// ImageViews used for button animations
	@FXML private ImageView settingsNormal;	@FXML private ImageView settingsHover;	@FXML private ImageView settingsClicked;
	@FXML private ImageView newQuizHover;
	@FXML private ImageView practiceQuizHover;
	@FXML private ImageView quitHover;
	@FXML private ImageView leaderboardHover;
	@FXML private ImageView helpHover;

	// stage, scene and root for MainController
	private Stage primaryStage;
	private Scene scene;
	private Parent root;

	/**
	 * Method that switches to the select theme scene for a new quiz
	 * @param event : mouse click
	 * @throws IOException
	 */
	public void switchToSelectTheme(MouseEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("FXML/SelectTheme.fxml"));
		primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		SelectThemeController.setModule("quiz");
	}

	/**
	 * Method that switches to the help scene
	 * @param event : mouse click
	 * @throws IOException
	 */
	public void switchToHelp(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("FXML/Help.fxml"));
		primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Method that switches to the select theme scene for the practice module
	 * @param event : mouse click
	 * @throws IOException
	 */
	public void switchToSelectThemeP(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("FXML/SelectTheme.fxml"));
		primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		SelectThemeController.setModule("practice");
	}

	/**
	 * Method that switches to the leaderboard scene
	 * @param event : mouse click
	 * @throws IOException
	 */
	public void switchToLeaderBoard(ActionEvent event) throws IOException {
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
		SettingsController.showSettings(event);
	}
	
	/**
	 * Method that closes the application
	 * @param event : mouse click
	 */
	public void quit(MouseEvent event) {
		primaryStage = (Stage) mainAnchorPane.getScene().getWindow();
		primaryStage.close();
	}





	// ---------------- BUTTON ANIMATION METHODS ----------------
	// The following methods are used for displaying different
	// images when buttons are hovered over or clicked
	
	// Settings button animations
	public void showSettingsGlow() {settingsHover.setVisible(true);}
	public void hideSettingsGlow() {settingsHover.setVisible(false);}
	public void showSettingsClicked() {settingsClicked.setVisible(true);}
	public void hideSettingsClicked() {settingsClicked.setVisible(false);}

	// New quiz button animations
	public void showNewQuizGlow() {newQuizHover.setVisible(true);}
	public void hideNewQuizGlow() {newQuizHover.setVisible(false);}

	// Practice quiz button animations
	public void showPracticeGlow() {practiceQuizHover.setVisible(true);}
	public void hidePracticeGlow() {practiceQuizHover.setVisible(false);}

	// Quit button animations
	public void showQuitGlow() {quitHover.setVisible(true);}
	public void hideQuitGlow() {quitHover.setVisible(false);}

	// Leaderboard button animations
	public void showLeaderboardGlow() {leaderboardHover.setVisible(true);}
	public void hideLeaderboardGlow() {leaderboardHover.setVisible(false);}

	// Help button animations
	public void showHelpGlow() {helpHover.setVisible(true);}
	public void hideHelpGlow() {helpHover.setVisible(false);}
}