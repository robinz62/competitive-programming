/*
ID: robinz61
LANG: JAVA
TASK: gift1
*/
import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class gift1 {
    static int MOD = 1000000007;

    static String input = "gift1.in";
    static String output = "gift1.out";

    void solve() throws IOException {
        int n = ri();
        List<String> people = new ArrayList<>();
        for (int i = 0; i < n; i++) people.add(br.readLine());
        Map<String, List<String>> gifts = new HashMap<>();
        Map<String, Integer> initial = new HashMap<>();
        for (int i = 0; i < n; i++) {
            String person = br.readLine();
            gifts.put(person, new ArrayList<>());
            int[] xk = ril(2);
            int x = xk[0];
            int k = xk[1];
            initial.put(person, x);
            for (int j = 0; j < k; j++) gifts.get(person).add(br.readLine());
        }
        
        Map<String, Integer> end = new HashMap<>();
        for (String giver : gifts.keySet()) {
            int have = initial.get(giver);
            int k = gifts.get(giver).size();
            if (k != 0) {
                for (String receiver : gifts.get(giver)) {
                    end.put(receiver, end.getOrDefault(receiver, 0) + have / k);
                }
            }
            end.put(giver, end.getOrDefault(giver, 0) + (k == 0 ? have : have % k));
        }
        for (String person : people) {
            int diff = end.get(person) - initial.get(person);
            pw.println(person + " " + diff);
        }
    }

    // Template code below

    static BufferedReader br;
    static PrintWriter pw;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new FileReader(input));
        pw = new PrintWriter(new FileWriter(output));
        gift1 m = new gift1();
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
        int c = br.read();
        int x = 0;
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return ril(x);
    }

    long[] rkll() throws IOException {
        int c = br.read();
        int x = 0;
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