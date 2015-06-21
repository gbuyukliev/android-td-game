package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Tower extends AbstractTower {

    public Tower(float positionX, float positionY, TextureRegion[] textures) {
        super(positionX, positionY, textures);

        timeFromLastShot = Float.MAX_VALUE;
        damage = 25;
        fireRate = 1f;
        range = 120f;
        rotationSpeed = 90f;
    }

    @Override
    public AbstractProjectile shoot() {
        timeFromLastShot = 0f;
        Projectile projectile = new Projectile(position.x, position.y, damage, new TextureRegion(
                new Texture(Gdx.files.internal("projectile.png"))));
        projectile.setTarget(foe);
        return projectile;
    }

    @Override
    public boolean isReady() {
        return timeFromLastShot > fireRate;
    }
}
