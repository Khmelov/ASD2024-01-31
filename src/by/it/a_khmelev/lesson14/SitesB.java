package by.it.a_khmelev.lesson14;

import java.util.*;

public class SitesB {

    // DSU class for managing disjoint sets
    public static class DSU {
        private int[] parent;

        public DSU(int size) {
            parent = new int[size];
        }

        void make_set(int v) {
            parent[v] = v;
        }

        int find_set(int v) {
            if (v == parent[v]) return v;
            parent[v] = find_set(parent[v]);  // Path compression
            return parent[v];
        }

        void union_sets(int a, int b) {
            a = find_set(a);
            b = find_set(b);
            if (a != b) {
                parent[b] = a;  // Union by rank can be added for optimization
            }
        }
    }

    // Calculates squared distance between two points
    public static int dist(int[] f, int[] s) {
        return (f[0] - s[0]) * (f[0] - s[0]) + (f[1] - s[1]) * (f[1] - s[1]) + (f[2] - s[2]) * (f[2] - s[2]);
    }

    // Checks if the squared distance between two points is less than or equal to a given value
    public static boolean less_dist(int d, int[] f, int[] s) {
        return dist(f, s) <= d * d;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // DSU initialization and input handling
        DSU myDSU = new DSU(100);  // Initialize DSU with an arbitrary size (will expand as needed)
        Map<String, Integer> toInd = new HashMap<>();
        int size = 0;

        // Read connections and process input
        String s;
        while (true) {
            s = in.next();
            if (s.equals("end")) break;

            int temp = s.indexOf('+');
            String f = s.substring(0, temp), d = s.substring(temp + 1);

            if (!toInd.containsKey(f)) {
                toInd.put(f, size++);
                myDSU.make_set(size - 1);
            }
            if (!toInd.containsKey(d)) {
                toInd.put(d, size++);
                myDSU.make_set(size - 1);
            }

            myDSU.union_sets(toInd.get(f), toInd.get(d));
        }

        // Count the sizes of each disjoint set
        Map<Integer, Integer> cnt = new HashMap<>();
        int free = 0;
        int[] ans = new int[size];

        for (int i = 0; i < size; ++i) {
            int p = myDSU.find_set(i);
            if (cnt.containsKey(p)) {
                ans[cnt.get(p)]++;
            } else {
                ans[free] = 1;
                cnt.put(p, free++);
            }
        }

        // Sort set sizes in descending order and print them
        System.out.println();
        ans = Arrays.copyOf(ans, cnt.size());
        Arrays.sort(ans);

        for (int i = ans.length - 1; i >= 0; i--) {
            System.out.print(ans[i] + " ");
        }
    }
}
