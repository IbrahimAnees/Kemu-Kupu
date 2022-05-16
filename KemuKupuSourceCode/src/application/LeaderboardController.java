package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * This class is the controller for the leader board scene
 *
 * This class contains the scores of all themes that have been
 * submitted by the user. There are 5 placings on the leadership,
 * and a drop down menu is used to access different themes.
 */

public class LeaderboardController implements Initializable {

	@FXML private ComboBox<String> dropDown; // drop down used to select theme
	// All labels used in LeaderboardController
	@FXML Label firstName;
	@FXML Label secondName;
	@FXML Label thirdName;
	@FXML Label fourthName;
	@FXML Label fifthName;
	@FXML Label firstScore;
	@FXML Label secondScore;
	@FXML Label thirdScore;
	@FXML Label fourthScore;
	@FXML Label fifthScore;
	@FXML Label firstPoints;
	@FXML Label secondPoints;
	@FXML Label thirdPoints;
	@FXML Label fourthPoints;
	@FXML Label fifthPoints;
	@FXML private Label themeLabel;
	// All ImageViews in LeaderboardController used for button animations
	@FXML private ImageView settingsHover;
	@FXML private ImageView settingsNormal;
	@FXML private ImageView settingsClicked;
	@FXML private ImageView homeHover;
	@FXML private ImageView homeClicked;
	@FXML private ImageView clearHover;
	@FXML private ImageView clearClicked;

	// stage, scene and root for LeaderboardController
	private Stage primaryStage;
	private Scene scene;
	private Parent root;

	private static HashMap<Integer, ArrayList<Label>> labels; //map to store all the labels used to display the score
	private static String theme = null; //label to store the most recently selected theme

	/**
	 * Method that sets the current theme
	 * @param currentTheme
	 */
	public static void setTheme(String currentTheme) {
		theme = currentTheme;
	}

	/*
	The "initialize" method runs at the beginner when this scene is switched to
	This method initializes the labels and drop down box
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		displayThemeLabel();

		String[] themes = {"Babies", "Colours", "Compass Points", "Days Of The Week 1", "Days Of The Week 2", 
				"Engineering", "Feelings", "Months Of The Year 1", "Months Of The Year 2", "Software", "Uni Life", 
				"Weather", "Work"};
		dropDown.getItems().addAll(themes);

		HashMap<String, Integer> dropDownValues = new HashMap<>();
		initialiseValues(dropDownValues);

		//add all the lables into a hashmap so they can be edited easily
		labels = new HashMap<Integer, ArrayList<Label>>();
		labels.put(1, new ArrayList<>(Arrays.asList(firstScore, firstPoints, firstName)));
		labels.put(2, new ArrayList<>(Arrays.asList(secondScore, secondPoints, secondName)));
		labels.put(3, new ArrayList<>(Arrays.asList(thirdScore, thirdPoints, thirdName)));
		labels.put(4, new ArrayList<>(Arrays.asList(fourthScore, fourthPoints, fourthName)));
		labels.put(5, new ArrayList<>(Arrays.asList(fifthScore, fifthPoints, fifthName)));

		//if the user is coming from a completed quiz
		if (theme != null) {
			//show the leaderboard for the theme they have just completed
			dropDown.getSelectionModel().select(dropDownValues.get(theme));
			String currentTheme = theme.replace(" ", "");
			printStats(currentTheme);
		}

		//add a listener to change the score board everytime the drop down is changed
		// ACKNOWLEDGEMENT | Code adapted from: https://stackoverflow.com/questions/30572918/how-to-get-the-selected-item-from-a-combobox-in-javafx/30576691 
		dropDown.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			theme = dropDown.getValue();
			displayThemeLabel();
			String currentTheme = theme.replace(" ", "");
			printStats(currentTheme);
		});
	}

	/*
	The "displayThemeLabel" method is used to display the current selected theme
	 */
	public void displayThemeLabel() {
		if (theme == null) {
			themeLabel.setText("");
		}
		else {

			switch (theme) {
			case "CompassPoints": { themeLabel.setText("Compass Points"); break;}
			case "DaysOfTheWeek1": { themeLabel.setText("Days of the Week 1"); break;}
			case "DaysOfTheWeek2": { themeLabel.setText("Days of the Week 2"); break;}
			case "MonthsOfTheYear1": { themeLabel.setText("Months of the Year 1"); break;}
			case "MonthsOfTheYear2": { themeLabel.setText("Months of the Year 2"); break;}
			case "UniLife": { themeLabel.setText("Uni Life"); break;}
			default: { themeLabel.setText(theme); break; }
			}
		}
	}

