package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import bg.ittalents.tower_defense.game.Assets;
import bg.ittalents.tower_defense.game.Level;

public class Tower extends AbstractTower {

    private Sound sound;

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

        sound = Assets.instance.getSound(Assets.SOUND_LASER);
    }

    @Override
    public AbstractProjectile shoot() {
        timeFromLastShot = 0f;
        Projectile projectile = new Projectile(position.x, position.y, damage,
                Assets.instance.getTexture(Assets.PROJECTILE));
        projectile.setMoveSpeed(projectile.getMoveSpeed() * Level.getCoeff());
        projectile.setTarget(foe);
        sound.play();
        return projectile;
    }
}
