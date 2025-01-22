package by.it.group310971.rogach.lesson13;

import by.it.HomeWork;
import org.junit.Test;

@SuppressWarnings("NewClassNamingConvention")
public class Test_Part2_Lesson13 extends HomeWork {

    @Test
    public void testGraphA() {
        // Примеры с числовыми вершинами
        run("0 -> 1", true).include("0 1");
        run("0 -> 1, 1 -> 2", true).include("0 1 2");
        run("0 -> 2, 1 -> 2, 0 -> 1", true).include("0 1 2");
        run("0 -> 2, 1 -> 3, 2 -> 3, 0 -> 1", true).include("0 1 2 3");
        run("1 -> 3, 2 -> 3, 2 -> 3, 0 -> 1, 0 -> 2", true).include("0 1 2 3");
        run("0 -> 1, 0 -> 2, 0 -> 2, 1 -> 3, 1 -> 3, 2 -> 3", true).include("0 1 2 3");

        // Примеры со строковыми вершинами
        run("A -> B, B -> C, C -> D", true).include("A B C D"); // Линейный граф

        run("0 -> 1, 1 -> 2, 1 -> 3", true).include("0 1 2 3"); // Несколько направлений

        run("0 -> 3, 0 -> 1, 1 -> 2, 2 -> 3", true).include("0 1 2 3"); // Линейный граф
        run("A -> C, A -> B, B -> D, C -> D, D -> E", true).include("A B C D E"); // Граф без циклов
        run("1 -> 2, 2 -> 3, 3 -> 4, 1 -> 4", true).include("1 2 3 4"); // Обвязывающий граф


        // Дополнительные примеры (различные структуры)

        // Смешанные примеры
    }


    @Test
    public void testGraphB() {
        run("0 -> 1", true).include("no").exclude("yes");
        run("0 -> 1, 1 -> 2", true).include("no").exclude("yes");
        run("0 -> 1, 1 -> 2, 2 -> 0", true).include("yes").exclude("no");
        run("0 -> 1, 1 -> 2, 2 -> 3", true).include("no").exclude("yes");
        run("0 -> 1, 1 -> 2, 2 -> 3, 3 -> 1", true).include("yes").exclude("no");
        run("0 -> 1, 0 -> 2, 1 -> 3, 2 -> 3", true).include("no").exclude("yes");
        run("0 -> 1, 1 -> 2, 2 -> 3, 3 -> 4, 4 -> 5", true).include("no").exclude("yes");
        run("0 -> 1, 1 -> 2, 2 -> 3, 3 -> 4, 4 -> 2", true).include("yes").exclude("no");
        run("0 -> 1, 1 -> 2, 2 -> 3, 3 -> 0", true).include("yes").exclude("no");
        run("0 -> 1, 1 -> 2, 2 -> 0, 0 -> 3, 3 -> 4", true).include("yes").exclude("no");
        run("0 -> 1, 1 -> 2, 2 -> 3, 3 -> 4, 5 -> 6, 6 -> 5", true).include("yes").exclude("no");
        run("0 -> 1, 1 -> 2, 2 -> 3, 3 -> 4, 5 -> 6", true).include("no").exclude("yes");
    }


    @Test
    public void testGraphC() {
        run("1->2, 2->3, 3->1, 3->4, 4->5, 5->6, 6->4", true)
                .include("123\n456");
        run("C->B, C->I, I->A, A->D, D->I, D->B, B->H, H->D, D->E, H->E, E->G, A->F, G->F, F->K, K->G", true)
                .include("C\nABDHI\nE\nFGK");
        run("A->B, B->C, C->A, A->C, B->A, C->B", true)
                .include("ABC");
    }
}
