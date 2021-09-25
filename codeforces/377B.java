import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //   npe, particularly in maps
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int[] nms = ril(3);
        int n = nms[0];
        int m = nms[1];
        int s = nms[2];
        int[] a = ril(m);  // bugs
        int[] b = ril(n);  // skill
        int[] c = ril(n);  // costs

        // bugs sorted by difficulty
        List<Integer> bugs = new ArrayList<>(m);
        for (int i = 0; i < m; i++) bugs.add(i);
        Collections.sort(bugs, (i1, i2) -> Integer.compare(a[i1], a[i2]));

        // students sorted by skill
        List<Integer> students = new ArrayList<>(n);
        for (int i = 0; i < n; i++) students.add(i);
        Collections.sort(students, (i1, i2) -> Integer.compare(b[i1], b[i2]));

        // for each bug, there is an interval of students that can do it
        // binary search? is it possible to complete in less than X days?
        // given X, we know the person that has the most tasks has <= X tasks.
        //
        // Find the cheapest person that can do the hardest task. Give him X tasks.
        // repeat.

        int ans = Integer.MAX_VALUE;
        int l = 1;
        int r = 100000;
        while (l <= r) {
            int X = l + (r - l) / 2;
            if (possible(s, a, b, c, bugs, students, X)) {
                ans = X;
                r = X-1;
            } else l = X+1;
        }
        if (ans == Integer.MAX_VALUE) pw.println("NO");
        else {
            pw.println("YES");
            int[] arr = getanswer(s, a, b, c, bugs, students, ans);
            for (int i : arr) pw.print(i+1 + " ");
            pw.println();
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    boolean possible(int s, int[] a, int[] b, int[] c, List<Integer> bugs, List<Integer> students, int X) {
        PriorityQueue<Integer> costs = new PriorityQueue<>();
        int studentsIdx = students.size() - 1;
        int bugsIdx = bugs.size() - 1;
        while (bugsIdx >= 0) {
            int hardestVal = a[bugs.get(bugsIdx)];
            while (studentsIdx >= 0 && b[students.get(studentsIdx)] >= hardestVal) {
                int cost = c[students.get(studentsIdx)];
                costs.add(cost);
                studentsIdx--;
            }
            if (costs.isEmpty()) return false;
            int cost = costs.remove();

            if (cost > s) return false;
            s -= cost;
            bugsIdx -= X;
        }
        return true;
    }

    int[] getanswer(int s, int[] a, int[] b, int[] c, List<Integer> bugs, List<Integer> students, int X) {
        int[] ans = new int[a.length];
        TreeMap<Integer, List<Integer>> costToCount = new TreeMap<>();
        int studentsIdx = students.size() - 1;
        int bugsIdx = bugs.size() - 1;
        while (bugsIdx >= 0) {
            int hardestVal = a[bugs.get(bugsIdx)];
            while (studentsIdx >= 0 && b[students.get(studentsIdx)] >= hardestVal) {
                int cost = c[students.get(studentsIdx)];
                if (!costToCount.containsKey(cost)) costToCount.put(cost, new ArrayList<>());
                costToCount.get(cost).add(students.get(studentsIdx));
                studentsIdx--;
            }
            int cost = costToCount.firstKey();
            List<Integer> l = costToCount.get(cost);
            int student = l.remove(l.size() - 1);
            if (l.isEmpty()) costToCount.remove(cost);

            // student gets bugs bugsIdx down to bugsIdx-X (not inclusive)
            for (int i = 0; i < X; i++) {
                if (bugsIdx-i < 0) break;
                int bug = bugs.get(bugsIdx - i);
                ans[bug] = student;
            }
            bugsIdx -= X;
        }
        return ans;
    }

    // Template code below

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);

    public static void main(String[] args) throws IOException {
        Main m = new Main();
        m.solve();
        m.close();
    }

    void close() throws IOException {
        pw.flush();
        pw.close();
        br.close();
    }

    int ri() throws IOException {
        return Integer.parseInt(br.readLine().trim());
    }

    long rl() throws IOException {
        return Long.parseLong(br.readLine().trim());
    }

    int[] ril(int n) throws IOException {
        int[] nums = new int[n];
        int c = 0;
        for (int i = 0; i < n; i++) {
            int sign = 1;
            c = br.read();
            int x = 0;
            if (c == '-') {
                sign = -1;
                c = br.read();
            }
            while (c >= '0' && c <= '9') {
                x = x * 10 + c - '0';
                c = br.read();
            }
            nums[i] = x * sign;
        }
        while (c != '\n' && c != -1) c = br.read();
        return nums;
    }

    long[] rll(int n) throws IOException {
        long[] nums = new long[n];
        int c = 0;
        for (int i = 0; i < n; i++) {
            int sign = 1;
            c = br.read();
            long x = 0;
            if (c == '-') {
                sign = -1;
                c = br.read();
            }
            while (c >= '0' && c <= '9') {
                x = x * 10 + c - '0';
                c = br.read();
            }
            nums[i] = x * sign;
        }
        while (c != '\n' && c != -1) c = br.read();
        return nums;
    }

    int[] rkil() throws IOException {
        int sign = 1;
        int c = br.read();
        int x = 0;
        if (c == '-') {
            sign = -1;
            c = br.read();
        }
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return ril(x);
    }

    long[] rkll() throws IOException {
        int sign = 1;
        int c = br.read();
        int x = 0;
        if (c == '-') {
            sign = -1;
            c = br.read();
        }
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return rll(x);
    }

    char[] rs() throws IOException {
        return br.readLine().toCharArray();
    }

    void sort(int[] A) {
        Random r = new Random();
        for (int i = A.length-1; i > 0; i--) {
            int j = r.nextInt(i+1);
            int temp = A[i];
            A[i] = A[j];
            A[j] = temp;
        }
        Arrays.sort(A);
    }

    void printDouble(double d) {
        pw.printf("%.16f", d);
    }
}