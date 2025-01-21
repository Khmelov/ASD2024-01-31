package by.it.a_khmelev.lesson14;

import java.util.Arrays;
import java.util.Scanner;

public class StatesHanoiTowerC {

    // Recursive method to solve Tower of Hanoi and update the number of moves per disk size
    static void solveHanoi(Integer[] pegs, Integer height, Integer[] moveCount, Integer from, Integer to) {
        if (height == 1) {
            // Move the disk from the 'from' peg to the 'to' peg
            pegs[to]++;
            pegs[from]--;
            // Update the count of moves for the peg with the maximum disks
            int maxPeg = Math.max(pegs[0], Math.max(pegs[1], pegs[2]));
            moveCount[maxPeg]++;
        } else {
            // Find the intermediate peg that isn't 'from' or 'to'
            int temp = (from == 0) ? (to == 1 ? 2 : 1) : (from == 1 ? (to == 0 ? 2 : 0) : (to == 1 ? 0 : 1));

            // Recursive step: move all but the largest disk to the intermediate peg
            solveHanoi(pegs, height - 1, moveCount, from, temp);
            // Move the largest disk to the 'to' peg
            pegs[to]++;
            pegs[from]--;
            int maxPeg = Math.max(pegs[0], Math.max(pegs[1], pegs[2]));
            moveCount[maxPeg]++;
            // Recursive step: move the disks from the intermediate peg to the 'to' peg
            solveHanoi(pegs, height - 1, moveCount, temp, to);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer n = in.nextInt();  // Number of disks

        // Array to track the number of disks on each peg
        Integer[] moveCount = new Integer[n + 1];
        // Array representing the number of disks on each peg (0: source, 1: auxiliary, 2: destination)
        Integer[] pegs = new Integer[3];

        // Initial setup: all disks start on peg 0 (source)
        pegs[0] = n;
        pegs[1] = 0;
        pegs[2] = 0;

        // Initialize the moveCount array to 0
        Arrays.fill(moveCount, 0);

        // Solve the Tower of Hanoi
        solveHanoi(pegs, n, moveCount, 0, 2);

        // Sort the move counts in ascending order
        Arrays.sort(moveCount);

        // Output the move counts for each disk size
        for (int i = 0; i < moveCount.length; ++i) {
            if (moveCount[i] > 0) {
                System.out.print(moveCount[i] + " ");
            }
        }
    }
}
