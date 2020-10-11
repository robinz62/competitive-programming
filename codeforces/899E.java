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
        int[] a = ril(n);
        int val = a[0];
        int cnt = 1;
        int id = 0;
        LinkedList ll = new LinkedList();
        TreeMap<Integer, TreeSet<ListNode>> countToNodes = new TreeMap<>();
        Comparator<ListNode> c = (n1, n2) -> Integer.compare(n1.id, n2.id);
        for (int i = 1; i < n; i++) {
            if (a[i] == val) cnt++;
            else {
                ListNode node = new ListNode(val, cnt, id++);
                if (!countToNodes.containsKey(cnt)) countToNodes.put(cnt, new TreeSet<>(c));
                countToNodes.get(cnt).add(node);
                val = a[i];
                cnt = 1;
                ll.addLast(node);
            }
        }
        ListNode nodex = new ListNode(val, cnt, id++);
        if (!countToNodes.containsKey(cnt)) countToNodes.put(cnt, new TreeSet<>(c));
        countToNodes.get(cnt).add(nodex);
        ll.addLast(nodex);

        int ans = 0;
        while (!countToNodes.isEmpty()) {
            ans++;
            int h = countToNodes.lastKey();
            ListNode node = countToNodes.get(h).first();
            countToNodes.get(h).remove(node);
            if (countToNodes.get(h).isEmpty()) countToNodes.remove(h);

            ListNode l = node.prev;
            ListNode r = node.next;
            ll.delete(node);
            if (l != null && r != null && l.val == r.val) {
                // remove l and r from map
                int prevlsize = l.cnt;
                int prevrsize = r.cnt;
                countToNodes.get(prevlsize).remove(l);
                countToNodes.get(prevrsize).remove(r);
                if (countToNodes.get(prevlsize).isEmpty()) countToNodes.remove(prevlsize);
                if (prevlsize != prevrsize && countToNodes.get(prevrsize).isEmpty()) countToNodes.remove(prevrsize);
                int newsize = prevlsize + prevrsize;
                l.cnt = newsize;
                ll.delete(r);
                if (!countToNodes.containsKey(newsize)) countToNodes.put(newsize, new TreeSet<>(c));
                countToNodes.get(newsize).add(l);
            }
        }
        pw.println(ans);
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
}

class ListNode {
    int val;
    int cnt;
    int id;  // keeps track of left to right ordering
    ListNode prev;
    ListNode next;
    ListNode(int v, int c, int i) { 
        val = v;
        cnt = c;
        id = i;
    }
}

class LinkedList {
    ListNode head;
    ListNode tail;
    int size;

    ListNode getFirst() { return head; }
    ListNode getLast() { return tail; }
    int size() { return size; }

    ListNode addLast(ListNode node) {
        size++;
        if (head == null) return head = tail = node;
        tail.next = node;
        node.prev = tail;
        tail = node;
        return node;
    }

    int removeFirst() {
        if (head == null) return -1;
        size--;
        int val = head.val;
        if (head == tail) {
            head = tail = null;
            return val;
        }
        head = head.next;
        head.prev = null;
        return val;
    }

    int removeLast() {
        if (tail == null) return -1;
        size--;
        int val = tail.val;
        if (head == tail) {
            head = tail = null;
            return val;
        }
        tail = tail.prev;
        tail.next = null;
        return val;
    }

    int delete(ListNode node) {
        if (node == head) return removeFirst();
        if (node == tail) return removeLast();
        size--;
        node.prev.next = node.next;
        node.next.prev = node.prev;
        return node.val;
    }

    void swapLeft(ListNode node) {
        if (node == head) return;
        ListNode ll = node.prev.prev;
        ListNode l = node.prev;
        ListNode r = node.next;
        if (l == head) head = node;
        if (node == tail) tail = l;
        if (ll != null) ll.next = node;
        node.prev = ll;
        node.next = l;
        l.prev = node;
        l.next = r;
        if (r != null) r.prev = l;
    }
}