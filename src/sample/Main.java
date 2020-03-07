package sample;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Main extends Application {


    TableView tableData = new TableView<Worker<Void>>();
    Button runTaskButton = new Button("Nowy wątek typu Task");
    private final ObservableList<ThreadTask> listThreads = FXCollections.observableArrayList();

    private void bindData() {
        tableData.setItems(listThreads);
    }

    private void configTable() {
        tableData.setEditable(false);
        TableColumn nameCol = new TableColumn("Nazwa wątku");
        nameCol.setCellValueFactory(
                new PropertyValueFactory<ThreadTask, Integer>("name") //1
        );
        nameCol.setPrefWidth(150);

        TableColumn progressCol = new TableColumn("Postęp");
        progressCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ThreadTask, ProgressBar>, ObservableValue<ProgressBar>>() {
            @Override
            public ObservableValue<ProgressBar> call(TableColumn.CellDataFeatures<ThreadTask, ProgressBar> arg0) {
                ThreadTask task = arg0.getValue();
                ProgressBar bar = new ProgressBar();
                bar.setPrefWidth(630);
                bar.progressProperty().bind(task.progressProperty()); //2
                return new SimpleObjectProperty<ProgressBar>(bar);
            }
        });
        progressCol.setPrefWidth(630);
        tableData.getColumns().addAll(nameCol, progressCol);
    }

    private void attachActions() {
        runTaskButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                ThreadTask task = new ThreadTask(); //1
                new Thread(task).start(); //2
                listThreads.add(task);

            }
        });

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane rootGroup = new BorderPane();
        FlowPane paneFlow = new FlowPane();
        paneFlow.getChildren().addAll(runTaskButton);
        paneFlow.setPadding(new Insets(10, 10, 10, 10));
        paneFlow.setHgap(10);
        paneFlow.setVgap(10);
        paneFlow.setAlignment(Pos.CENTER);

        rootGroup.setCenter(tableData);
        rootGroup.setBottom(paneFlow);

        Scene scene = new Scene(rootGroup, 800, 600);
        scene.setFill(Color.OLDLACE);

        primaryStage.setTitle("Java wątki");
        primaryStage.setScene(scene);

        configTable();
        attachActions();
        bindData();

        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
