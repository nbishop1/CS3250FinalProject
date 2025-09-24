import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(new StartUpPane(), 800, 600);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

}
