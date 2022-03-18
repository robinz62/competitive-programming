import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        int T = Integer.parseInt(br.readLine());
        for (int Ti = 0; Ti < T; Ti++) {
            String[] line = br.readLine().split(" ");
            int m = Integer.parseInt(line[0]);
            int n = Integer.parseInt(line[1]);
            int k = Integer.parseInt(line[2]);

            int[] f = new int[n];
            line = br.readLine().split(" ");
            for (int i = 0; i < n; i++) f[i] = Integer.parseInt(line[i])-1;

            // Observation: can never scroll folders back up
            // Greedy seems correct...?

            // folder -> count of emails for that folder
            int[] count = new int[m];
            for (int i = 0; i < n; i++) count[f[i]]++;

            // These three contain indices. [above] and [below] never contain finished things. [window] might.
            Deque<Integer> above = new ArrayDeque<>();
            Deque<Integer> window = new ArrayDeque<>();
            Deque<Integer> below = new ArrayDeque<>();
            TreeMap<Integer, TreeSet<Integer>> folderWindow = new TreeMap<>();  // folder value -> indices with that value
            for (int i = 0; i < k; i++) {
                if (!folderWindow.containsKey(f[i])) folderWindow.put(f[i], new TreeSet<>());
                folderWindow.get(f[i]).add(i);
                window.addLast(i);
            }
            for (int i = k; i < n; i++) below.addLast(i);

            boolean bad = false;
            for (int currTopFolder = 0; currTopFolder < m; currTopFolder++) {
                // We can file into folders [currTopFolder, currTopFolder + k - 1]
                while (count[currTopFolder] > 0) {
                    // File whatever we can given the current fixed windows
                    boolean go = true;
                    while (go) {
                        go = false;
                        Integer lowest = folderWindow.ceilingKey(currTopFolder);
                        if (lowest != null && lowest < currTopFolder + k) {
                            go = true;
                            int idxDone = folderWindow.get(lowest).first();
                            folderWindow.get(lowest).remove(idxDone);
                            if (folderWindow.get(lowest).isEmpty()) folderWindow.remove(lowest);
                            f[idxDone] = -1;
                            count[lowest]--;

                            // If there are unfiled files after, then increase r.
                            // Otherwise, decrease l to unfinished
                            if (!below.isEmpty()) {
                                int idx = below.removeFirst();
                                window.addLast(idx);
                                if (!folderWindow.containsKey(f[idx])) folderWindow.put(f[idx], new TreeSet<>());
                                folderWindow.get(f[idx]).add(idx);
                            } else if (!above.isEmpty()) {
                                int idx = above.removeLast();
                                window.addFirst(idx);
                                if (!folderWindow.containsKey(f[idx])) folderWindow.put(f[idx], new TreeSet<>());
                                folderWindow.get(f[idx]).add(idx);
                            }
                        }
                    }

                    if (count[currTopFolder] == 0) {
                        // gucci
                        break;
                    } else {
                        // scroll down
                        if (!below.isEmpty()) {
                            // first guy in window who isn't done is booted
                            while (true) {
                                if (window.isEmpty()) {
                                    bad = true;
                                    break;
                                }
                                int idx = window.removeFirst();
                                if (f[idx] == -1) continue;
                                if (!folderWindow.containsKey(f[idx])) {
                                    while (true) {
                                        // this should never happen...?
                                    }
                                }
                                folderWindow.get(f[idx]).remove(idx);
                                if (folderWindow.get(f[idx]).isEmpty()) folderWindow.remove(f[idx]);
                                above.addLast(idx);
                                break;
                            }
                            if (bad) break;

                            // add from below
                            int idx = below.removeFirst();
                            window.addLast(idx);
                            if (!folderWindow.containsKey(f[idx])) folderWindow.put(f[idx], new TreeSet<>());
                            folderWindow.get(f[idx]).add(idx);
                        } else {
                            // dead
                            bad = true;
                            break;
                        }
                    }
                }
                if (bad) break;
            }

            pw.println(bad ? "NO" : "YES");
        }

        pw.flush();
    }
}