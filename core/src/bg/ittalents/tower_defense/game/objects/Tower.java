package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import bg.ittalents.tower_defense.game.Assets;
import bg.ittalents.tower_defense.game.Level;
import bg.ittalents.tower_defense.game.LevelData;
import bg.ittalents.tower_defense.utils.AudioManager;


public class Tower extends AbstractObject {
    public static final String TAG = Assets.class.getName();
    public static final float UPGRADE_DAMAGE_COEFF = 1.20f;
    public static final float UPGRADE_FIRE_RATE_COEFF = 0.8f;

    private TextureRegion[] textures;
    private int nextUpgrade;
    private Creep foe;

    // rotation moveSpeed degrees per second
    private float rotationSpeed;

    private String typeOfTower;
    private float damage;
    private float fireRate;
    private float range;
    private int price;
    private boolean isUpgradable;
    private int upgradePrice;

    private float timeFromLastShot;
    private int moneySpent;
    private Level level;

    private static LevelData.TowerType tower;

    private Sound sound;

    public Tower(float positionX, float positionY, TextureRegion[] textures, Level level) {
        super(positionX, positionY, textures[0]);
        this.level = level;
        this.textures = textures;
        timeFromLastShot = Float.MAX_VALUE;
        rotationSpeed = 90f;
        isUpgradable = true;
        nextUpgrade = 0;
        upgrade();

        sound = Assets.instance.getSound(Assets.SOUND_LASER);
    }

    public static Tower createTower(float col, float row, String type, Level level) {
        tower = level.getLevelData().getTower(type);
        TextureRegion[] textures = null;

        switch(tower.getTypeOfTower()) {
            case "basicTower":
                textures = Assets.instance.getTower(Assets.TOWER_TYPE_BASIC);
                break;
            case "slowTower":
                textures = Assets.instance.getTower(Assets.TOWER_TYPE_SLOW);
                break;
            case "splashTower":
                textures = Assets.instance.getTower(Assets.TOWER_TYPE_SPLASH);
                break;
            case "specialTower":
                textures = Assets.instance.getTower(Assets.TOWER_TYPE_SPECIAL);
                break;
            default:
                break;
        }

        Tower createdTower = new Tower(col, row, textures, level);

        createdTower.setDamage(tower.getDamage());
        createdTower.setFireRate(tower.getFireRate());
        createdTower.setPrice(tower.getPrice());
        createdTower.setRange(tower.getRange());
        createdTower.setTypeOfTower(tower.getTypeOfTower());
        createdTower.setUpgradePrice(tower.getUpgradePrice());
        createdTower.moneySpent = createdTower.price;

        return createdTower;
    }

    public Projectile getProjectile() {
        switch (typeOfTower) {
            case "slowTower":
                return new Projectile(position.x, position.y,
                        Assets.instance.getProjectile(Assets.PROJECTILE_ICE), this);
            case "splashTower":
                return new Projectile(position.x, position.y,
                        Assets.instance.getProjectile(Assets.PROJECTILE_FIRE), this);
            case "specialTower":
                return new Projectile(position.x, position.y,
                        Assets.instance.getProjectile(Assets.PROJECTILE_GREEN), this);
            case "basicTower":
            default:
                return new Projectile(position.x, position.y,
                        Assets.instance.getProjectile(Assets.PROJECTILE_GRAY), this);
        }
    }

    public Projectile shoot() {
        timeFromLastShot = 0f;
        Projectile projectile = getProjectile();
        projectile.setMoveSpeed(projectile.getMoveSpeed() * Level.getCoeff());
        projectile.setTarget(foe);

        AudioManager.instance.play(sound);
//        sound.play(0.3f);
        return projectile;
    }

    public boolean isReadyToShoot() {
        return timeFromLastShot > getFireRate();
    }

    public boolean isInRange(Creep foe) {
        if ((getTypeOfTower().equals("specialTower") && !foe.getTypeOfCreep().equals("specialCreep")) ||
                (!getTypeOfTower().equals("specialTower") && foe.getTypeOfCreep().equals("specialCreep"))) {
            return false;
        }

        float dx = (foe.position.x - position.x);
        float dy = (foe.position.y - position.y);
        return (getRange() * getRange()) > ((dx * dx) + (dy * dy));
    }

    public Creep getFoe() {
        return foe;
    }

    public void setFoe(Creep foe) {
        this.foe = foe;
    }

    public void upgrade() {
        if (textures != null && textures.length > nextUpgrade && level.getMoney() >= getUpgradePrice()) {
            texture = textures[nextUpgrade++];
            damage *= UPGRADE_DAMAGE_COEFF;
            setFireRate(getFireRate() * UPGRADE_FIRE_RATE_COEFF);
            moneySpent += getUpgradePrice();
            level.setMoney(level.getMoney() - getUpgradePrice());
        }

        if (textures.length <= nextUpgrade) {
            isUpgradable = false;
        }
    }

    public boolean hasTarget() {
        return foe != null && isInRange(foe);
    }

    public void update(float deltaTime) {
        timeFromLastShot += deltaTime;
        if (hasTarget()) {
            if (!foe.isVisible()) {
                foe = null;
                return;
            }
            double angleRad = AbstractObject.countAngle(position, foe.position);
            angle = (float) Math.toDegrees(angleRad);

//            Gdx.app.debug(TAG, "Rotation: " + angle);
        }
    }

    public int getPrice() {
        return price;
    }

    public boolean isUpgradable() {
        return isUpgradable;
    }

    public int getMoneySpent() {
        return moneySpent;
    }

    public float getRange() {
        return range;
    }

    public int getUpgradePrice() {
        return upgradePrice;
    }

    public String getTypeOfTower() {
        return typeOfTower;
    }

    public float getDamage() { return damage; }

    public void setTypeOfTower(String typeOfTower) {
        this.typeOfTower = typeOfTower;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getFireRate() {
        return fireRate;
    }

    public void setFireRate(float fireRate) {
        this.fireRate = fireRate;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setUpgradePrice(int upgradePrice) {
        this.upgradePrice = upgradePrice;
    }
}
