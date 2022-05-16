package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * This is the main method of the Kēmu Kupu applicaton
 *
 * This class is run in order to start the application
 */

public class Main extends Application {
	/**
	 * The start method that creates the main stage for the main scene
	 */
	@Override
	public void start(Stage primaryStage) throws IOException, InterruptedException, IllegalArgumentException {
		// Creating a root, scene and stage
		try {
			Parent root = FXMLLoader.load(getClass().getResource("FXML/Main.fxml")); // loading the main scene
			Scene scene = new Scene(root);
			String css = this.getClass().getResource("CSS/application.css").toExternalForm();
			scene.getStylesheets().add(css); // adding the appropriate css styling to the main scene

			primaryStage.setTitle("Kēmu Kupu"); // setting the title of the stage
			primaryStage.setResizable(false); // not allowing the stage to be resized

			primaryStage.setScene(scene);
			primaryStage.show(); // displaying the stage

			//speak out a welcome message in both Maori and English
			new File("./resources/tempFiles").mkdirs();
			BashProcess.sayMaoriEnglishSpeech("Welcome","Nau mai");

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that deletes all temp files
	 * @param file : file path to be deleted
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
	 * Overriding the stop method to delete temp files when the program is closed
	 */
	@Override
	public void stop() throws IOException {
		Path path = Paths.get("./resources/tempFiles");
		if (Files.exists(path)) {
			File file = new File("./resources/tempFiles");
			deleteDirectory(file);
			file.delete();
		}
	}


	/**
	 * The main method where the program starts from: launches the main scene
	 * @param args : VM arguments
	 * @throws IllegalArgumentException
	 */
	public static void main(String[] args) throws IllegalArgumentException {
		launch(args);
	}
}