package game;

public class CombatRules {

    /** 
     * @param currentHp
     * @param maxHp
     * @param amount
     * @return int
     */
    public static int calculateNewHp(int currentHp, int maxHp, int amount) {
        int newHp = currentHp + amount;

        if (newHp > maxHp)
            return maxHp;

        return newHp;
    }

    /** 
     * @param hp
     * @return boolean
     */
    public static boolean isDead(int hp) {
        return hp < 1;
    }
}
