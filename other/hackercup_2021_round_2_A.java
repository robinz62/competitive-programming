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
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int[] NM = ril(2);
            int N = NM[0];
            int M = NM[1];
            int[] S = ril(M);

            int[][] P = new int[N][];
            for (int i = 0; i < N; i++) P[i] = ril(M);
            
            List<Integer> yes = new ArrayList<>();
            List<Integer> no = new ArrayList<>();
            for (int si : S) yes.add(si);

            int ans = 0;
            for (int[] round : P) {
                boolean[] usedY = new boolean[yes.size()];
                boolean[] usedN = new boolean[no.size()];
                List<Integer> nextyes = new ArrayList<>();
                List<Integer> nextno = new ArrayList<>();

                Map<Integer, Integer> need = new HashMap<>();
                for (int style : round) need.put(style, need.getOrDefault(style, 0) + 1);
                
                for (int i = 0; i < no.size(); i++) {
                    int style = no.get(i);
                    if (need.containsKey(style)) {
                        usedN[i] = true;
                        if (need.get(style) == 1) need.remove(style);
                        else need.put(style, need.get(style) - 1);
                        nextno.add(style);
                    }
                }

                for (int i = 0; i < yes.size(); i++) {
                    int style = yes.get(i);
                    if (need.containsKey(style)) {
                        usedY[i] = true;
                        if (need.get(style) == 1) need.remove(style);
                        else need.put(style, need.get(style) - 1);
                        nextyes.add(style);
                    }
                }

                List<Integer> yy = new ArrayList<>();
                List<Integer> nn = new ArrayList<>();
                for (int i = 0; i < yes.size(); i++) if (!usedY[i]) yy.add(yes.get(i));
                for (int i = 0; i < no.size(); i++) if (!usedN[i]) nn.add(no.get(i));
                for (int style : need.keySet()) {
                    for (int x = 0; x < need.get(style); x++) {
                        if (!yy.isEmpty()) {
                            yy.remove(yy.size() - 1);
                            nextno.add(style);
                        } else {
                            nn.remove(nn.size() - 1);
                            nextno.add(style);
                            ans++;
                        }
                    }
                }

                yes = nextyes;
                no = nextno;
            }

            pw.println("Case #" + (Ti+1) + ": " + ans);
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

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