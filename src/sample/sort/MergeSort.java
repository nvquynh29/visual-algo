package sample.sort;

import javafx.animation.ParallelTransition;
import javafx.animation.Transition;
import javafx.scene.paint.Color;
import sample.node.MyNode;
import sample.util.ControlNodes;
import sample.view.AnimationController;

import java.util.*;

public class MergeSort extends Sort {
    public static final int BASE_LINE = 280;
    public static final int COMPARE_LINE = 500;
    public static final int DELTA_Y = 220;


    private MyNode[] tmp;

//    private ArrayList<Transition> merge(MyNode[] arr, int p, int q, int r) {
//        DX = AnimationController.WINDOW_WIDTH / AnimationController.NO_OF_NODES;
//        ArrayList<Transition> transitions = new ArrayList<>();
//
//        List<MyNode> tmpList = new ArrayList<>();
//
//        for (int i = p; i <= r; i++) {
//            tmp[i] = arr[i];
//            tmpList.add(tmp[i]);
//        }
//
////        int i = p;
////        int j = q + 1;
////        int k = p;
////
////        while (i <= q && j <= r) {
////            if (tmp[i].getValue() <= tmp[j].getValue()) {
////                arr[k++] = tmp[i++];
////            } else {
////                arr[k++] = tmp[j++];
////            }
////        }
////
////        while (i <= q) {
////            arr[k++] = tmp[i++];
////        }
////
////        while (j <= r) {
////            arr[k++] = tmp[j++];
////        }
//
//        transitions.add(colorNode(tmpList, SELECT_COLOR));
//        transitions.addAll(sortNode(tmpList));
//
//        Collections.sort(tmpList);
//
//        int temp = 0;
//        for (int h = p; h <= r; ++h) {
//            arr[h] = tmpList.get(temp);
//            temp++;
//        }
//
//        transitions.add(colorNode(tmpList, START_COLOR));
//
//        return transitions;
//    }
//
//    public List<Transition> sortNode(List<MyNode> nodes) {
//        List<Transition> result = new ArrayList<>();
//        List<MyNode> temp = new ArrayList<>();
//        int size = nodes.size();
//        for (int i = 0; i < size; ++i) {
//            temp.add(nodes.get(i).clone());
//        }
//
//        for (int i = 0; i < size - 1; ++i) {
//            for (int j = i + 1; j < size; ++j) {
//                if (temp.get(j).getValue() < temp.get(i).getValue()) {
//                    Collections.swap(temp, i, j);
//                }
//            }
//        }
//
//        for (int i = 0; i < size; ++i) {
//            MyNode node = temp.get(i);
//            node.setY(COMPARE_LINE - node.getHeight());
//            node.setX(nodes.get(i).getX());
//        }
//
//        MyNode dest = null, src = null;
//        for (int i = 0; i < size; ++i) {
//            dest = temp.get(i);
//            int index = searchNode(nodes, dest);
//            if (index == -1) {
//                System.out.println("NULL SRC");
//            } else {
//                src = nodes.get(index);
//                if (src.getX() != dest.getX()) {
//                    System.out.println("OTHER");
//                }
//                System.out.println("MOVE " + src + " => " + dest);
//                result.add(src.swap(dest));
//                System.out.println("NOW " + src);
//                if (src.getY() < 0 || src.getY() > 500 - src.getHeight()) {
//                    System.out.println("Value = " + src.getY() + " out");
//                }
//            }
//        }
//        result.addAll(moveUp(nodes));
//
//        return result;
//    }
private ArrayList<Transition> merge(MyNode[] arr, int p, int q, int r) {
    ArrayList<Transition> transitions = new ArrayList<>();

    List<MyNode> tmpList = new ArrayList<>();

    for (int i = p; i <= r; i++) {
        tmp[i] = arr[i];
        tmpList.add(tmp[i]);
    }

    int i = p;
    int j = q + 1;
    int k = p;

    while (i <= q && j <= r) {
        if (tmp[i].getValue() <= tmp[j].getValue()) {
            arr[k++] = tmp[i++];
        } else {
            arr[k++] = tmp[j++];
        }
    }

    while (i <= q) {
        arr[k++] = tmp[i++];
    }

    while (j <= r) {
        arr[k++] = tmp[j++];
    }

    transitions.add(colorNode(tmpList, SELECT_COLOR));

    ParallelTransition pt = new ParallelTransition();

    for (int x = p; x <= r; x++) {
        for (int y = p; y <= r; y++) {
            if (tmp[x].equals(arr[y])) {
                pt.getChildren().add(tmp[x].moveX(DX * (y - x)));
                pt.getChildren().add(tmp[x].moveTextX(DX * (y - x)));
            }
        }
    }

    transitions.add(pt);
    transitions.add(colorNode(tmpList, START_COLOR));

    return transitions;
}

