package bg.ittalents.tower_defense.game.waves;

import com.badlogic.gdx.utils.Array;

public class LevelData {
    Array<CreepTypes> typesOfEnemies;
    Array<Array<CreepCount>> waves;

    public static class CreepTypes {
        int moveSpeed;
        int award;
        int level;
        String name;
        int health;
        String typeOfEnemy;

        @Override
        public String toString() {
            return "CreepTypes{" +
                    "moveSpeed=" + moveSpeed +
                    ", award=" + award +
                    ", level=" + level +
                    ", name='" + name + '\'' +
                    ", health=" + health +
                    ", typeOfEnemy='" + typeOfEnemy + '\'' +
                    '}';
        }
    }

    public static class CreepCount {
        int count;
        String typeOfEnemy;

        public CreepCount() {
        }

        public CreepCount(int count, String type) {
            this.count = count;
            typeOfEnemy = type;
        }

        @Override
        public String toString() {
            return "CreepCount{" +
                    "count=" + count +
                    ", typeOfEnemy='" + typeOfEnemy + '\'' +
                    '}';
        }
    }

    public static LevelData testWaves() {
        LevelData levelData = new LevelData();

        levelData.typesOfEnemies = new Array<CreepTypes>();
        levelData.waves = new Array<Array<CreepCount>>();

        levelData.typesOfEnemies.add(new CreepTypes());
        levelData.typesOfEnemies.add(new CreepTypes());
        levelData.typesOfEnemies.add(new CreepTypes());
        Array<CreepCount> wave = new Array<CreepCount>();
        wave.add(new CreepCount(1, "Type"));
        wave.add(new CreepCount(1, "Type"));
        wave.add(new CreepCount(1, "Type"));

        levelData.waves.add(wave);
        levelData.waves.add(wave);
        levelData.waves.add(wave);
        return levelData;
    }

    @Override
    public String toString() {
        return "WaveManager{" +
                "typesOfEnemies=" + typesOfEnemies +
                ", waves=" + waves +
                '}';
    }
}
