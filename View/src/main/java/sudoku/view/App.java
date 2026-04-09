package sudoku.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ResourceBundle;

public class App extends Application {
    private static Stage primaryStage;
    private static Logger logger = LoggerFactory.getLogger(App.class);

    @Override
    public void start(Stage stage) throws IOException {
        logger.info("Start Sudoku GUI");

        primaryStage = stage;

        ResourceBundle resourceBundle = LanguageEnum.getResourceBundle();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sudoku/view/MainMenu.fxml"), resourceBundle);
        Image icon = new Image(getClass().getResourceAsStream("/icons/favicon.png"));

        MainMenuController mainMenuController = new MainMenuController();
        loader.setController(mainMenuController);

        Parent root = loader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Sudoku Game - Minh Do");
        primaryStage.show();
        primaryStage.getIcons().add(icon);
    }

    static void setScene(Parent newRoot) {
        primaryStage.setScene(new Scene(newRoot));
    }

    public static void main(String[] args) {
        launch();
    }
}