package by.it.group310971.rogach.lesson02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*
Даны события events
реализуйте метод calcStartTimes, так, чтобы число включений регистратора на
заданный период времени (1) было минимальным, а все события events
были зарегистрированы.
Алгоритм жадный. Для реализации обдумайте надежный шаг.
*/

public class A_VideoRegistrator {

    public static void main(String[] args) {
        A_VideoRegistrator instance=new A_VideoRegistrator();
        double[] events=new double[]{1, 1.1, 1.6, 2.2, 2.4, 2.7, 3.9, 8.1, 9.1, 5.5, 3.7};
        List<Double> starts=instance.calcStartTimes(events,1); //рассчитаем моменты старта, с длинной сеанса 1
        System.out.println(starts);                            //покажем моменты старта
    }

    protected List<Double> calcStartTimes(double[] events, double workDuration){
        //events - события которые нужно зарегистрировать
        //timeWorkDuration время работы видеокамеры после старта
        // Сортировка событий по времени
        Arrays.sort(events);
        List<Double> result = new ArrayList<>();
        int i=0;                              //i - это индекс события events[i]
        while (i < events.length) {
            // Выбираем первое не зарегистрированное событие
            double startTime = events[i];
            result.add(startTime);

            // Вычисляем момент окончания работы видеокамеры
            double endTime = startTime + workDuration;

            // Пропускаем все события, которые покрываются текущей видеокамерой
            while (i < events.length && events[i] <= endTime) {
                i++;
            }
        }

        return result;                        //вернем итог
    }
}
