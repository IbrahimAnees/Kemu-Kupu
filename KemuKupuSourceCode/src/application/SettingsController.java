package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * This class is a controller for the setting tab
 *
 * The user is able to click a button on any page to bring up
 * this settings tab. On this page there are the options to
 * show/hide macron buttons as well as the time. There is a slider
 * that can be used to adjust the speed of the text to speech
 */

public class SettingsController {

	@FXML
	private static Slider slider;
	private static Stage settingsPopUp = new Stage();
	private static Scene popscene;

	//variable to keep track of the TTS speed when the slider is changed
	private static double sliderSpeed = 1;
	//variable to keep track of the slider speed when the slider is changed
	private static double textToSpeechSpeed = 1;
	private static boolean timerChecked = true;
	private static boolean macronsChecked = true;
	private static String speedIndicator = "NORMAL";

	public static boolean getTimerChecked() {
		//this method returns the boolean to see if the timer checkbox is ticked or not
		return timerChecked;
	}

	public static double getTextToSpeechSpeed() {
		//returns the speed for TTS
		return textToSpeechSpeed;
	}

	/*
    The "showSettings" method initialises the settings tab and displays the pop up stage
    @params: called on a mouse click on a button
	 */
	public static void showSettings(MouseEvent event) throws IOException {
		settingsPopUp.setTitle("Settings");
		settingsPopUp.setResizable(false);
		Button okBtn = new Button("CLOSE");
		okBtn.setFont(new Font(20));

		okBtn.setStyle("-fx-background-color: transparent;" +
				"-fx-text-fill: #828282;" +
				"-fx-underline: false;"
				);

		DropShadow shadow = new DropShadow();

		//add effects for when the mouse is hovered
		okBtn.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				okBtn.setEffect(shadow);
				okBtn.setStyle("-fx-background-color: transparent;" +
						"-fx-text-fill: #828282;" +
						"-fx-cursor: hand;"
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

		okBtn.setOnAction( actionEvent -> {
			settingsPopUp.close();

		});

		VBox layout = new VBox(10);
		addSettingsLabel(layout);
		addTimerCheckbox(layout);
		addMacronCheckbox(layout);
		addSlider(layout);
		layout.getChildren().addAll(okBtn);
		layout.setAlignment(Pos.CENTER);

		//set background colour to black-blue gradient
		layout.setStyle("-fx-background-color: linear-gradient(to right,#0F2027, #203A43, #2C5364);");
		popscene = new Scene(layout, 450, 300);
		popscene.setFill(Color.web("#00BFFF"));

		//add styling sheet, sheet contains styling for checkbox and slider
		layout.getStylesheets().add(SettingsController.class.getResource("CSS/styling.css").toExternalForm());

		settingsPopUp.setScene(popscene);
		settingsPopUp.show();
	}

	/*
    The "addSettingsLabel" method add the setting label to scene
    @params: takes in the VBox that is used for the layout on the settings tab
	 */
	public static void addSettingsLabel(VBox layer) {
		//styling for the 'settings' header
		Label settingsLabel = new Label("SETTINGS");
		settingsLabel.setFont(new Font("Cousine Bold", 36));
		settingsLabel.setTextFill(Color.web("#FFFFFF"));
		settingsLabel.setStyle("-fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )");

		layer.getChildren().addAll(settingsLabel);
	}

	/*
    The "addTimerCheckbox" method add the checkbox which the user and tick/untick to show/hide
    the timer during the new quiz
    @params: takes in the VBox that is used for the layout on the settings tab
	 */
	public static void addTimerCheckbox(VBox layer) {
		CheckBox checkbox = new CheckBox("SHOW TIMER");
		checkbox.setFont(new Font("Cousine", 14));
		checkbox.setTextFill(Color.web("#FFFFFF"));
		checkbox.setStyle("-fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )");
		checkbox.setStyle("-fx-background-color: transparent");

		//initialise the timer status each time the settings tab is opened
		if (timerChecked) {
			checkbox.setSelected(true);
		} else {
			checkbox.setSelected(false);
		}

		//listener to show the label whenever the checkbox is changed
		checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (checkbox.isSelected()) {
					timerChecked = true;
				} else {
					timerChecked = false;
				}
			}
		});

		layer.getChildren().add(checkbox);
	}

	/*
    The "addMacronCheckbox" method add the checkbox which the user and tick/untick
    to show/hide the macron buttons during the new quiz and practice
    @params: takes in the VBox that is used for the layout on the settings tab
	 */
	public static void addMacronCheckbox(VBox layer) {
		CheckBox checkbox = new CheckBox("SHOW MACRON BUTTONS");
		checkbox.setFont(new Font("Cousine", 14));
		checkbox.setTextFill(Color.web("#FFFFFF"));
		checkbox.setStyle("-fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )");
		checkbox.setStyle("-fx-background-color: transparent");

		if (macronsChecked) {
			checkbox.setSelected(true);
		} else {
			checkbox.setSelected(false);
		}

		//listener to show the label whenever the checkbox is changed
		checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (checkbox.isSelected()) {
					//System.out.println("ticked");
					macronsChecked = true;
					NewQuizController.setShowMacrons(true);
					PracticeModuleController.setShowMacron(true);
				} else {
					//System.out.println("not ticked");
					macronsChecked = false;
					NewQuizController.setShowMacrons(false);
					PracticeModuleController.setShowMacron(false);
				}
			}
		});

		layer.getChildren().add(checkbox);
	}

	/*
    The "addSlider" method add the slider which the user can use to adjust
    the speed at which Maori word are spoken
    @params: takes in the VBox that is used for the layout on the settings tab
	 */
	public static void addSlider(VBox layer) {
		Label speedLabel = new Label("SPEAKER SPEED: " + speedIndicator);
		speedLabel.setTextFill(Color.web("#FFFFFF"));

		// ACKNOWLEDGEMENT | Code adapted from: https://stackoverflow.com/questions/45226652/how-do-i-change-the-color-of-a-slider-track-track-in-javafx-using-css 
		slider = new Slider(0.5,1.5, sliderSpeed);
		slider.setMaxWidth(200);
		speedLabel.setFont(new Font("Cousine", 14));
		speedLabel.setTextFill(Color.web("#FFFFFF"));
		speedLabel.setStyle("-fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )");

		//listener to change the tts speed when the slider is changed
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				speedLabel.setText(Integer.toString((int)slider.getValue())); //change the label
				sliderSpeed =slider.getValue();
				textToSpeechSpeed = 2 - slider.getValue(); //convert the slider value to tts duration speed

				//changing the speed label
				if (sliderSpeed > 1.4) {
					speedIndicator = "SUPER FAST";
					speedLabel.setText("SPEAKER SPEED: " + speedIndicator);
				} else if (sliderSpeed > 1.1){
					speedIndicator = "FAST";
					speedLabel.setText("SPEAKER SPEED: " + speedIndicator);
				} else if (sliderSpeed > 0.9) {
					speedIndicator = "NORMAL SPEED";
					speedLabel.setText("SPEAKER SPEED: " + speedIndicator);
				} else if (sliderSpeed > 0.7) {
					speedIndicator = "SLOW";
					speedLabel.setText("SPEAKER SPEED: " + speedIndicator);
				} else {
					speedIndicator = "SLOWER";
					speedLabel.setText("SPEAKER SPEED: " + speedIndicator);
				}
			}
		});

		layer.getChildren().addAll(slider, speedLabel);
	}

}