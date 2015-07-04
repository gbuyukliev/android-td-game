package bg.ittalents.tower_defense.game.waves;

import com.badlogic.gdx.utils.Array;

public class LevelData {
    Array<CreepTypes> enemies;
    Array<Array<CreepCount>> waves;
    Array<TowerTypes> towers;

    public static class CreepTypes {
        private float moveSpeed;
        private float reward;
        private int level;
        private float health;
        private String typeOfEnemy;

        @Override
        public String toString() {
            return "CreepTypes{" +
                    "moveSpeed=" + getMoveSpeed() +
                    ", award=" + getReward() +
                    ", level=" + getLevel() +
                    ", health=" + getHealth() +
                    ", typeOfEnemy='" + getTypeOfEnemy() + '\'' +
                    '}';
        }

        public float getMoveSpeed() {
            return moveSpeed;
        }

        public void setMoveSpeed(float moveSpeed) {
            if (moveSpeed > 0) {
                this.moveSpeed = moveSpeed;
            }
        }

        public float getReward() {
            return reward;
        }

        public void setReward(float reward) {
            if (reward > 0) {
                this.reward = reward;
            }
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            if (level > 0) {
                this.level = level;
            }
        }

        public float getHealth() {
            return health;
        }

        public void setHealth(float health) {
            if (health > 0) {
                this.health = health;
            }
        }

        public String getTypeOfEnemy() {
            return typeOfEnemy;
        }

        public void setTypeOfEnemy(String typeOfEnemy) {
            if (typeOfEnemy != null) {
                this.typeOfEnemy = typeOfEnemy;
            }
        }
    }

    public static class CreepCount {
        private int count;
        private String typeOfEnemy;

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

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            if (count > 0) {
                this.count = count;
            }
        }

        public String getTypeOfEnemy() {
            return typeOfEnemy;
        }

        public void setTypeOfEnemy(String typeOfEnemy) {
            if (typeOfEnemy != null) {
                this.typeOfEnemy = typeOfEnemy;
            }
        }
    }

    public static class TowerTypes {
        private float damage;
        private String typeOfTower;
        private float price;
        private float attackSpeed;
        private float range;
        private float sellPrice;

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

        public float getDamage() {
            return damage;
        }

        public void setDamage(float damage) {
            if (damage > 0) {
                this.damage = damage;
            }
        }

        public String getTypeOfTower() {
            return typeOfTower;
        }

        public void setTypeOfTower(String typeOfTower) {
            if (typeOfTower != null) {
                this.typeOfTower = typeOfTower;
            }
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            if (price > 0) {
                this.price = price;
            }
        }

        public float getAttackSpeed() {
            return attackSpeed;
        }

        public void setAttackSpeed(float attackSpeed) {
            if (attackSpeed > 0) {
                this.attackSpeed = attackSpeed;
            }
        }

        public float getRange() {
            return range;
        }

        public void setRange(float range) {
            if (range > 0) {
                this.range = range;
            }
        }

        public float getSellPrice() {
            return sellPrice;
        }

        public void setSellPrice(float sellPrice) {
            if (sellPrice > 0) {
                this.sellPrice = sellPrice;
            }
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
