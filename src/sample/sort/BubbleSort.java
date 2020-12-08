package sample.sort;

import javafx.animation.Transition;
import sample.node.MyNode;

import java.util.ArrayList;
import java.util.Arrays;

public class BubbleSort extends Sort {
    private boolean swapped;
    private ArrayList<Transition> transitions;

    public BubbleSort() {
        this.transitions = new ArrayList<>();
    }

    private ArrayList<Transition> compareNode(MyNode[] arr, int a, int b) {
        ArrayList<Transition> transitions = new ArrayList<>();

        transitions.add(colorNode(arr, SELECT_COLOR, a, b));

        if (arr[a].getValue() > arr[b].getValue()) {
            transitions.add(swap(arr, a, b));
            swapped = true;
        }

        transitions.add(colorNode(arr, START_COLOR, a, b));

        return transitions;
    }

    private void bubbleSort(MyNode[] arr) {
        for (int i = 0; i < arr.length; i++) {
            swapped = false;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                this.transitions.addAll(compareNode(arr, j, j + 1));
            }

            if (!swapped) {
                break;
            }
        }

    }

    @Override
    public ArrayList<Transition> startSort(MyNode[] arr) {
        bubbleSort(arr);

        this.transitions.add(colorNode(Arrays.asList(arr), SORTED_COLOR));

        return this.transitions;

    }

    public static int getMillisSort(int[] arr) {
        int n = arr.length;
        long start = System.currentTimeMillis();
        for (int i = n - 1; i > 0; --i)
        {
            for (int j = 0; j < i; ++j)
            {
                if (arr[j] > arr[j + 1])
                {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        long end = System.currentTimeMillis();
        int time = (int) (end - start);
        return time;
    }

}
