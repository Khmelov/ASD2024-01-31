package by.it.group310971.rusakovich.lesson03;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class C_HeapMax {

    private class MaxHeap {
        private List<Long> heap = new ArrayList<>();

        int siftDown(int i) { // просеивание вниз
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int largest = i;

            if (left < heap.size() && heap.get(left) > heap.get(largest)) {
                largest = left;
            }
            if (right < heap.size() && heap.get(right) > heap.get(largest)) {
                largest = right;
            }
            if (largest != i) {
                swap(i, largest);
                siftDown(largest);
            }
            return i;
        }

        int siftUp(int i) { // просеивание вверх
            while (i > 0) {
                int parent = (i - 1) / 2;
                if (heap.get(i) > heap.get(parent)) {
                    swap(i, parent);
                    i = parent;
                } else {
                    break;
                }
            }
            return i;
        }

        void insert(Long value) { // вставка
            heap.add(value);
            siftUp(heap.size() - 1);
        }

        Long extractMax() { // извлечение и удаление максимума
            if (heap.isEmpty()) {
                return null;
            }
            Long result = heap.get(0);
            heap.set(0, heap.get(heap.size() - 1));
            heap.remove(heap.size() - 1);
            if (!heap.isEmpty()) {
                siftDown(0);
            }
            return result;
        }

        private void swap(int i, int j) {
            Long temp = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, temp);
        }
    }

    Long findMaxValue(InputStream stream) {
        Long maxValue = 0L;
        MaxHeap heap = new MaxHeap();
        Scanner scanner = new Scanner(stream);
        int count = scanner.nextInt();
        scanner.nextLine(); // Пропустить оставшуюся часть строки после числа операций
        for (int i = 0; i < count; ) {
            String s = scanner.nextLine();
            if (s.equalsIgnoreCase("extractMax")) {
                Long res = heap.extractMax();
                if (res != null && res > maxValue) {
                    maxValue = res;
                }
                System.out.println(res);
                i++;
            } else if (s.contains(" ")) {
                String[] p = s.split(" ");
                if (p[0].equalsIgnoreCase("insert")) {
                    heap.insert(Long.parseLong(p[1]));
                    i++;
                }
            }
        }
        return maxValue;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310971/rusakovich/lesson03/heapData.txt");
        C_HeapMax instance = new C_HeapMax();
        System.out.println("MAX=" + instance.findMaxValue(stream));
    }
}
