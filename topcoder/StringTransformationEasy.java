import java.util.ArrayList;
import java.util.List;

public class StringTransformationEasy {
    public String getResult(String s, String t) {
        if (s.equals(t)) return "YES";
        if (s.length() <= t.length()) return "NO";
        if (s.length() % 2 != t.length() % 2) return "NO";
        if (s.charAt(0) != t.charAt(0)) return "NO";
        
        int currChar = s.charAt(0);
        int count = 0;
        List<Integer> sCounts = ballsToCounts(s);
        List<Integer> tCounts = ballsToCounts(t);
        if (sCounts.size() != tCounts.size()) return "NO";
        if (sCounts.size() == 1 && sCounts.get(0) != tCounts.get(0)) return "NO";
        for (int i = 0; i < sCounts.size(); i++) {
            if (sCounts.get(i) < tCounts.get(i)) return "NO";
            if ((sCounts.get(i) - tCounts.get(i)) % 2 != 0) return "NO";
            // OK: can reduce
        }
        return "YES";
    }

    List<Integer> ballsToCounts(String s) {
        List<Integer> l = new ArrayList<>();
        int prev = s.charAt(0);
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == prev) count++;
            else {
                l.add(count);
                count = 1;
                prev = s.charAt(i);
            }
        }
        l.add(count);
        return l;
    }
}