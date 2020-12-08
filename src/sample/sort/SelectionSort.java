package sample.sort;

import javafx.animation.ParallelTransition;
import javafx.animation.Transition;
import javafx.scene.paint.Color;
import sample.node.MyNode;

import java.util.ArrayList;
import java.util.Arrays;

public class SelectionSort extends Sort {
    private static final Color MININDX_COLOR = Color.ORANGE;
    private static final Color NEW_MININDX_COLOR = Color.LIGHTGREEN;

    ArrayList<Transition> transitions = new ArrayList<>();

//    private ParallelTransition colorNode(MyNode[] arr, int a, int b, Color colorA, Color colorB) {
//        ParallelTransition pt = new ParallelTransition();
//
//        pt.getChildren().addAll(colorNode(arr, colorA, a), colorNode(arr, colorB, b));
//
//        return pt;
//    }

    public void selectionSort(MyNode[] arr) {
        for (int i = 0; i < arr.length - 1; ++i) {
            int min_idx = i;
            transitions.add(colorNode(arr, Color.RED, i));
            for (int j = i + 1; j < arr.length; ++j) {
                if (arr[j].getValue() < arr[min_idx].getValue())
                {
                    if (min_idx != i) {
                        transitions.add(colorNode(arr, START_COLOR, min_idx));
                    }
                    min_idx = j;
                    transitions.add(colorNode(arr, Color.GREEN, min_idx));
                } else {
                    transitions.add(colorNode(arr, SELECT_COLOR, j));
                    transitions.add(colorNode(arr, START_COLOR, j));
                }
            }
            transitions.add(swap(arr, i, min_idx));
            transitions.add(colorNode(arr, START_COLOR, i, min_idx));
        }
    }

    @Override
    public ArrayList<Transition> startSort(MyNode[] arr) {
        selectionSort(arr);

        transitions.add(colorNode(Arrays.asList(arr), SORTED_COLOR));

        return transitions;
    }
}
