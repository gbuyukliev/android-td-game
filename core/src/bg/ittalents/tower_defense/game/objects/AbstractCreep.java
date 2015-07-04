package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;

import bg.ittalents.tower_defense.game.Assets;

public abstract class AbstractCreep extends AbstractObject {

    public static final float WAYPOINT_TOLERANCE;

    static {
        WAYPOINT_TOLERANCE = 5f;
    }

    Animation animation;
    float moveSpeed;
    int award;
    float health;
    float maxHealth;
    float stateTime;
    boolean isFlying;
    String typeOfEnemy;
    HealthBar healthBar;

    int currentWaypoint;
    Array<Vector2> waypoints;

    private class HealthBar {
        private final short BUFFER_X = 3;
        private final short BUFFER_Y = 44;
        private static final float SCALE = 1f;

        private Sprite healthBarBG;
        NinePatchDrawable health;

        public HealthBar() {
            healthBarBG = new Sprite(Assets.instance.getTexture(Assets.HEALTH_BAR_BACKGROUND));
            health = new NinePatchDrawable(new NinePatch(Assets.instance.getTexture(Assets.HEALTH_BAR), 1, 1, 1, 1));

            setPosition();
            healthBarBG.setOrigin(0, 0);
            healthBarBG.setScale(SCALE);
        }

        private void setPosition() {
            healthBarBG.setX(getTextureX() + BUFFER_X);
            healthBarBG.setY(getTextureY() + BUFFER_Y);
        }

        public void update() {
            setPosition();
        }

        public void render(Batch batch) {
            healthBarBG.draw(batch);
            health.draw(batch, getTextureX() + BUFFER_X, getTextureY() + BUFFER_Y,
                    AbstractCreep.this.health / AbstractCreep.this.maxHealth * 40f, 5f);
        }
    }

    public AbstractCreep(float positionX, float positionY, Animation animation) {
        super(positionX, positionY, animation.getKeyFrames()[0]);
        this.animation = animation;
        angle = 90f;
        currentWaypoint = -1;

        healthBar = new HealthBar();
    }

    public void setWaypoints(Array<Vector2> waypoints) {
        this.waypoints = waypoints;
        this.currentWaypoint = 0;
    }

    private void getNextWaypoint() {
        if (++this.currentWaypoint >= waypoints.size) {
            setVisible(false);
        }
    }

    public boolean isDead() {
        return health <= 0;
    }

    public int getReward () {
        return award;
    }

    private boolean isWaypointReached() {
        if (MathUtils.isEqual(position.x, waypoints.get(currentWaypoint).x, WAYPOINT_TOLERANCE) &&
                MathUtils.isEqual(position.y, waypoints.get(currentWaypoint).y, WAYPOINT_TOLERANCE)) {
            position.x = waypoints.get(currentWaypoint).x;
            position.y = waypoints.get(currentWaypoint).y;
            return true;
        }
        return false;
    }

    private void updatePosition(float deltaTime) {
        if (deltaTime > 1f) {
            return;
        }

        if (currentWaypoint >= 0 && currentWaypoint < waypoints.size) {
            if (isWaypointReached()) {
                getNextWaypoint();
                return;
            }
            double radRotation = AbstractObject.countAngle(position, waypoints.get(currentWaypoint));
            angle = (float) Math.toDegrees(radRotation);
            updatePosition(position.x + moveSpeed * deltaTime * (float)Math.cos(radRotation),
                    position.y + moveSpeed * deltaTime * (float)Math.sin(radRotation));
        }
    }

    public void reciveDamage(float damage) {
        health -= damage;
        if (health <= 0) {
            setVisible(false);
        }
    }

    public void update(float deltaTime) {
        updatePosition(deltaTime);
        stateTime += deltaTime;
        texture = animation.getKeyFrame(stateTime);
        healthBar.update();
    }

    @Override
    public void render(Batch batch) {
        super.render(batch);
//        if (health < maxHealth) {
            healthBar.render(batch);
//        }
    }
}