    public int searchNode(List<MyNode> nodes, MyNode target) {
        int index = -1;
        for (int i = 0; i < nodes.size(); ++i) {
            if (nodes.get(i).getValue() == target.getValue()) {
                if (nodes.get(i).getY() + nodes.get(i).getHeight() == BASE_LINE) {
                    return i;
                }
            }
        }
        return index;
    }

    public static MyNode[] randomNodes(int numOfNodes) {
        MyNode[] arr = new MyNode[numOfNodes];
        Random r = new Random();

        for (int i = 0; i < numOfNodes; i++) {
            arr[i] = new MyNode(1 + r.nextInt(numOfNodes));
            arr[i].setX(i * (AnimationController.WINDOW_WIDTH / numOfNodes));
            arr[i].setFill(Sort.START_COLOR);
            setNode(arr[i], numOfNodes, numOfNodes);
            ControlNodes.setTextCoordinates(arr[i]);
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
        }

        for (int i = 0; i < numOfNodes; ++i) {
            setNode(arr[i], numOfNodes, ControlNodes.getMaxValue(arr));
            ControlNodes.setTextCoordinates(arr[i]);
        }
        return arr;
    }

    public static void setNode(MyNode myNode, int n, int max) {
        int width = AnimationController.WINDOW_WIDTH;
        int height = AnimationController.WINDOW_HEIGHT;
        int xGap = AnimationController.XGAP;
        int bottom = COMPARE_LINE;
        int top = AnimationController.TOP_BOUNDARY;

        myNode.setWidth(width / n - xGap);
        myNode.setHeight((height - bottom - top) / max * myNode.getValue());
        myNode.setY(COMPARE_LINE - myNode.getHeight());
    }

    public static MyNode[] colorNodes(MyNode[] arr) {
        int size = arr.length;
        for (int i = 0; i < size; i++) {
            arr[i].setX(i * (AnimationController.WINDOW_WIDTH / size));
            arr[i].setFill(Sort.START_COLOR);
            setNode(arr[i], size, ControlNodes.getMaxValue(arr));
            ControlNodes.setTextCoordinates(arr[i]);
        }
        return arr;
    }

    public List<Transition> moveUp(List<MyNode> nodes) {
        List<Transition> transitions = new ArrayList<>();
        for (MyNode node : nodes) {
            transitions.add(node.moveTo(0, -DELTA_Y));
        }
        return transitions;
    }

