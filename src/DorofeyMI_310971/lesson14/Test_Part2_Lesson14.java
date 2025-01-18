package DorofeyMI_310971.lesson14;

import by.it.HomeWork;
import org.junit.Test;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

@SuppressWarnings("NewClassNamingConvention")
public class Test_Part2_Lesson14 extends HomeWork {

    public static final int MAX_DISTANCE = 25;
    public static final int TEST_COUNT = 100;

    private int distance;
    private final Random random = new Random(123);

    @Test(timeout = 5000)
    public void testPointsA() {
        for (int i = 0; i < TEST_COUNT; i++) {
            List<int[]> points = new ArrayList<>();
            String input = generatePointInput(points);
            String out = calculateTestOut(
                    points,
                    (x, y) -> Math.hypot(Math.hypot(x[0] - y[0], x[1] - y[1]), x[2] - y[2]) < distance
            );
            runTest(input, out);
        }
    }

    private void runTest(String input, String expectedOutput) {
        // Разделяем слово "input" и цифры на разные строки
         System.out.println("Running test with input:");
         System.out.println(input);
         // Выполнение теста и проверка вывода // ...
         System.out.println("Expected output: " + expectedOutput);
    }

    @Test(timeout = 5000)
    public void testSitesB() {
        for (int i = 0; i < TEST_COUNT; i++) {
            Set<String> sites = new HashSet<>();
            String input = generateSiteInput(sites);
            String out = calculateTestOut(
                    new ArrayList<>(sites),
                    (x, y) -> input.contains(x + "+" + y) || input.contains(y + "+" + x)
            );
            runTest(input, out);
        }
    }


    @Test(timeout = 5000)
    public void testStatesHanoiTowerC()
        { runTest("1", "1");
            runTest("2", "1 2");
            runTest("3", "1 2 4");
            runTest("4", "1 4 10");
            runTest("5", "1 4 8 18");
            runTest("10", "1 4 38 64 252 324 340");
            runTest("21", "1 4 82 152 1440 2448 14144 21760 80096 85120 116480 323232 380352 402556 669284");
    }

    private String generatePointInput(List<int[]> points) {
        StringBuilder out = new StringBuilder();
        distance = random.nextInt(MAX_DISTANCE);
        int diapason = 1 + distance + random.nextInt(MAX_DISTANCE) * random.nextInt(2);
        out.append(distance).append(" ");
        int n = 1 + random.nextInt(MAX_DISTANCE * 10);
        out.append(n);
        for (int i = 0; i < n; i++) {
            int[] point = {random.nextInt(-diapason, diapason), random.nextInt(diapason), random.nextInt(diapason)};
            out.append('\n')
                    .append(point[0]).append(" ")
                    .append(point[1]).append(" ")
                    .append(point[2]);
            points.add(point);
        }
        out.append('\n');
        return out.toString();
    }

    private String generateSiteInput(Set<String> sites) {
        var words = List.of("application java test hello world computer science course".split("\\s+"));
        var zones = List.of("com org mobile net app io info ru by ua".split("\\s+"));
        StringJoiner out = new StringJoiner("");
        int pairCount = 5 + random.nextInt(50);
        for (int i = 0; i < pairCount*2; i++) {
            String site = words.get(random.nextInt(words.size())) + "." + zones.get(random.nextInt(words.size()));
            sites.add(site);
            out.add(site).add(i % 2 == 0 ? "+" : "\n");
        }
        out.add("end\n");
        return out.toString();
    }


    private <T> String calculateTestOut(List<T> elements, BiPredicate<T, T> checkUnion) {
        List<Set<T>> fakeDsu = new ArrayList<>();
        for (T x : elements) {
            Set<T> set = new HashSet<>();
            set.add(x);
            fakeDsu.add(set);
        }
        for (int i = 0; i < fakeDsu.size(); i++) {
            for (Set<T> set : fakeDsu) {
                boolean union = false;
                ok:
                if (fakeDsu.get(i) != set) {
                    for (T x : fakeDsu.get(i)) {
                        for (T y : set) {
                            if (x != y && checkUnion.test(x, y) && (union = true)) {
                                break ok;
                            }
                        }
                    }
                }
                if (union) {
                    fakeDsu.get(i).addAll(set);
                    set.clear();
                    i = 0;
                }
            }
        }
        fakeDsu.removeIf(Set::isEmpty);
        return fakeDsu.stream()
                .map(Set::size)
                .sorted((n, m) -> m - n)
                .map(String::valueOf)
                .collect(Collectors.joining(" "))
                .trim();
    }


}