package sample.view;

import javafx.animation.SequentialTransition;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.node.MyNode;
import sample.sort.BubbleSort;
import sample.sort.MergeSort;
import sample.sort.Sort;
import sample.util.ControlNodes;

import java.util.Arrays;
import java.util.List;

public class AnimationController extends BorderPane {
    public static final int WINDOW_WIDTH = 1200;
    public static final int WINDOW_HEIGHT = 600;
    public static final int XGAP = 10;
    public static final int BOTTOM_BOUNDARY = 100;
    public static final int NO_OF_NODES = 20;
    public static final int BASE_LINE = 500;
    public static final int TOP_BOUNDARY = 50;
    public static int DX;

    private static Sort sort;

    public static Pane display;
    private Label lbInput;
    private TextField txtInput;
    private Button btnRandom;
    private Button btnCustom;
    private Button btnPlay;
    private Button btnPause;
    private Button btnReplay;
    private ButtonBar createBar;  // Random + Custom
    private ButtonBar animationBar;  // Play + Pause + Replay
    private ComboBox<String> boxSpeed;
    private HBox controlHBox;
    private MenuBar menuBar;


    private MyNode[] myNodes;
    private SequentialTransition sq;

    public AnimationController() {
        this.display = new Pane();
        initControlBar();
        initMenuBar();

        this.setCenter(display);
        this.setBottom(controlHBox);
        this.setPadding(new Insets(0, 0, 20, 0));
        controlHBox.setAlignment(Pos.CENTER);

        this.myNodes = ControlNodes.randomNodes(NO_OF_NODES);
        DX = WINDOW_WIDTH / NO_OF_NODES;
        setAllEvent();

        addNodes();
    }

    public void addNodes() {
        display.getChildren().addAll(myNodes);
        display.getChildren().addAll(ControlNodes.getTexts(myNodes));
    }

    public void initControlBar() {
        lbInput = new Label("Input size: (Max = 60)");
        txtInput = new TextField();
        txtInput.setPrefSize(50, 18);
        txtInput.setFont(new Font("Times New Roman", 14));

        btnRandom = new Button("Random");
        btnCustom = new Button("Custom");
        btnPlay = new Button("Play");
        btnPause = new Button("Pause");
        btnReplay = new Button("Replay");

        createBar = new ButtonBar();
        createBar.getButtons().addAll(btnRandom, btnCustom);
        animationBar = new ButtonBar();
        animationBar.getButtons().addAll(btnPlay, btnPause, btnReplay);

        List<String> speeds = Arrays.asList("0.25x", "0.5x", "1.0x", "1.5x", "2.0x");
        boxSpeed = new ComboBox<>(FXCollections.observableList(speeds));
        boxSpeed.getSelectionModel().select(2);
        boxSpeed.setVisible(false);

        controlHBox = new HBox(lbInput, txtInput, createBar, animationBar, boxSpeed);
        controlHBox.setSpacing(15);

        createBar.setDisable(true);
        btnPause.setDisable(true);
        btnReplay.setDisable(true);
    }

