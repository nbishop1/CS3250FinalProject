import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(new StartUpPane(primaryStage));
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.setResizable(false);

		// ESC key handler to show SettingsPane
		scene.setOnKeyPressed(event -> {
			switch (event.getCode()) {
				case ESCAPE:
					javafx.scene.Parent currentRoot = scene.getRoot();
					SettingsPane settingsPane = new SettingsPane(primaryStage, currentRoot);
					scene.setRoot(settingsPane);
					break;
				default:
					break;
			}
		});

		primaryStage.show();
	}

}