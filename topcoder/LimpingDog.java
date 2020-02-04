public class LimpingDog {
    public int countSteps(int time) {
        int t = 0;
        int totalSteps = 0;
        int currSteps = 0;
        int type = 0;
        while (t < time) {
            if (type == 2) {
                if (t + 2 <= time) {
                    totalSteps++;
                    currSteps++;
                    t += 2;
                } else break;
            } else {
                totalSteps++;
                currSteps++;
                t++;
            }
            type = (type + 1) % 4;
            if (currSteps == 47) {
                currSteps = 0;
                t += 42;
            }
        }
        return totalSteps;
    }
}