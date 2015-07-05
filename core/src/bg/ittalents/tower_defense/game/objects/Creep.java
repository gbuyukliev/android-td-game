package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

import bg.ittalents.tower_defense.game.Assets;
import bg.ittalents.tower_defense.game.Level;
import bg.ittalents.tower_defense.game.LevelData;

public class Creep extends AbstractObject {

    public static final float WAYPOINT_TOLERANCE = 5f;

    Animation animation;
    protected String typeOfCreep;
    protected int reward;
    protected float moveSpeed;
    protected float health;
    protected float maxHealth;
    protected float stateTime;
    protected HealthBar healthBar;
    protected float slowedMoveSpeed;
    protected float savedMoveSpeed;
    private float timeSinceSlowed;

    protected Level level;
    private static LevelData.CreepType creep;

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
                    Creep.this.getHealth() / Creep.this.maxHealth * 40f, 5f);
        }
    }

    public Creep(float positionX, float positionY, Animation animation, Level level) {
        super(positionX, positionY, animation.getKeyFrames()[0]);
        this.animation = animation;
        this.level = level;
        angle = 90f;
        timeSinceSlowed = 0;
        currentWaypoint = -1;
        healthBar = new HealthBar();
    }

    public static Creep createCreep(Vector2 startingLocation, Wave wave, Level level) {
        creep = level.getLevelData().getCreep(wave.getTypeOfCreeps());
        Animation animation = null;

        switch(creep.getTypeOfCreeps()) {
            case "basicCreep":
                animation = Assets.instance.getCreep(Assets.CREEP_BASIC);
                break;
            case "slowCreep":
                animation = Assets.instance.getCreep(Assets.CREEP_SLOW);
                break;
            case "specialCreep":
                animation = Assets.instance.getCreep(Assets.CREEP_SPECIAL);
                break;
            case "bossCreep":
                animation = Assets.instance.getCreep(Assets.CREEP_BOSS);
                break;
            default:
                break;
        }

        Creep createdCreep = new Creep(startingLocation.x, startingLocation.y,
                animation, level);

        createdCreep.setHealth(creep.getHealth() * Level.getCoeff());
        createdCreep.setMoveSpeed(creep.getMoveSpeed() * Level.getCoeff());
        createdCreep.setReward(creep.getReward() * (int) Level.getCoeff());
        createdCreep.setTypeOfCreep(creep.getTypeOfCreeps());
        createdCreep.maxHealth = createdCreep.health;
        createdCreep.slowedMoveSpeed = createdCreep.moveSpeed * Projectile.SLOW_AMOUNT;
        createdCreep.savedMoveSpeed = createdCreep.moveSpeed;

        return createdCreep;
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
        return getHealth() <= 0;
    }

    public int getReward () {
        return reward;
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
            updatePosition(position.x + getMoveSpeed() * deltaTime * (float) Math.cos(radRotation),
                    position.y + getMoveSpeed() * deltaTime * (float) Math.sin(radRotation));
        }
    }

    public void receiveDamage(float damage) {
        setHealth(getHealth() - damage);
        if (getHealth() <= 0) {
            setVisible(false);
        }
    }

    public void getSlowed() {
        setMoveSpeed(slowedMoveSpeed);
        timeSinceSlowed = 0f;
    }

    public String getTypeOfCreep() {
        return typeOfCreep;
    }

    public void setTypeOfCreep(String typeOfCreep) {
        if (typeOfCreep != null) {
            this.typeOfCreep = typeOfCreep;
        }
    }

    public void setReward(int reward) {
        if (reward > 0) {
            this.reward = reward;
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

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public void updateTimeSlowed(float deltaTime) {
        timeSinceSlowed += deltaTime;

        if (timeSinceSlowed >= 3f) {
            setMoveSpeed(savedMoveSpeed);
        }
    }

    public void update(float deltaTime) {
        updateTimeSlowed(deltaTime);
        updatePosition(deltaTime);
        stateTime += deltaTime;
        texture = animation.getKeyFrame(stateTime);
        healthBar.update();
    }

    @Override
    public void render(Batch batch) {
        super.render(batch);
    }

    public void renderHealthBar(Batch batch) {
        healthBar.render(batch);
    }
}
