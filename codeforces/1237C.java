import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int n = ri();
        int[][] points = new int[n][4];
        for (int i = 0; i < n; i++) {
            int[] xyz = ril(3);
            System.arraycopy(xyz, 0, points[i], 0, 3);
            points[i][3] = i+1;
        }
        Map<Integer, List<int[]>> planes = new HashMap<>();
        for (int[] p : points) {
            int z = p[2];
            if (!planes.containsKey(z)) planes.put(z, new ArrayList<>());
            planes.get(z).add(p);
        }
        List<int[]> newPoints = new ArrayList<>();
        for (int z : planes.keySet()) {
            int[] ret = solvePlane(planes.get(z));
            if (ret != null) newPoints.add(ret);
        }
        solveLine(newPoints, 2);
    }

    int[] solvePlane(List<int[]> points) {
        Map<Integer, List<int[]>> lines = new HashMap<>();
        for (int[] p : points) {
            int y = p[1];
            if (!lines.containsKey(y)) lines.put(y, new ArrayList<>());
            lines.get(y).add(p);
        }
        List<int[]> newPoints = new ArrayList<>();
        for (int y : lines.keySet()) {
            int[] ret = solveLine(lines.get(y), 0);
            if (ret != null) newPoints.add(ret);
        }
        return solveLine(newPoints, 1);
    }

    int[] solveLine(List<int[]> points, int axis) {
        Collections.sort(points, (p1, p2) -> Integer.compare(p1[axis], p2[axis]));
        for (int i = points.size()-2; i >= 0; i -= 2) {
            int i1 = points.get(i+1)[3];
            int i2 = points.get(i)[3];
            points.remove(i+1);
            points.remove(i);
            pw.println(i1 + " " + i2);
        }
        return points.isEmpty() ? null : points.get(0);
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
        return Integer.parseInt(br.readLine());
    }

    long rl() throws IOException {
        return Long.parseLong(br.readLine());
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

    int max(int a, int b) {
        return a >= b ? a : b;
    }

    int min(int a, int b) {
        return a <= b ? a : b;
    }
}