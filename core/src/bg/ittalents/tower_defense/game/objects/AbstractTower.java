package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import bg.ittalents.tower_defense.game.Assets;
import bg.ittalents.tower_defense.game.Level;


public abstract class AbstractTower extends AbstractObject {
    public static final String TAG = Assets.class.getName();
    public static final float UPGRADE_DAAMAGE_COEFF = 1.20f;
    public static final float UPGRADE_FIRE_RATE_COEFF = 0.8f;

    TextureRegion[] textures;
    int upgrade;
    AbstractCreep foe;

    // rotation moveSpeed degrees per second
    float rotationSpeed;
    float range;
    float fireRate;
    float timeFromLastShot;
    int damage;
    int price;
    int upgradePrice;
    int moneySpent;
    boolean isUpgradable;
    private Level level;

    public AbstractTower(float positionX, float positionY, TextureRegion[] textures, Level level) {
        super(positionX, positionY, textures[0]);
        this.level = level;
        this.textures = textures;
        upgrade = -1;
        upgrade();
    }

    public abstract AbstractProjectile shoot();

    public boolean isReadyToShoot() {
        return timeFromLastShot > fireRate;
    }

    public boolean isInRange(AbstractCreep foe) {
        float dx = (foe.position.x - position.x);
        float dy = (foe.position.y - position.y);
        return (getRange() * getRange()) > ((dx * dx) + (dy * dy));
    }

    public AbstractCreep getFoe() {
        return foe;
    }

    public void setFoe(AbstractCreep foe) {
        this.foe = foe;
    }

    public void upgrade() {
        if (textures != null && textures.length > upgrade + 1 && level.getMoney() >= getUpgradePrice()) {
            texture = textures[++upgrade];
            damage *= UPGRADE_DAAMAGE_COEFF;
            fireRate *= UPGRADE_FIRE_RATE_COEFF;
            moneySpent += getUpgradePrice();
            level.setMoney(level.getMoney() - getUpgradePrice());
        }

        if (textures.length <= upgrade + 1) {
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
}
