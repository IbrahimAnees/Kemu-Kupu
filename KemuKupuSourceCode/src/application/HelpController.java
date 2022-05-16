package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The HelpController class is the controller class for the Help.fxml scene.
 * The class implements all functionality required for showing help of different
 * aspects of Kemu Kupu such as scoring, speech speed, game modules and macrons.
 */

public class HelpController {
	@FXML private AnchorPane mainAnchorPane; // anchor pane for HelpController
	
	// All ImageViews required for button animations in HelpController
	@FXML private ImageView settingsHover;
	@FXML private ImageView settingsNormal;
	@FXML private ImageView settingsClicked;
	@FXML private ImageView homeHover;
	@FXML private ImageView homeClicked;
	@FXML private ImageView scoringHover; 		@FXML private ImageView scoringClicked;
	@FXML private ImageView speechSpeedHover; 	@FXML private ImageView speechSpeedClicked;
	@FXML private ImageView gameModulesHover; 	@FXML private ImageView gameModulesClicked;
	@FXML private ImageView macronsHover; 		@FXML private ImageView macronsClicked;

	// All texts used in HelpController
	@FXML private Text scoring1, scoring2, scoring3, scoring4, scoring5;
	@FXML private Text gm1, gm2, gm3, gm4;
	@FXML private Text macrons1, macrons2, macrons3, macrons4;
	@FXML private Text ss1, ss2;

	// All buttons used in HelpController
	@FXML private Button scoringButton;
	@FXML private Button speedSpeechButton;
	@FXML private Button gameModulesButton;
	@FXML private Button macronsButton;

	// Stage, scene and root for HelpController
	private Stage primaryStage;
	private Scene scene;
	private Parent root;

	/**
	 * Method that shows the help text for scoring
	 * @param event : mouse click
	 */
	public void showScoring(ActionEvent event) {
		// Hiding and showing relevant text for this particular case
		scoring1.setVisible(true); scoring2.setVisible(true); scoring3.setVisible(true); scoring4.setVisible(true); scoring5.setVisible(true);
		gm1.setVisible(false); gm2.setVisible(false); gm3.setVisible(false); gm4.setVisible(false);
		macrons1.setVisible(false); macrons2.setVisible(false); macrons3.setVisible(false); macrons4.setVisible(false);
		ss1.setVisible(false); ss2.setVisible(false);

		// Button animations and disable current button
		hideSpeedSpeechClicked();
		showScoringClicked();
		hideGameModulesClicked();
		hideMacronsClicked();
		scoringButton.setDisable(true);
		speedSpeechButton.setDisable(false);
		gameModulesButton.setDisable(false);
		macronsButton.setDisable(false);
	}

	/**
	 * Method that shows the help text for speed speech
	 * @param event : mouse click
	 */
	public void showSpeedSpeech(ActionEvent event) {
		// Hiding and showing relevant text for this particular case
		scoring1.setVisible(false); scoring2.setVisible(false); scoring3.setVisible(false); scoring4.setVisible(false); scoring5.setVisible(false);
		gm1.setVisible(false); gm2.setVisible(false); gm3.setVisible(false); gm4.setVisible(false);
		macrons1.setVisible(false); macrons2.setVisible(false); macrons3.setVisible(false); macrons4.setVisible(false);
		ss1.setVisible(true); ss2.setVisible(true);

		// Button animations and disable current button
		showSpeedSpeechClicked();
		hideScoringClicked();
		hideGameModulesClicked();
		hideMacronsClicked();
		scoringButton.setDisable(false);
		speedSpeechButton.setDisable(true);
		gameModulesButton.setDisable(false);
		macronsButton.setDisable(false);
	}

	/**
	 * Method that shows the help text for game modules
	 * @param event : mouse click
	 */
	public void showGameModules(ActionEvent event) {
		// Hiding and showing relevant text for this particular case
		scoring1.setVisible(false); scoring2.setVisible(false); scoring3.setVisible(false); scoring4.setVisible(false); scoring5.setVisible(false);
		gm1.setVisible(true); gm2.setVisible(true); gm3.setVisible(true); gm4.setVisible(true);
		macrons1.setVisible(false); macrons2.setVisible(false); macrons3.setVisible(false); macrons4.setVisible(false);
		ss1.setVisible(false); ss2.setVisible(false);

		// Button animations and disable current button
		hideSpeedSpeechClicked();
		hideScoringClicked();
		showGameModulesClicked();
		hideMacronsClicked();
		scoringButton.setDisable(false);
		speedSpeechButton.setDisable(false);
		gameModulesButton.setDisable(true);
		macronsButton.setDisable(false);
	}

	/**
	 * Method that shows the help text for macrons
	 * @param event : mouse click
	 */
	public void showMacrons(ActionEvent event) {
		// Hiding and showing relevant text for this particular case
		scoring1.setVisible(false); scoring2.setVisible(false); scoring3.setVisible(false); scoring4.setVisible(false); scoring5.setVisible(false);
		gm1.setVisible(false); gm2.setVisible(false); gm3.setVisible(false); gm4.setVisible(false);
		macrons1.setVisible(true); macrons2.setVisible(true); macrons3.setVisible(true); macrons4.setVisible(true);
		ss1.setVisible(false); ss2.setVisible(false);

		// Button animations and disable current button
		hideSpeedSpeechClicked();
		hideScoringClicked();
		hideGameModulesClicked();
		showMacronsClicked();
		scoringButton.setDisable(false);
		speedSpeechButton.setDisable(false);
		gameModulesButton.setDisable(false);
		macronsButton.setDisable(true);
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
	 * Method to switch to the main menu
	 * @param event : mouse click
	 * @throws IOException
	 */
	public void switchToMain(ActionEvent event) throws IOException {
		//return to the main menu
		root = FXMLLoader.load(getClass().getResource("FXML/Main.fxml"));
		primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
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

	// Scoring button animations
	public void showScoringGlow() {scoringHover.setVisible(true);}
	public void hideScoringGlow() {scoringHover.setVisible(false);}
	public void showScoringClicked() { scoringClicked.setVisible(true); }
	public void hideScoringClicked() { scoringClicked.setVisible(false); }
	
	// Macrons button animations
	public void showMacronsGlow() {macronsHover.setVisible(true);}
	public void hideMacronsGlow() {macronsHover.setVisible(false);}
	public void showMacronsClicked() { macronsClicked.setVisible(true); }
	public void hideMacronsClicked() { macronsClicked.setVisible(false); }

	// Speed speech button animations
	public void showSpeechSpeedGlow() {speechSpeedHover.setVisible(true);}
	public void hideSpeedSpeechGlow() {speechSpeedHover.setVisible(false);}
	public void showSpeedSpeechClicked() { speechSpeedClicked.setVisible(true); }
	public void hideSpeedSpeechClicked() { speechSpeedClicked.setVisible(false); }

	// Game modules button animations
	public void showGameModulesGlow() {gameModulesHover.setVisible(true);}
	public void hideGameModulesGlow() {gameModulesHover.setVisible(false);}
	public void showGameModulesClicked() { gameModulesClicked.setVisible(true); }
	public void hideGameModulesClicked() { gameModulesClicked.setVisible(false); }
}
