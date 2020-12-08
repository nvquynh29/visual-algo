package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.view.AnimationController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        AnimationController animationController = new AnimationController();
        animationController.setStyle("-fx-background-color: #EEEEEE");

        Scene scene = new Scene(animationController,
                AnimationController.WINDOW_WIDTH,
                AnimationController.WINDOW_HEIGHT);

        primaryStage.setTitle("Visual Sorting Algorithms");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
