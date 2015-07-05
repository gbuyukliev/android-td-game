package bg.ittalents.tower_defense.game;

import java.util.HashMap;

public class LevelData {
    private HashMap<String, CreepType> creeps;
    private HashMap<String, TowerType> towers;

    public static class CreepType {
        private String typeOfCreep;
        private int reward;
        private float moveSpeed;
        private float health;

        @Override
        public String toString() {
            return "CreepType{" +
                    "typeOfCreep=" + typeOfCreep +
                    ", reward=" + reward +
                    ", moveSpeed=" + moveSpeed +
                    ", health=" + health +
                    '}';
        }

        public String getTypeOfCreeps() {
            return typeOfCreep;
        }

        public void setTypeOfCreeps(String typeOfCreeps) {
            if (typeOfCreeps != null) {
                this.typeOfCreep = typeOfCreeps;
            }
        }

        public int getReward() {
            return reward;
        }

        public void setReward(int reward) {
            if (reward > 0) {
                this.reward = reward;
            }
        }

        public float getMoveSpeed() {
            return moveSpeed;
        }

        public void setMoveSpeed(float moveSpeed) {
            if (moveSpeed > 0) {
                this.moveSpeed = moveSpeed;
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
    }

    public static class TowerType {
        private String typeOfTower;
        private float damage;
        private float fireRate;
        private float range;
        private int price;
        private int upgradePrice;

        @Override
        public String toString() {
            return "TowerType{" +
                    "typeOfTower=" + typeOfTower +
                    ", damage='" + damage +
                    ", fireRate=" + fireRate +
                    ", range=" + range +
                    ", price=" + price +
                    ", upgradePrice=" + upgradePrice +
                    '}';
        }


        public String getTypeOfTower() {
            return typeOfTower;
        }

        public void setTypeOfTower(String typeOfTower) {
            if (typeOfTower != null) {
                this.typeOfTower = typeOfTower;
            }
        }

        public float getDamage() {
            return damage;
        }

        public void setDamage(float damage) {
            if (damage > 0) {
                this.damage = damage;
            }
        }

        public float getFireRate() {
            return fireRate;
        }

        public void setFireRate(float fireRate) {
            if (fireRate > 0) {
                this.fireRate = fireRate;
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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            if (price > 0) {
                this.price = price;
            }
        }

        public int getUpgradePrice() {
            return upgradePrice;
        }

        public void setUpgradePrice(int upgradePrice) {
            if (upgradePrice > 0) {
                this.upgradePrice = upgradePrice;
            }
        }
    }

    @Override
    public String toString() {
        return "LevelData{" +
                "creeps=" + creeps +
                "\ntowers=" + towers +
                '}';
    }

    public CreepType getCreep(String typeOfCreep) {
        for (String x : creeps.keySet()) {
            if (x.equals(typeOfCreep)) {
                return creeps.get(x);
            }
        }

        return null;
    }

    public TowerType getTower(String typeOfTower) {
        for (String x : towers.keySet()) {
            if (x.equals(typeOfTower)) {
                return towers.get(x);
            }
        }

        return null;
    }

//    static void getData() {
//        Json json = new Json();
//        json.setTypeName(null);
//        json.setUsePrototypes(false);
//        json.setIgnoreUnknownFields(true);
//        json.setOutputType(JsonWriter.OutputType.json);
//
//        Data data = json.fromJson(Data.class, Gdx.files.internal("offline/LevelData.json"));
//
//        Array<CreepType> results = new Array<CreepType>();
//        for (CreepType creep : data.creeps) {
//            results.add(creep.typeOfCreep);
//        }
//    }
}