	/*
	 * This method is used to print the leader board onto the labels
	 * @params: the current theme that is selected
	 */
	public static void printStats(String theme) {
		clearLabels();

		try {
			//read in the file which the leaderboard is stored
			BufferedReader reader = new BufferedReader(new FileReader("./resources/Leaderboard/" + theme + ".txt"));
			String line;
			int counter = 1;
			ArrayList<Label> list;

			//read the text file that contains the stats
			while ((line = reader.readLine()) != null) {
				list = labels.get(counter);

				//get the name/scores/points
				String[] stats = line.split("  ");

				for (int i = 0; i < labels.get(1).size(); i++) {
					//set the text for each of the 3 labels
					list.get(i).setText(stats[i]);
				}
				counter++;
				if (counter == 6) {
					//only read out the top 5 results
					break;
				}
			}         reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
    The "clearLabels" methods is used to clear all the labels on the page
    This could be when the stats are cleared or there is no stats to display
	 */
	public static void clearLabels() {
		//clear all the labels before setting them to the next theme
		for (int i = 1; i <= labels.size(); i++) {
			ArrayList<Label> labelsList = labels.get(i);
			for (int j = 0; j < labelsList.size(); j++) {
				labelsList.get(j).setText("");
			}
		}
	}

	/*
    The "clearScoreboard" methods is used to clear all the scorebaord.
    This method calls a bash command that empties the text file that stores the scores

    @param: a button click action event
	 */
	public void clearScoreboard(ActionEvent event) throws IOException {
		//this method creates a pop up window to which the user can confirm is they want to
		//clear the leaderboard for the current theme
		Stage popup = new Stage();
		popup.initModality(Modality.APPLICATION_MODAL);
		popup.setMinWidth(250);
		popup.setTitle("Clear Leaderboard");
		Button okBtn = new Button("YES");
		Label label = new Label("ARE YOU SURE YOU WANT TO CLEAR THIS LEADERBOARD?");
		label.setTextFill(Color.web("#FFFFFF"));
		label.setFont(new Font("Cousine Bold", 16));

		okBtn.setStyle("-fx-background-color: transparent;" +
				"-fx-text-fill: #828282;" +
				"-fx-underline: false;"
				+ "-fx-font-size: 16"
				);

		DropShadow shadow = new DropShadow();
		okBtn.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				okBtn.setEffect(shadow);
				okBtn.setStyle("-fx-background-color: transparent;" +
						"-fx-text-fill: #828282;" +
						"-fx-cursor: hand;"
						+ "-fx-font-size: 16"
						);
			}
		});

		okBtn.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				okBtn.setEffect(null);
			}
		});

		//on button click
		okBtn.setOnAction( actionEvent -> {
			try {

				BashProcess.clearFile(theme);
			} catch (IOException e) {
				e.printStackTrace();
			}
			clearLabels();
			popup.close();

		});

		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, okBtn);
		layout.setAlignment(Pos.CENTER);
		layout.setStyle("-fx-background-color: linear-gradient(to right,#0F2027, #203A43, #2C5364);");
		Scene popscene = new Scene(layout, 500, 200);
		popup.setScene(popscene);
		popup.show();
	}

	/**
   	The "initialiseValues" method sets up an associated value with each drop down selection

   	@params: takes in an empty map
	 */
	public void initialiseValues(HashMap<String, Integer> map) {
		map.put("Babies", 0);
		map.put("Colours", 1);
		map.put("Compass Points", 2);
		map.put("Days Of The Week 1", 3);
		map.put("Days Of The Week 2", 4);
		map.put("Engineering", 5);
		map.put("Feelings", 6);
		map.put("Months Of The Year 1", 7);
		map.put("Months Of The Year 2", 8);
		map.put("Software", 9);
		map.put("Uni Life", 10);
		map.put("Weather", 11);
		map.put("Work", 12);
	}

	/**
	 * Method that switches to the main scene
	 * @param event
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

	/**
	 * Method that shows the settings popup
	 * @param event
	 * @throws IOException
	 */
	public void showSettings(MouseEvent event) throws IOException {
		//show the settings tab

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

	// Clear leaderboard button animations
	public void showClearGlow() {clearHover.setVisible(true);}
	public void hideClearGlow() {clearHover.setVisible(false);}
	public void showClearClicked() {clearClicked.setVisible(true);}
	public void hideClearClicked() {clearClicked.setVisible(false);}
}
