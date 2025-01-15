package DorofeyMI_310971.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Arrays;

/*
Видеорегистраторы и площадь.
На площади установлена одна или несколько камер.
Известны данные о том, когда каждая из них включалась и выключалась (отрезки работы)
Известен список событий на площади (время начала каждого события).
Вам необходимо определить для каждого события сколько камер его записали.

В первой строке задано два целых числа:
    число включений камер (отрезки) 1<=n<=50000
    число событий (точки) 1<=m<=50000.

Следующие n строк содержат по два целых числа ai и bi (ai<=bi) -
координаты концов отрезков (время работы одной какой-то камеры).
Последняя строка содержит m целых чисел - координаты точек.
Все координаты не превышают 10E8 по модулю (!).

Точка считается принадлежащей отрезку, если она находится внутри него или на границе.

Для каждой точки в порядке их появления во вводе выведите,
скольким отрезкам она принадлежит.
    Sample Input:
    2 3
    0 5
    7 10
    1 6 11
    Sample Output:
    1 0 0

*/

public class A_QSort {

    //отрезок
    private class Segment  implements Comparable<Segment>{
        int start;
        int stop;

        Segment(int start, int stop) {
            // Убедимся, что start всегда меньше stop
            this.start = Math.min(start, stop);
            this.stop = Math.max(start, stop);
        }

        @Override
        public int compareTo(Segment o) {
            // Сравниваем начальные точки, если они равны, то сравниваем конечные точки
            if (this.start == o.start) {
                return this.stop - o.stop;
            }
            return this.start - o.start;
        }
    }
    // Класс для представления событий
    private class Event implements Comparable<Event> {
        int time;
        int type; // 1 для начала отрезка, -1 для конца отрезка, 0 для точки

        Event(int time, int type) {
            this.time = time;
            this.type = type;
        }

        @Override
        public int compareTo(Event o) {
            // Сравниваем по времени, затем по типу события
            if (this.time == o.time) {
                return this.type - o.type;
            }
            return this.time - o.time;
        }
    }

    int[] getAccessory(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];
        int m = scanner.nextInt();
        int[] points = new int[m];
        int[] result = new int[m];

        Event[] events = new Event[2 * n + m];
        int eventIndex = 0;

        for (int i = 0; i < n; i++) {
            int start = scanner.nextInt();
            int stop = scanner.nextInt();
            segments[i] = new Segment(start, stop);
            events[eventIndex++] = new Event(start, 1); // Начало отрезка
            events[eventIndex++] = new Event(stop, -1); // Конец отрезка
        }

        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
            events[eventIndex++] = new Event(points[i], 0); // Точка
        }

        Arrays.sort(events);

        int activeSegments = 0;
        int pointIndex = 0;
        for (Event event : events) {
            if (event.type == 1) { // Начало отрезка
                activeSegments++;
            } else if (event.type == -1) { // Конец отрезка
                activeSegments--;
            } else { // Точка
                result[pointIndex++] = activeSegments;
            }
        }

        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "DorofeyMI_310971/lesson05/dataA.txt");
        A_QSort instance = new A_QSort();
        int[] result=instance.getAccessory(stream);
        for (int index:result){
            System.out.print(index+" ");
        }
    }

}
