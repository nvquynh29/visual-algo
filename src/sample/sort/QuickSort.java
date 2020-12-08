package sample.sort;

import javafx.animation.Transition;
import javafx.scene.paint.Color;
import sample.node.MyNode;

import java.util.ArrayList;
import java.util.Arrays;

public class QuickSort extends Sort {
    private static final Color PIVOT_COLOR = Color.DARKMAGENTA;
    private static final Color LEFT_COLOR = Color.RED;
    private static final Color RIGHT_COLOR = Color.GREEN;
    private ArrayList<Transition> transitions;

    public QuickSort() {
        this.transitions = new ArrayList<>();
    }

    private void quickSort(MyNode[] arr, int lo, int hi) {
        if (lo < hi) {
            int q = partition(arr, lo, hi);
            quickSort(arr, lo, q - 1);
            quickSort(arr, q + 1, hi);
        }
    }

    //first element of array chosen as pivot
    private int partition(MyNode[] arr, int lo, int hi) {
        MyNode pivot = arr[lo];
        int left = lo + 1;
        int right = hi;
        transitions.add(colorNode(arr, PIVOT_COLOR, lo));
        while (true)
        {
            while ((left <= right) && arr[left].getValue() < pivot.getValue())
            {
                transitions.add(colorNode(arr, LEFT_COLOR, left));
                transitions.add(colorNode(arr, START_COLOR, left));
                left++;
            }
            while ((left <= right) && arr[right].getValue() >= pivot.getValue())
            {
                transitions.add(colorNode(arr, RIGHT_COLOR, right));
                transitions.add(colorNode(arr, START_COLOR, right));
                right--;
            }
            if (left < right)
            {
                transitions.add(colorNode(arr, SELECT_COLOR, left, right));
                transitions.add(swap(arr, left, right));
                transitions.add(colorNode(arr, START_COLOR, left, right));
                left++;
                right--;
            }
            else
            {
                break;
            }
        }
        transitions.add(colorNode(arr, SELECT_COLOR, lo, right));
        transitions.add(swap(arr, lo, right));
        transitions.add(colorNode(arr, START_COLOR, lo, right));
        return right;
    }

    @Override
    public ArrayList<Transition> startSort(MyNode[] arr) {
        quickSort(arr, 0, arr.length - 1);
        transitions.add(colorNode(Arrays.asList(arr), SORTED_COLOR));

        return transitions;
    }
}
