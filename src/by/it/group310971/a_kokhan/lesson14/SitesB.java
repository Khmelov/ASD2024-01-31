package by.it.group310971.a_kokhan.lesson14;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class SitesB {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            var indexes = new HashMap<String, Integer>();
            var curr_index = 0;

            var in = new ArrayList<String>();
            String line;
            while (!(line = scanner.nextLine()).equals("end")) {
                in.add(line);
                var sites = line.split("\\+");
                for (var site : sites)
                    if (!indexes.containsKey(site))
                        indexes.put(site, curr_index++);
            }

            DSU dsu = new DSU(curr_index);
            for (var lineInput : in) {
                var sites = lineInput.split("\\+");
                dsu.union(indexes.get(sites[0]), indexes.get(sites[1]));
            }

            var sizes = new ArrayList<Integer>();
            var visited = new boolean[curr_index];
            for (var i = 0; i < curr_index; i++) {
                var root = dsu.find(i);
                if (!visited[root]){
                    visited[root] = true;
                    sizes.add(dsu.size[root]);
                }
            }

            sizes.sort(Collections.reverseOrder());
            for (var size : sizes) {
                System.out.print(size);
                System.out.print(" ");
            }
        }
    }
    
    private static class DSU {
        private int[] parent;
        private int[] size;

        private DSU(int n){
            parent = new int[n];
            size = new int[n];
            for (var i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        private int find(int v){
            if (v != parent[v])
                parent[v] = find(parent[v]);
            return parent[v];
        }

        private void union (int p, int q){
            var rootP = find(p);
            var rootQ = find(q);

            if (rootP == rootQ) { return; }

            if (size[rootP] < size[rootQ]) {
                parent[rootP] = rootQ;
                size[rootQ] += size[rootP];
            }
            else {
                parent[rootQ] = rootP;
                size[rootP] += size[rootQ];
            }
        }
    }
}