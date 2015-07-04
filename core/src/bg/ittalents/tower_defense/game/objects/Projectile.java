package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Projectile extends AbstractProjectile {

    public Projectile(float positionX, float positionY, int damage, TextureRegion texture) {
        super(positionX, positionY, texture);
        this.damage = damage;
        moveSpeed = 250f;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - texture.getRegionWidth() / 2, position.y - texture.getRegionHeight() / 2,
                texture.getRegionWidth() / 2, texture.getRegionWidth() / 2,
                texture.getRegionWidth(), texture.getRegionHeight(), 1f, 1f, angle);
    }
}
