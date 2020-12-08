package sample.util;

import javafx.scene.text.Text;
import sample.node.MyNode;
import sample.sort.Sort;
import sample.view.AnimationController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ControlNodes {
    private static List<Text> texts = new ArrayList<>();

    public ControlNodes() {

    }

    public List<Text> getTexts() {
        return texts;
    }

    public static void setTextCoordinates(MyNode node) {
        Text text = node.getText();
        double textSize = text.getBoundsInLocal().getWidth();
        double delta = (node.getWidth() - textSize) / 2;
        text.setX(node.getX() + delta);
        text.setY(node.getY() - 5);
        node.setText(text);
    }

    public static List<Text> getTexts(MyNode[] nodes) {
        List<Text> texts = new ArrayList<>();
        for (MyNode node : nodes) {
            texts.add(node.getText());
        }
        return texts;
    }

    public static MyNode[] randomNodes(int numOfNodes) {
        MyNode[] arr = new MyNode[numOfNodes];
        Random r = new Random();

        for (int i = 0; i < numOfNodes; i++) {
            arr[i] = new MyNode(1 + r.nextInt(numOfNodes));
            arr[i].setX(i * (AnimationController.WINDOW_WIDTH / numOfNodes));
            arr[i].setFill(Sort.START_COLOR);
            setNode(arr[i], numOfNodes, numOfNodes);
            setTextCoordinates(arr[i]);
        }
        return arr;
    }

    public static MyNode[] randomNodes(int numOfNodes, int min, int max) {
        MyNode[] arr = new MyNode[numOfNodes];
        Random r = new Random();

        for (int i = 0; i < numOfNodes; i++) {
            arr[i] = new MyNode(r.nextInt(max - min + 1) + min);
            arr[i].setX(i * (AnimationController.WINDOW_WIDTH / numOfNodes));
            arr[i].setFill(Sort.START_COLOR);
            setNode(arr[i], numOfNodes, max);
            setTextCoordinates(arr[i]);
        }
        return arr;
    }

    public static MyNode[] colorNodes(MyNode[] arr) {
        int size = arr.length;
        for (int i = 0; i < size; i++) {
            arr[i].setX(i * (AnimationController.WINDOW_WIDTH / size));
            arr[i].setFill(Sort.START_COLOR);
            setNode(arr[i], size, getMaxValue(arr));
            setTextCoordinates(arr[i]);
        }
        return arr;
    }

    public static int getMaxValue(MyNode[] arr) {
        int max = 0;
        for (int i = 0; i < arr.length; ++i) {
            if (arr[i].getValue() > max) {
                max = arr[i].getValue();
            }
        }
        return max;
    }

    public static void setNode(MyNode myNode, int n, int max) {
        int width = AnimationController.WINDOW_WIDTH;
        int height = AnimationController.WINDOW_HEIGHT;
        int xGap = AnimationController.XGAP;
        int bottom = AnimationController.BOTTOM_BOUNDARY;
        int top = AnimationController.TOP_BOUNDARY;

        myNode.setWidth(width / n - xGap);
        myNode.setHeight((height - bottom - top) / max * myNode.getValue());
        myNode.setY(AnimationController.BASE_LINE - myNode.getHeight());
    }
}
