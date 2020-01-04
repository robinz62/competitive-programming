import java.io.*;
import java.util.*;

public class A850 {
    public static void main(String[] args) throws IOException {
        A850 solver = new A850();
        solver.solve();
    }

    public void solve() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            points.add(new Point(br.readLine().split(" ")));
        }
        Vector[][] vectors = new Vector[n][n];
        for (int i = 0; i < n; ++i) {
            int[] pi = points.get(i).pt;
            for (int j = 0; j < n; ++j) {
                int[] pj = points.get(j).pt;
                int[] vect = new int[]{ pj[0] - pi[0], pj[1] - pi[1], pj[2] - pi[2], pj[3] - pi[3], pj[4] - pi[4] };
                vectors[i][j] = new Vector(vect);
            }
        }
        boolean[] bad = new boolean[n];
        int badCount = 0;
        for (int row = 0; row < n; ++row) {
            boolean terminate = false;
            for (int v1 = 0; v1 < n; ++v1) {
                for (int v2 = v1 + 1; v2 < n; ++v2) {
                    if (dot(vectors[row][v1], vectors[row][v2]) > 0) {
                        bad[row] = true;
                        ++badCount;
                        terminate = true;
                        break;
                    }
                }
                if (terminate) break;
            }
        }

        System.out.println(n - badCount);
        for (int i = 0; i < bad.length; ++i) {
            if (!bad[i]) System.out.println(i + 1);
        }
    }

    public static int dot(Vector a, Vector b) {
        int sum = 0;
        for (int i = 0; i < a.v.length; ++i) {
            sum += a.v[i] * b.v[i];
        }
        return sum;
    }

    private class Point {
        public int[] pt;
        public int hash;
        public Point(String[] p) {
            pt = new int[5];
            for (int i = 0; i < p.length; ++i) {
                pt[i] = Integer.parseInt(p[i]);
                hash += pt[i];
            }
        }
        public int hashCode() {
            return hash;
        }
        public boolean equals(Object o) {
            if (!(o instanceof Point)) return false;
            Point p = (Point) o;
            return Arrays.equals(pt, p.pt);
        }
        public String toString() {
            return pt[0] + " " + pt[1] + " " + pt[2] + " " + pt[3] + " " + pt[4];
        }
    }

    private class Vector {
        public int[] v;
        public int hash;
        public Vector(int[] v) {
            this.v = v;
            for (int i = 0; i < v.length; ++i) {
                hash += v[i];
            }
        }
        public int hashCode() {
            return hash;
        }
        public boolean equals(Object o) {
            if (!(o instanceof Vector)) return false;
            Vector p = (Vector) o;
            return Arrays.equals(v, p.v);
        }
    }
}