    public void initMenuBar() {
        Menu compare = new Menu("Compare");
//        compare.setDisable(true);
        Menu sortMenu = new Menu("Sort");

        RadioMenuItem bubbleSort = new RadioMenuItem("Bubble Sort");
        RadioMenuItem heapSort = new RadioMenuItem("Heap Sort");
        RadioMenuItem insertionSort = new RadioMenuItem("Insertion Sort");
        RadioMenuItem mergeSort = new RadioMenuItem("Merge Sort");
        RadioMenuItem quickSort = new RadioMenuItem("Quick Sort");
        RadioMenuItem selectionSort = new RadioMenuItem("Selection Sort");

        ToggleGroup tg = new ToggleGroup();

        bubbleSort.setToggleGroup(tg);
        heapSort.setToggleGroup(tg);
        insertionSort.setToggleGroup(tg);
        mergeSort.setToggleGroup(tg);
        quickSort.setToggleGroup(tg);
        selectionSort.setToggleGroup(tg);

        sortMenu.getItems().addAll(bubbleSort, heapSort, insertionSort, mergeSort, quickSort, selectionSort);
        bubbleSort.setSelected(true);
        sort = new BubbleSort();
        this.menuBar = new MenuBar(sortMenu, compare);
        this.setTop(menuBar);

        //Event
        for (MenuItem menuItem : sortMenu.getItems()) {
            menuItem.setOnAction(event -> {
                sort = Sort.getSortAlgo(menuItem.getText());
                lbInput.setDisable(false);
                txtInput.setDisable(false);
                if (sq != null) {
                    sq.stop();
                    sq = null;
                }
                display.getChildren().clear();

//                if (sort instanceof MergeSort) {
//                    myNodes = MergeSort.randomNodes(NO_OF_NODES);
//                } else {
//                    myNodes = ControlNodes.randomNodes(NO_OF_NODES);
//                }
                myNodes = ControlNodes.randomNodes(NO_OF_NODES);

                fillNode(NO_OF_NODES);

                btnPlay.setDisable(false);
                btnPause.setDisable(true);
                btnReplay.setDisable(true);
            });
        }

        compare.setOnAction(event -> {
            Pane pane = new Pane();
            Label label = new Label("Number of testcase: ");
            TextField number = new TextField();
            Label lbInputSize = new Label("Input size (comma-separated): ");
            TextField sizes = new TextField();

            HBox numberBox = new HBox(label, number);
            HBox sizeBox = new HBox(lbInputSize, sizes);
            sizeBox.setDisable(true);
            VBox vBox = new VBox(numberBox, sizeBox);

            numberBox.setSpacing(20);
            sizeBox.setSpacing(20);
            vBox.setSpacing(20);

            number.setOnKeyReleased(event1 -> {
                if (isValidate(number)) {
                    int numberOfTest = Integer.parseInt(number.getText());
                    sizeBox.setDisable(false);
                    String text = sizes.getText();
                    String[] cases = text.split(",");
                    int arr[] = new int[numberOfTest];
                    for (int i = 0; i < cases.length; ++i) {
                        arr[i] = Integer.parseInt(cases[i]);
                    }

                    for (int i = 0; i < numberOfTest; ++i) {
                        int[] temp = Sort.randomArray(arr[i]);
                        System.out.println(BubbleSort.getMillisSort(temp));
                    }

                } else {
                    sizeBox.setDisable(false);
                }
            });
        });
    }

    public void startAnimation() {
        sq = new SequentialTransition();

        sq.getChildren().addAll(sort.startSort(myNodes));

        sq.setOnFinished(e -> {
            btnPlay.setDisable(false);
            btnReplay.setDisable(false);
            btnPause.setDisable(true);

            lbInput.setDisable(false);
            txtInput.setText("");
            txtInput.setDisable(false);
        });

        sq.play();
    }

    public void setAllEvent() {
        txtInput.setOnKeyReleased(event -> {
            if (isValidate(txtInput) && Integer.parseInt(txtInput.getText()) <= 60) {
                createBar.setDisable(false);
            } else {
                createBar.setDisable(true);
            }
        });

        btnRandom.setOnAction(event -> {
            createRandom();
        });

        btnCustom.setOnAction(event -> {
            createCustom();
        });

        btnPlay.setOnAction(event -> {
            btnPlay.setDisable(true);
            btnPause.setDisable(false);
            btnReplay.setDisable(false);
            setDisableInputControl(true);

            if (sq == null) {
                startAnimation();
            } else {
                sq.play();
            }
        });

        btnPause.setOnAction(event -> {
            btnPause.setDisable(true);
            btnPlay.setDisable(false);
            btnReplay.setDisable(false);
            setDisableInputControl(false);

            sq.pause();
        });

        btnReplay.setOnAction(event -> {
            sq.stop();
            btnReplay.setDisable(true);
            btnPlay.setDisable(true);
            btnPause.setDisable(false);
            setDisableInputControl(true);

            startAnimation();
        });

        //TODO : speed
        boxSpeed.setOnAction(event -> {
            String speedStr = boxSpeed.getSelectionModel().getSelectedItem();
            double speed = Double.parseDouble(speedStr.substring(0, speedStr.length() - 1));
            MyNode.TIME_SWAP = (int) (MyNode.TIME_SWAP / speed);
        });
    }

    public void setDisableInputControl(boolean isDisable) {
        lbInput.setDisable(isDisable);
        txtInput.setDisable(isDisable);
        createBar.setDisable(isDisable);
    }

