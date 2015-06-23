package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import bg.ittalents.tower_defense.utils.Constants;

public abstract class AbstractTower extends AbstractObject {
    public static final String TAG = Assets.class.getName();

    TextureRegion[] textures;
    int upgrade;
    AbstractCreep foe;

    // rotation speed degrees per second
    float rotationSpeed;
    float range;
    float fireRate;
    float timeFromLastShot;
    int damage;

    public AbstractTower(float positionX, float positionY, TextureRegion[] textures) {
        super(positionX, positionY, textures[0]);
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
        return (range * range) > ((dx * dx) + (dy * dy));
    }

    public AbstractCreep getFoe() {
        return foe;
    }

    public void setFoe(AbstractCreep foe) {
        this.foe = foe;
    }

    public void upgrade() {
        if (textures != null && textures.length > upgrade + 1) {
            texture = textures[++upgrade];
        } else {
            Gdx.app.debug(TAG, "can't upgrade tower");
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
}