    public List<Transition> moveDown(MyNode... nodes) {
        List<Transition> transitions = new ArrayList<>();
        MyNode[] myNodes = nodes.clone();
        for (MyNode node : myNodes) {
            transitions.add(node.moveTo(0, DELTA_Y));
        }
        return transitions;
    }

//    private List<Transition> merge(MyNode arr[], int l, int m, int r) {
//        List<Transition> transitions = new ArrayList<>();
//        int i, j, k;  // Biến đếm cho các mảng L, R, merge
//        int n1 = m - l + 1;
//        int n2 = r - m;
//
//        MyNode[] L = new MyNode[n1];
//        MyNode[] R = new MyNode[n2];
//
//        for (i = 0; i < n1; ++i) {
//            L[i] = arr[l + i];
////            transitions.add(colorNode(arr, Color.RED, l + i));
//        }
//        for (j = 0; j < n2; ++j) {
//            R[j] = arr[m + 1 + j];
////            transitions.add(colorNode(arr, Color.LIGHTGREEN, m + 1 + j));
//        }
//
//        i = 0;
//        j = 0;
//        k = l;  // Gộp mảng từ l tới r
//
//        while ((i < n1) && (j < n2))
//        {
//            System.out.println(n1 + "-" + n2);
//            transitions.add(colorNode(L, SELECT_COLOR, i));
//            transitions.add(colorNode(R, SELECT_COLOR, j));
//            // Parallel transition
//            if (L[i].getValue() <= R[j].getValue())
//            {
////                k++;
////                i++;
//                transitions.addAll(moveDown(L[i], R[j]));
////                transitions.addAll(moveUp(L[i], R[j]));
//                k++;
//                i++;
////                arr[k++] = L[i++];
//            }
//            else
//            {
////                k++;
////                j++;
//                transitions.addAll(swap(L[i], R[j]));
////                transitions.addAll(moveUp(L[i], R[j]));
//                k++;
//                j++;
////                arr[k++] = R[j++];
//            }
//        }
//        while (i < n1)
//        {
//            arr[k++] = L[i++];
//        }
//        while (j < n2)
//        {
//            arr[k++] = R[j++];
//        }
//        return transitions;
//    }

//    public List<Transition> merge(MyNode[] arr, int l, int m, int r) {
//        List<Transition> transitions = new ArrayList<>();
//
//        int rightSize = r - m;
//        int leftSize = m - l + 1;
//        int maxSize = Math.max(leftSize, rightSize);
//
//        List<MyNode> left = new ArrayList<>();
//        List<MyNode> right = new ArrayList<>();
////        MyNode[] left = new MyNode[leftSize];
////        MyNode[] right = new MyNode[rightSize];
//        int size = r - l;
//
//        for (int i = 0; i < maxSize; ++i) {
//            if (i < leftSize) {
//                left.add(arr[l + i]);
//                transitions.add(colorNode(arr, SELECT_COLOR, l + i));
//            }
//            if (i < rightSize) {
//                right.add(arr[m + 1 + i]);
//                transitions.add(colorNode(arr, SELECT_COLOR, m + 1 + i));
//            }
//        }
//        int i = 0;
//        while (i < size) {
//            if (!left.isEmpty() && !right.isEmpty()) {
//                if (left.get(0).getValue() > right.get(0).getValue()) {
//                    arr[l + i] = right.remove(0);
//                } else {
//                    arr[l + i] = left.remove(0);
//                }
//            } else if (right.isEmpty()) {
//                arr[l + i] = left.remove(0);
//            } else {
//                arr[l + i] = right.remove(0);
//            }
//            i++;
//        }
//        return transitions;
//    }

    private ArrayList<Transition> mergeSort(MyNode[] arr, int l, int r) {
        ArrayList<Transition> transitions = new ArrayList<>();
        DX = AnimationController.WINDOW_WIDTH / arr.length;
        if (l < r) {
            int m = (l + r) / 2;
            transitions.addAll(mergeSort(arr, l, m));
            transitions.addAll(mergeSort(arr, m + 1, r));
            transitions.addAll(merge(arr, l,  m, r));
        }
        return transitions;
    }

    @Override
    public ArrayList<Transition> startSort(MyNode[] arr) {
        ArrayList<Transition> transitions = new ArrayList<>();

        this.tmp = new MyNode[arr.length];

        transitions.addAll(mergeSort(arr, 0, arr.length - 1));

        transitions.add(colorNode(Arrays.asList(arr), SORTED_COLOR));

        return transitions;
    }
}