    public boolean isValidate(TextField textField) {
        String text = textField.getText();
        if (text == null || text.trim().equals("")) {
            return false;
        }
        if (text.startsWith("-") || text.contains(".") || text.contains(",")) {
            return false;
        } else {
            int size = 0;
            try {
                size = Integer.parseInt(text);
            } catch (NumberFormatException e) {
                return false;
            }
            if (size == 0) {
                return false;
            }
        }
        return true;
    }

    public void createRandom() {
        Scene scene = initComponent();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Random Elements");
        stage.setResizable(false);
        stage.show();
    }

    public void createCustom() {
        Label inputLB = new Label("Element 1:");
        TextField element = new TextField();
        Button btnNext = new Button("Next");
        Button btnDone = new Button("Done");
        btnDone.setDisable(true);

        int size = Integer.parseInt(txtInput.getText());
        MyNode[] temp = new MyNode[size];

        btnNext.setOnAction(event -> {
            if (isValidate(element)) {
                int value = Integer.parseInt(element.getText());
                String text = inputLB.getText();
                int start = text.indexOf(' ');
                int end = text.indexOf(':');
                int index = Integer.parseInt(text.substring(start + 1, end));
                temp[index - 1] = new MyNode(value);

                if (index < size) {
                    text = "Element " + (index + 1) + ":";
                    inputLB.setText(text);
                    element.setText("");
                } else {
                    btnDone.setDisable(false);
                    showAlert("Click Done to continue!");
                }
            } else {
                showAlert("Enter your number!");
            }
        });

        btnDone.setOnAction(event -> {
            myNodes = temp;
            if (sort instanceof MergeSort) {
                MergeSort.colorNodes(myNodes);
            } else {
                ControlNodes.colorNodes(myNodes);
            }
            fillNode(size);
            Stage stage = (Stage) btnDone.getScene().getWindow();
            stage.close();
        });

        HBox boxInput = new HBox(inputLB, element, btnNext);
        VBox vBox = new VBox(boxInput, btnDone);
        boxInput.setSpacing(20);
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(vBox);
        borderPane.setPadding(new Insets(20));

        Scene scene = new Scene(borderPane, 400, 150);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Custom Elements");
        stage.setResizable(false);
        stage.show();
    }

    public Scene initComponent(Node... nodes) {
        Label lbMin = new Label("Min : ");
        Label lbMax = new Label("Max : ");
        TextField txtMin = new TextField();
        txtMin.setText("1");
        TextField txtMax = new TextField();
        Button btnSubmit = new Button("Random");
        
        HBox input = new HBox(new Label("Input size: "), new TextField(txtInput.getText()));
        input.setDisable(true);
        HBox boxMin = new HBox(lbMin, txtMin);
        HBox boxMax = new HBox(lbMax, txtMax);
        VBox vBox = new VBox(input, boxMin, boxMax);

        input.setSpacing(20);
        boxMin.setSpacing(45);
        boxMax.setSpacing(45);
        vBox.setSpacing(20);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(vBox);
        borderPane.setPadding(new Insets(20));
        vBox.setAlignment(Pos.CENTER);

        HBox hBox = new HBox(btnSubmit);
        borderPane.setBottom(hBox);
        hBox.setAlignment(Pos.CENTER);

        btnSubmit.setOnMouseClicked(event -> {
            if (isValidate(txtMax) && isValidate(txtMin)) {
                int size = Integer.parseInt(txtInput.getText());
                int min = Integer.parseInt(txtMin.getText().trim());
                int max = Integer.parseInt(txtMax.getText().trim());
                myNodes = ControlNodes.randomNodes(size, min, max);
//                if (sort instanceof MergeSort) {
//                    myNodes = MergeSort.randomNodes(size, min, max);
//                } else {
//                    myNodes = ControlNodes.randomNodes(size, min, max);
//                }
                fillNode(size);

                Stage stage = (Stage) btnSubmit.getScene().getWindow();
                stage.close();
            } else {
                showAlert("Enter Min and Max number first!");
            }
        });

        Scene scene = new Scene(borderPane, 400, 400);
        return scene;
    }

    public void fillNode(int size) {
        display.getChildren().clear();
        addNodes();

        DX = WINDOW_WIDTH / size;
        animationBar.setDisable(false);
        btnPause.setDisable(true);
    }

    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.setTitle("Warning");
        alert.showAndWait();
    }
}
