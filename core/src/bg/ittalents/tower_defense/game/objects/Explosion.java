package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

import bg.ittalents.tower_defense.game.Assets;

public class Explosion extends AbstractObject {
    Animation animation;
    float stateTime;
    float splashRadius;
    float explosionDuration;
    float damage;

    public Explosion(Projectile projectile) {
        super(projectile.position.x, projectile.position.y, null);
        this.animation = Assets.instance.getProjectile(Assets.EXPLOSION);
        damage = projectile.tower.getDamage();
        visible = true;
        texture = animation.getKeyFrame(0);
        this.splashRadius = projectile.splashRadius;
        stateTime = 0;
        explosionDuration = 0.5f;
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
        if (stateTime > explosionDuration) {
            setVisible(false);
            return;
        }
        texture = animation.getKeyFrame(stateTime);
    }

    public float getDamage() {
        return damage;
    }

    public boolean isInRange(Creep foe) {
        float dx = (foe.position.x - position.x);
        float dy = (foe.position.y - position.y);
        return (splashRadius * splashRadius) > ((dx * dx) + (dy * dy));
    }
}
