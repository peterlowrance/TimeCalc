import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.*;

public class TimeCalcMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static String printTime(double totalHours, String currentTime) {
        // Variables
        double timeTaken = 0;
        int hours;
        int hoursLeft;
        int minutes;
        int minutesLeft;
        String result = "";
        timeTaken = totalHours;
        int TOTAL_HOURS;
        if(timeTaken > 40){
            TOTAL_HOURS = 80;
        }
        else{
            TOTAL_HOURS = 40;
        }
        // Calculate hours and minutes
        hours = (int) Math.floor(timeTaken);
        minutes = (int) Math.round((timeTaken - hours) * 60);
        hoursLeft = TOTAL_HOURS - hours - (minutes > 0 ? 1 : 0);
        minutesLeft = (minutes > 0 ? 60 : 0) - minutes;
        // Print hours counted and hours remaining
        result = "You have " + hours + " hours and " + minutes + " minutes.\n "
                + hoursLeft + " hours and " + minutesLeft
                + " minutes until you reach " + TOTAL_HOURS + " hours.";
        System.out.println(result);
        result = result + "\n" + printEndTime(hoursLeft, minutesLeft, currentTime);
        return result;
    }

    public static String printEndTime(int hours, int minutes, String currentTime) {
        Scanner in = new Scanner(currentTime);
        in.useDelimiter(":");
        int currentHours = Integer.parseInt(in.next());
        int currentMinutes = Integer.parseInt(in.next());
        in.close();
        String result = "\nAccording to your last punch, you should clock out\nat "
                + ((currentHours + (hours%8) + (minutes + currentMinutes >= 60 ? 1
                : 0)) % 12) + ":" + String.format("%02d", (minutes + currentMinutes) % 60)
                + " to be on schedule for 8 hour days.";
        System.out.println(result);
        return result;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Time Calculator");
        // Create current time field
        final TextField dateTextField = new TextField();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat h = new SimpleDateFormat("HH");
        SimpleDateFormat m = new SimpleDateFormat("mm");
        String currentHours = h.format(cal.getTime());
        String currentMinutes = m.format(cal.getTime());
        dateTextField.setText(currentHours + ":" + currentMinutes);
        // Label for time field
        Label timeLabel = new Label("Time of last punch: ");
        HBox timeBox = new HBox();
        timeBox.setSpacing(10);
        timeBox.setAlignment(Pos.CENTER_LEFT);
        timeBox.getChildren().addAll(timeLabel, dateTextField);
        // Create text fields
        final TextField inputTextField = new TextField();
        final Text outputText = new Text();
        outputText.setText("\n\n\n\n\n");
        // Add button and text
        Label hoursLabel = new Label("Total Hours: ");
        HBox hoursBox = new HBox();
        hoursBox.setAlignment(Pos.CENTER_LEFT);
        hoursBox.setSpacing(10);
        hoursBox.getChildren().addAll(hoursLabel, inputTextField);
        // Create button
        final Button btn = new Button();
        btn.setText("Calculate: ");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    outputText.setText(printTime(Double
                            .parseDouble(inputTextField.getText()), dateTextField.getText()));
                } catch (Exception e) {
                    outputText.setText("Invalid input.");
                }
            }
        });

        // Grid
        GridPane grid = new GridPane();
        GridPane.setConstraints(timeBox, 0, 0);
        GridPane.setConstraints(hoursBox, 0, 1);
        GridPane.setConstraints(btn, 0, 2);
        GridPane.setConstraints(outputText, 0, 3);
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);
        grid.getChildren().addAll(timeBox, hoursBox, btn, outputText);

        // Style
        btn.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");

        // Create group and start scene
        Group root = new Group(grid);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
