package bg.ittalents.tower_defense.game.waves;

import com.badlogic.gdx.utils.Array;

public class LevelData {
    Array<CreepTypes> enemies;
    Array<Array<CreepCount>> waves;
    Array<TowerTypes> towers;

    public static class CreepTypes {
        float moveSpeed;
        float award;
        int level;
        String name;
        float health;
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

    public static class TowerTypes {
        float damage;
        String typeOfTower;
        float price;
        float attackSpeed;
        float range;
        float sellPrice;

        @Override
        public String toString() {
            return "TowerTypes{" +
                    "damage=" + damage +
                    ", typeOfTower='" + typeOfTower + '\'' +
                    ", price=" + price +
                    ", attackSpeed=" + attackSpeed +
                    ", range=" + range +
                    ", sellPrice=" + sellPrice +
                    '}';
        }
    }

//    public static LevelData testWaves() {
//        LevelData levelData = new LevelData();
//
//        levelData.enemies = new Array<CreepTypes>();
//        levelData.waves = new Array<Array<CreepCount>>();
//
//        levelData.enemies.add(new CreepTypes());
//        levelData.enemies.add(new CreepTypes());
//        levelData.enemies.add(new CreepTypes());
//        Array<CreepCount> wave = new Array<CreepCount>();
//        wave.add(new CreepCount(1, "Type"));
//        wave.add(new CreepCount(1, "Type"));
//        wave.add(new CreepCount(1, "Type"));
//
//        levelData.waves.add(wave);
//        levelData.waves.add(wave);
//        levelData.waves.add(wave);
//        return levelData;
//    }

    @Override
    public String toString() {
        return "LevelData{" +
                "enemies=" + enemies +
                "\nwaves=" + waves +
                "\ntowers=" + towers +
                '}';
    }
}
