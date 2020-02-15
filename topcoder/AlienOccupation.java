import java.util.*;
public class AlienOccupation {
    public int[] getInfo(int N, int A, int[] X, int[] Y) {
        int T = 1;
        int U = -1;
        int V = 0;
        boolean[] visited = new boolean[N];
        Deque<Integer> q = new ArrayDeque<>();
        q.addLast(A);
        visited[A] = true;
        while (!q.isEmpty()) {
            int size = q.size();
            int gain = 0;
            for (int i = 0; i < size; i++) {
                int a = q.removeFirst();
                for (int j = 0; j < X.length; j++) {
                    int b = (int) (((long) X[j] * a + Y[j]) % N);
                    if (!visited[b]) {
                        visited[b] = true;
                        q.addLast(b);
                        gain++;
                    }
                }
            }
            V = Math.max(V, gain);
            T += gain;
            U++;
        }
        return new int[]{T, U, V};
    }
}