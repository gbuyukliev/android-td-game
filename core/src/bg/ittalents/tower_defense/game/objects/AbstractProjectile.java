package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class AbstractProjectile extends AbstractObject {
    float moveSpeed;
    int damage;
    AbstractCreep target;

    public AbstractProjectile(float positionX, float positionY, TextureRegion texture) {
        super(positionX, positionY, texture);
    }

    public void setTarget(AbstractCreep target) {
        this.target = target;
    }

    public void update(float deltaTime) {
        if (deltaTime > 1f) {
            return;
        }
        double radRotation = AbstractObject.countAngle(position, target.position);
        angle = (float) Math.toDegrees(radRotation);

        updatePosition(position.x + moveSpeed * deltaTime * (float)Math.cos(radRotation),
                position.y + moveSpeed * deltaTime * (float)Math.sin(radRotation));
//        position.x += moveSpeed * deltaTime * Math.cos(radRotation);
//        position.y += moveSpeed * deltaTime * Math.sin(radRotation);
//        texturePosition.x = position.x - texture.getRegionWidth();
//        texturePosition.y = position.y - texture.getRegionHeight();

        if (isVisible() && position.dst(target.position) < texture.getRegionWidth()) {
            target.reciveDamage(damage);
            setVisible(false);
        }
    }
}
