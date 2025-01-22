package by.it.group310971.a_kokhan.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

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
    private class Segment implements Comparable<Segment>{
        int start;
        int stop;

        Segment(int start, int stop){
            if (start > stop) {
                this.start = stop;
                this.stop = start;
                return;
            }
            this.start = start;
            this.stop = stop;
            //тут вообще-то лучше доделать конструктор на случай если
            //концы отрезков придут в обратном порядке
        }

        @Override
        public int compareTo(Segment o) {
            //подумайте, что должен возвращать компаратор отрезков
            int difference = this.start-o.start;
            if (difference == 0)
                difference = this.stop-o.stop;
            return difference;
        }
    }


    int[] getAccessory(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        //число отрезков отсортированного массива
        int recordsNumber = scanner.nextInt();
        //число точек
        int eventsNumber = scanner.nextInt();
        int[] events = new int[eventsNumber];
        int[] result = new int[eventsNumber];

        //читаем сами отрезки
        int bufferInt;
        ArrayList<Segment> segmentList = new ArrayList<>();
        for (int i = 0; i < recordsNumber; i++) {
            bufferInt = 0;
            var currentItem = new Segment(scanner.nextInt(),scanner.nextInt());

            for (var element : segmentList){
                if (element.compareTo(currentItem) > 0)
                    break;
                bufferInt++;
            }
            segmentList.add(bufferInt, currentItem);
        }
        //читаем точки
        for (int i = 0; i < eventsNumber; i++) {
            events[i] = scanner.nextInt();
        }
        scanner.close();
        //тут реализуйте логику задачи с применением быстрой сортировки
        //в классе отрезка Segment реализуйте нужный для этой задачи компаратор
        for (int i = 0; i < events.length; i++)
            for (Segment segment : segmentList)
                if (events[i] >= segment.start && events[i] <= segment.stop)
                    result[i]++;

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson05/dataA.txt");
        A_QSort instance = new A_QSort();
        int[] result=instance.getAccessory(stream);
        for (int index:result){
            System.out.print(index+" ");
        }
    }

}
