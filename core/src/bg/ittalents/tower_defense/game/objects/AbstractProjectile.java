package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

public abstract class AbstractProjectile extends AbstractObject {
    protected float moveSpeed;
    protected AbstractCreep target;
    protected AbstractTower tower;
    Animation animation;
    float stateTime;
    float splashRadius;
    float slowAmount;
    boolean isSpecial;

    public AbstractProjectile(float positionX, float positionY, Animation animation, AbstractTower tower) {
        super(positionX, positionY, null);
        this.tower = tower;
        this.animation = animation;
        visible = true;
        texture = animation.getKeyFrame(0);
        moveSpeed = 250f;
        splashRadius = 0;
        slowAmount = 1f;
        isSpecial = false;
    }

    public void setTarget(AbstractCreep target) {
        this.target = target;
    }

    public boolean hasSplash() {
        return splashRadius > 0f;
    }

    protected void onImpact(float deltaTime) {
        target.receiveDamage(tower.getDamage());

        if (tower.getTypeOfTower().equals("slowTower")) {
            target.getSlowed();
        }
        setVisible(false);
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
        texture = animation.getKeyFrame(stateTime);
        double radRotation = AbstractObject.countAngle(position, target.position);
        angle = (float) Math.toDegrees(radRotation);

        updatePosition(position.x + moveSpeed * deltaTime * (float) Math.cos(radRotation),
                position.y + moveSpeed * deltaTime * (float) Math.sin(radRotation));
        if (isVisible() && position.dst(target.position) < texture.getRegionWidth()) {
            onImpact(deltaTime);
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

    public float getSplashRadius() {
        return splashRadius;
    }
}
