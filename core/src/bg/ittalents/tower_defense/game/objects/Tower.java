package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import bg.ittalents.tower_defense.game.Level;

public class Tower extends AbstractTower {

    private static final Texture PROJECTILE_TEXTURE = new Texture(Gdx.files.internal("projectile.png"));

    public Tower(float positionX, float positionY, TextureRegion[] textures, Level level) {
        super(positionX, positionY, textures, level);

        timeFromLastShot = Float.MAX_VALUE;
        damage = 20;
        fireRate = 1f;
        range = 120f;
        rotationSpeed = 90f;
        price = 50;
        moneySpent = price;
        isUpgradable = true;
        upgradePrice = 30;
    }

    @Override
    public AbstractProjectile shoot() {
        timeFromLastShot = 0f;
        Projectile projectile = new Projectile(position.x, position.y, damage, new TextureRegion(
                PROJECTILE_TEXTURE));
        projectile.setTarget(foe);
        return projectile;
    }
}
