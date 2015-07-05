package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

import bg.ittalents.tower_defense.game.Assets;

public class ProjectileSplash extends AbstractProjectile {
    private static Animation explosion = Assets.instance.getProjectile(Assets.EXPLOSION);

    public ProjectileSplash(float positionX, float positionY, AbstractTower tower) {
        super(positionX, positionY,
                Assets.instance.getProjectile(Assets.PROJECTILE_FIRE), tower);
        splashRadius = 80f;
    }
}
