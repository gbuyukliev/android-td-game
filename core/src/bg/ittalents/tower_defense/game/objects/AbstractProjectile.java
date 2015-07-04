package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class AbstractProjectile extends AbstractObject {
    protected float moveSpeed;
    protected AbstractCreep target;
    protected AbstractTower tower;

    public AbstractProjectile(float positionX, float positionY, TextureRegion texture, AbstractTower tower) {
        super(positionX, positionY, texture);
        this.tower = tower;
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
            target.receiveDamage(tower.getDamage());

            if (tower.getEffect().equals("slow")) {
                target.getSlowed();
            }

            setVisible(false);
        }
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(float moveSpeed) {
        if (moveSpeed > 0) {
            this.moveSpeed = moveSpeed;
        }
    }
}
