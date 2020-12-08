package sample.sort;

import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.Transition;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sample.node.MyNode;
import sample.view.AnimationController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Sort {
    public static final Color START_COLOR = Color.web("#ADD8E6");
    public static final Color SELECT_COLOR = Color.web("#FFA500");
    public static final Color SORTED_COLOR = START_COLOR;

    static int DX;

    ParallelTransition colorNode(MyNode[] arr, Color color, int... a) {
        ParallelTransition pt = new ParallelTransition();
        for (int i = 0; i < a.length; i++) {
            FillTransition ft = new FillTransition();
            ft.setShape(arr[a[i]]);
            ft.setToValue(color);
            ft.setDuration(Duration.millis(MyNode.TIME_SWAP));
            pt.getChildren().add(ft);
        }
        return pt;
    }

    ParallelTransition colorNode(List<MyNode> list, Color color) {
        ParallelTransition pt = new ParallelTransition();
        for (MyNode myNode : list) {
            FillTransition ft = new FillTransition();
            ft.setShape(myNode);
            ft.setToValue(color);
            ft.setDuration(Duration.millis(100));
            pt.getChildren().add(ft);
        }

        return pt;
    }

    ParallelTransition swap(MyNode[] arr, int i, int j) {
        ParallelTransition pt = new ParallelTransition();
        DX = AnimationController.DX;
        int dxFactor = j - i;

        pt.getChildren().addAll(arr[i].moveX(DX * dxFactor), arr[j].moveX(-DX * dxFactor));
        pt.getChildren().addAll(arr[i].moveTextX(DX * dxFactor), arr[j].moveTextX(-DX * dxFactor));

        MyNode tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;

        return pt;
    }


    public static Sort getSortAlgo(String name) {
        Sort sort = null;
        if (name.equals("Bubble Sort")) {
            sort = new BubbleSort();
        } else if (name.equals("Heap Sort")) {
            sort = new HeapSort();
        } else if (name.equals("Insertion Sort")) {
            sort = new InsertionSort();
        } else if (name.equals("Merge Sort")) {
            sort = new MergeSort();
        } else if (name.equals("Quick Sort")) {
            sort = new QuickSort();
        } else if (name.equals("Selection Sort")) {
            sort = new SelectionSort();
        }
        return sort;
    }

    public abstract ArrayList<Transition> startSort(MyNode[] arr);


    public static int[] randomArray(int size) {
        Random rd = new Random();
        int arr[] = new int[size];
        for (int i = 0; i < size; ++i) {
            arr[i] = rd.nextInt(size * 2 + 1);
        }
        return arr;
    }
}
