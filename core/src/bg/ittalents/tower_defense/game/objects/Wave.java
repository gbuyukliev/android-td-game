package bg.ittalents.tower_defense.game.objects;

public class Wave {
    public static final int DEFAULT_CREEPS_COUNT = 6;
    public static final int BOSS_CREEPS_COUNT = 1;
    public static final int SLOW_CREEPS_COUNT = 3;
    private String typeOfCreeps;
    private int creepsCount;

    private Wave(String typeOfCreeps, int creepsCount)
    {
        this.typeOfCreeps = typeOfCreeps;
        this.creepsCount = creepsCount;
    }

    public static Wave createWave(int numOfWave) {
        if (isBoss(numOfWave)) {
            return new Wave("bossCreep", BOSS_CREEPS_COUNT);
        } else if (isSlow(numOfWave)) {
            return new Wave("slowCreep", SLOW_CREEPS_COUNT);
        } else if (isSpecial(numOfWave)) {
            return new Wave("specialCreep", DEFAULT_CREEPS_COUNT);
        } else {
            return new Wave("basicCreep", DEFAULT_CREEPS_COUNT);
        }
    }

    private static boolean isBoss(int numOfWave) {
        return numOfWave % 10 == 0;
    }

    private static boolean isSlow(int numOfWave) {
        return numOfWave % 5 == 0;
    }

    private static boolean isSpecial(int numOfWave) {
        return numOfWave % 7 == 0;
    }

    public String getTypeOfCreeps() {
        return typeOfCreeps;
    }

    public void setTypeOfCreeps(String typeOfCreeps) {
        if (typeOfCreeps != null) {
            this.typeOfCreeps = typeOfCreeps;
        }
    }

    public int getCreepsCount() {
        return creepsCount;
    }

    public void setCreepsCount(int creepsCount) {
        if (creepsCount > 0) {
            this.creepsCount = creepsCount;
        }
    }
}
