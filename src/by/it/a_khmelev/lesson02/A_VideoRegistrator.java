package by.it.a_khmelev.lesson02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A_VideoRegistrator {

    public static void main(String[] args) {
        A_VideoRegistrator instance = new A_VideoRegistrator();
        double[] events = new double[]{1, 1.1, 1.6, 2.2, 2.4, 2.7, 3.9, 8.1, 9.1, 5.5, 3.7};
        List<Double> starts = instance.calcStartTimes(events, 1); // Рассчитаем моменты старта, с длиной сеанса 1
        System.out.println(starts); // Покажем моменты старта
    }

    List<Double> calcStartTimes(double[] events, double workDuration) {
        // Список для хранения результата
        List<Double> result = new ArrayList<>();

        // Сортируем массив событий
        Arrays.sort(events);

        int i = 0; // Индекс для итерации по массиву событий

        // Пока есть события, которые нужно зарегистрировать
        while (i < events.length) {
            // Получаем время начала текущего события
            double startTime = events[i];
            // Добавляем это время начала в результат
            result.add(startTime);
            // Вычисляем время окончания текущего сеанса записи
            double endTime = startTime + workDuration;
            // Пропускаем все события, покрываемые текущим сеансом записи
            while (i < events.length && events[i] <= endTime) {
                i++;
            }
        }

        return result; // Возвращаем результат
    }
}
