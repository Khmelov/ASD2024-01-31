package by.it.group310971.Isaichikova.lesson03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class C_HeapMax {

    private class MaxHeap {
        private List<Long> heap = new ArrayList<>();

        int siftDown(int i) { //просеивание вверх
            int parent = (i - 1) / 2;
            while (i > 0 && heap.get(i) > heap.get(parent)) {
                long temp = heap.get(i);
                heap.set(i, heap.get(parent));
                heap.set(parent, temp);
                i = parent;
                parent = (i - 1) / 2;
            }
            return i;
        }

        int siftUp(int i) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            while (left < heap.size()) {
                int maxChild = left;
                if (right < heap.size() && heap.get(right) > heap.get(left)) {
                    maxChild = right;
                }
                if (heap.get(i) < heap.get(maxChild)) {
                    long temp = heap.get(i);
                    heap.set(i, heap.get(maxChild));
                    heap.set(maxChild, temp);
                    i = maxChild;
                    left = 2 * i + 1;
                    right = 2 * i + 2;
                } else {
                    break;
                }
            }
            return i;
        }

        void insert(Long value) {
            heap.add(value);
            siftDown(heap.size() - 1);
        }

        Long extractMax() {
            Long result = null;
            if (!heap.isEmpty()) {
                result = heap.get(0);
                heap.set(0, heap.get(heap.size() - 1));
                heap.remove(heap.size() - 1);
                siftUp(0);
            }
            return result;
        }
    }

    Long findMaxValue(InputStream stream) {
        Long maxValue=0L;
        MaxHeap heap = new MaxHeap();
        Scanner scanner = new Scanner(stream);
        Integer count = scanner.nextInt();
        for (int i = 0; i < count; ) {
            String s = scanner.nextLine();
            if (s.equalsIgnoreCase("extractMax")) {
                Long res=heap.extractMax();
                if (res!=null && res>maxValue) maxValue=res;
                System.out.println();
                i++;
            }
            if (s.contains(" ")) {
                String[] p = s.split(" ");
                if (p[0].equalsIgnoreCase("insert"))
                    heap.insert(Long.parseLong(p[1]));
                i++;
            }
        }
        return maxValue;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310971/Isaichikova/lesson03/heapData.txt");
        C_HeapMax instance = new C_HeapMax();
        System.out.println("MAX="+instance.findMaxValue(stream));
    }
}
