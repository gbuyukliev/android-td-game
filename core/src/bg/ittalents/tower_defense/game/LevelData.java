package bg.ittalents.tower_defense.game;

import com.badlogic.gdx.utils.Array;

public class LevelData {
    Array<CreepTypes> creeps;
    Array<TowerTypes> towers;

    public static class CreepTypes {
        private String typeOfCreep;
        private float reward;
        private float moveSpeed;
        private float health;

        @Override
        public String toString() {
            return "CreepTypes{" +
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

        public float getReward() {
            return reward;
        }

        public void setReward(float reward) {
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

    public static class TowerTypes {
        private String typeOfTower;
        private float damage;
        private float fireRate;
        private float range;
        private float price;
        private float upgradePrice;

        @Override
        public String toString() {
            return "TowerTypes{" +
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

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            if (price > 0) {
                this.price = price;
            }
        }

        public float getUpgradePrice() {
            return upgradePrice;
        }

        public void setUpgradePrice(float upgradePrice) {
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

//    static void getData() {
//        Json json = new Json();
//        json.setTypeName(null);
//        json.setUsePrototypes(false);
//        json.setIgnoreUnknownFields(true);
//        json.setOutputType(JsonWriter.OutputType.json);
//
//        Data data = json.fromJson(Data.class, Gdx.files.internal("offline/LevelData.json"));
//
//        Array<CreepTypes> results = new Array<CreepTypes>();
//        for (CreepTypes creep : data.creeps) {
//            results.add(creep.typeOfCreep);
//        }
//    }
}