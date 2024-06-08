package by.it.group310971.Isaichikova.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class C_LongNotUpSubSeq {

    int getNotUpSeqSize(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);
        int n = scanner.nextInt();
        int[] m = new int[n];
        for (int i = 0; i < n; i++) {
            m[i] = scanner.nextInt();
        }
        int[] dp = new int[n];
        int[] prev = new int[n];
        dp[0] = 1;
        prev[0] = -1;
        int result = 1;
        int lastIndex = 0;
        for (int i = 1; i < n; i++) {
            dp[i] = 1;
            prev[i] = -1;
            for (int j = 0; j < i; j++) {
                if (m[j] >= m[i] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    prev[i] = j;
                }
            }
            if (dp[i] > result) {
                result = dp[i];
                lastIndex = i;
            }
        }
        int[] seq = new int[result];
        int index = lastIndex;
        int pos = result - 1;
        while (index != -1) {
            seq[pos] = index + 1;
            pos--;
            index = prev[index];
        }
        System.out.println(result);
        for (int i = 0; i < result; i++) {
            System.out.print(seq[i] + " ");
        }
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310971/Isaichikova/lesson06/dataC.txt");
        C_LongNotUpSubSeq instance = new C_LongNotUpSubSeq();
        int result = instance.getNotUpSeqSize(stream);
        System.out.print(result);
    }

}
