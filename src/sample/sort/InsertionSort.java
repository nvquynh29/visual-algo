package sample.sort;

import javafx.animation.ParallelTransition;
import javafx.animation.Transition;
import javafx.scene.paint.Color;
import sample.node.MyNode;

import java.util.ArrayList;
import java.util.Arrays;

public class InsertionSort extends Sort {
    private ArrayList<Transition> transitions;

    public InsertionSort() {
        this.transitions = new ArrayList<>();
    }

    public void insertionSort(MyNode[] arr) {
        for (int i = 1; i < arr.length; ++i) {
            transitions.add(colorNode(arr, Color.RED, i));
            for (int j = i; j > 0; --j) {
                if (arr[j].getValue() < arr[j - 1].getValue()) {
                    transitions.add(colorNode(arr, SELECT_COLOR, j - 1, j));
                    transitions.add(swap(arr, j - 1, j));
                    transitions.add(colorNode(arr, START_COLOR, j - 1, j));
                } else {
                    break;
                }
            }
            transitions.add(colorNode(arr, START_COLOR, i));
        }
    }

    @Override
    public ArrayList<Transition> startSort(MyNode[] arr) {

        insertionSort(arr);

        transitions.add(colorNode(Arrays.asList(arr), SORTED_COLOR));

        return transitions;

    }
}
