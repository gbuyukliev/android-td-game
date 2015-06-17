package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import bg.ittalents.tower_defense.utils.Constants;

public abstract class AbstractTower extends AbstractObject {
    public static final String TAG = Assets.class.getName();

    TextureRegion[] textures;
    int upgrade;
    AbstractCreep foe;

    float range;
    float rate;
    float damage;

    public AbstractTower(Vector2 position, TextureRegion[] textures) {
        super(position, null);
        this.textures = textures;
        upgrade = -1;
        upgrade();
    }

    public boolean isInRange(AbstractCreep foe) {
        float dx = (foe.position.x - position.x);
        float dy = (foe.position.y - position.y);
        return (range * range) > ((dx * dx) + (dy * dy));
    }

    public void setFoe(AbstractCreep foe) {
        this.foe = foe;
//        Gdx.app.debug(TAG, "Foe");
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
        if (hasTarget()) {
            double radRotation = AbstractCreep.countAngle(position, foe.position);
            rotation = (float) Math.toDegrees(radRotation) -90f;
//            Gdx.app.debug(TAG, "Rotation: " + rotation);
        }
    }
}
