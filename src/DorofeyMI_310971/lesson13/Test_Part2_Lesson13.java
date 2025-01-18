package DorofeyMI_310971.lesson13;

import by.it.HomeWork;
import org.junit.Test;

@SuppressWarnings("NewClassNamingConvention")
public class Test_Part2_Lesson13 extends HomeWork {

    @Test
    public void testGraphA() {
        run("0 -> 1", true).include("0 1");
        run("0 -> 1, 1 -> 2", true).include("0 1 2");
        run("0 -> 2, 1 -> 2, 0 -> 1", true).include("0 1 2");
        run("0 -> 2, 1 -> 3, 2 -> 3, 0 -> 1", true).include("0 1 2 3");
        run("1 -> 3, 2 -> 3, 2 -> 3, 0 -> 1, 0 -> 2", true).include("0 1 2 3");
        run("0 -> 1, 0 -> 2, 0 -> 2, 1 -> 3, 1 -> 3, 2 -> 3", true).include("0 1 2 3");
        run("A -> B, A -> C, B -> D, C -> D", true).include("A B C D");
        run("A -> B, A -> C, B -> D, C -> D, A -> D", true).include("A B C D");
        run("0 -> 1, 1 -> 2, 2 -> 3, 3 -> 4, 4 -> 5", true).include("0 1 2 3 4 5");
        run("5 -> 4, 4 -> 3, 3 -> 2, 2 -> 1, 1 -> 0", true).include("5 4 3 2 1 0");
        run("0 -> 1, 0 -> 2, 1 -> 3, 2 -> 3, 3 -> 4, 4 -> 5", true).include("0 1 2 3 4 5");
        run("A -> B, B -> C, C -> D, D -> E, E -> F", true).include("A B C D E F");
        run("F -> E, E -> D, D -> C, C -> B, B -> A", true).include("F E D C B A");
        run("0 -> 1, 1 -> 2, 2 -> 3, 3 -> 4, 4 -> 5, 5 -> 6", true).include("0 1 2 3 4 5 6");
        run("6 -> 5, 5 -> 4, 4 -> 3, 3 -> 2, 2 -> 1, 1 -> 0", true).include("6 5 4 3 2 1 0");
        run("A -> B, B -> C, C -> D, D -> E, E -> F, F -> G", true).include("A B C D E F G");
        run("G -> F, F -> E, E -> D, D -> C, C -> B, B -> A", true).include("G F E D C B A");
        run("0 -> 1, 1 -> 2, 2 -> 3, 3 -> 4, 4 -> 5, 5 -> 6, 6 -> 7", true).include("0 1 2 3 4 5 6 7");
        run("7 -> 6, 6 -> 5, 5 -> 4, 4 -> 3, 3 -> 2, 2 -> 1, 1 -> 0", true).include("7 6 5 4 3 2 1 0");
        run("A -> B, B -> C, C -> D, D -> E, E -> F, F -> G, G -> H", true).include("A B C D E F G H");
        run("H -> G, G -> F, F -> E, E -> D, D -> C, C -> B, B -> A", true).include("H G F E D C B A");
    }


    @Test
    public void testGraphB() {
        run("0 -> 1", true).include("no").exclude("yes");
        run("0 -> 1, 1 -> 2", true).include("no").exclude("yes");
        run("0 -> 1, 1 -> 2, 2 -> 0", true).include("yes").exclude("no");
        run("1 -> 2, 2 -> 3, 3 -> 4", true).include("no").exclude("yes");
        run("1 -> 2, 2 -> 3, 3 -> 1", true).include("yes").exclude("no");
        run("A -> B, B -> C, C -> D", true).include("no").exclude("yes");
        run("A -> B, B -> C, C -> A", true).include("yes").exclude("no");
        run("X -> Y, Y -> Z", true).include("no").exclude("yes");
        run("X -> Y, Y -> Z, Z -> X", true).include("yes").exclude("no");
        run("P -> Q, Q -> R, R -> S", true).include("no").exclude("yes");
        run("P -> Q, Q -> R, R -> P", true).include("yes").exclude("no");
        run("1 -> 2, 2 -> 3, 3 -> 4, 4 -> 5", true).include("no").exclude("yes");
        run("1 -> 2, 2 -> 3, 3 -> 4, 4 -> 2", true).include("yes").exclude("no");
    }


    @Test
    public void testGraphC() {
        run("1->2, 2->3, 3->1, 3->4, 4->5, 5->6, 6->4", true)
                .include("123\n456");
        run("C->B, C->I, I->A, A->D, D->I, D->B, B->H, H->D, D->E, H->E, E->G, A->F, G->F, F->K, K->G", true)
                .include("C\nABDHI\nE\nFGK");
        //Дополните эти тесты СВОИМИ более сложными примерами и проверьте их работоспособность.
        //Параметр метода run - это ввод. Параметр метода include - это вывод.
        //Общее число примеров должно быть не менее 8 (сейчас их 2).
    }




}