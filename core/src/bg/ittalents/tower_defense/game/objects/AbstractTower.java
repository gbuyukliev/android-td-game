package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import bg.ittalents.tower_defense.game.Assets;
import bg.ittalents.tower_defense.game.Level;


public abstract class AbstractTower extends AbstractObject {
    public static final String TAG = Assets.class.getName();
    public static final float UPGRADE_DAMAGE_COEFF = 1.20f;
    public static final float UPGRADE_FIRE_RATE_COEFF = 0.8f;

    TextureRegion[] textures;
    int upgrade;
    AbstractCreep foe;

    // rotation moveSpeed degrees per second
    protected float rotationSpeed;

    protected String typeOfTower;
    protected int damage;
    protected float fireRate;
    protected float range;
    protected int price;
    protected boolean isUpgradable;
    protected int upgradePrice;

    protected float timeFromLastShot;
    protected int moneySpent;
    protected Level level;

    Animation projectileAnimation;

    private Sound sound;

    public AbstractTower(float positionX, float positionY, TextureRegion[] textures, Level level) {
        super(positionX, positionY, textures[0]);
        this.level = level;
        this.textures = textures;
        timeFromLastShot = Float.MAX_VALUE;
        rotationSpeed = 90f;
        isUpgradable = true;
        upgrade = -1;
        upgrade();

        sound = Assets.instance.getSound(Assets.SOUND_LASER);
    }

    public static AbstractTower createTower(float col, float row, String type, Level level) {
        switch(type) {
            case "basicTower":
                return new TowerBasic(col, row, Assets.instance.getTower(Assets.TOWER_TYPE_BASIC), level);
            case "slowTower":
                return new TowerSlow(col, row, Assets.instance.getTower(Assets.TOWER_TYPE_SLOW), level);
            case "splashTower":
                return new TowerSplash(col, row, Assets.instance.getTower(Assets.TOWER_TYPE_SPLASH), level);
            case "specialTower":
                return new TowerSpecial(col, row, Assets.instance.getTower(Assets.TOWER_TYPE_SPECIAL), level);
            default:
                return new TowerBasic(col, row, Assets.instance.getTower(Assets.TOWER_TYPE_BASIC), level);
        }
    }

    public AbstractProjectile shoot() {
        timeFromLastShot = 0f;
        Projectile projectile = new Projectile(position.x, position.y,
                projectileAnimation, this);
        projectile.setMoveSpeed(projectile.getMoveSpeed() * Level.getCoeff());
        projectile.setTarget(foe);

        sound.play(0.3f);

        return projectile;
    }

    public boolean isReadyToShoot() {
        return timeFromLastShot > fireRate;
    }

    public boolean isInRange(AbstractCreep foe) {
        if ((typeOfTower.equals("specialTower") && !foe.getTypeOfCreep().equals("specialCreep")) ||
                (!typeOfTower.equals("specialTower") && foe.getTypeOfCreep().equals("specialCreep"))) {
            return false;
        }

        float dx = (foe.position.x - position.x);
        float dy = (foe.position.y - position.y);
        return (range * range) > ((dx * dx) + (dy * dy));
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
            damage *= UPGRADE_DAMAGE_COEFF;
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

    public String getTypeOfTower() {
        return typeOfTower;
    }

    public int getDamage() { return damage; }
}
