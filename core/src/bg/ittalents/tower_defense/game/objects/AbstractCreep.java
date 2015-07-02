package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;

public abstract class AbstractCreep extends AbstractObject {

    private static final Texture ENEMY_BG_TEXTURE;
    private static final Texture ENEMY_FG_TEXTURE;
    private static final Texture ENEMY_FG;

    private static float waveCoeff;

    static {
        ENEMY_BG_TEXTURE = new Texture("enemyhealthbg.png");
        ENEMY_FG_TEXTURE = new Texture("enemyhealthfg.png");
        ENEMY_FG = new Texture(Gdx.files.internal("enemyhealth.png"));
        waveCoeff = 1f;
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
        private Sprite healthBarFG;
        NinePatchDrawable health;
        private AbstractCreep owner;

        public HealthBar(AbstractCreep owner, Texture healthBG, Texture healthFG) {
            this.owner = owner;
            healthBarBG = new Sprite(healthBG);
            healthBarFG = new Sprite(healthFG);
            health = new NinePatchDrawable(new NinePatch(ENEMY_FG, 1, 1, 1, 1));

            setPosition();
            healthBarBG.setOrigin(0, 0);
            healthBarFG.setOrigin(0, 0);
            healthBarBG.setScale(SCALE);
        }

        private void setPosition() {
            healthBarBG.setX(texturePosition.x + BUFFER_X);
            healthBarBG.setY(texturePosition.y + BUFFER_Y);
            healthBarFG.setX(texturePosition.x + BUFFER_X);
            healthBarFG.setY(texturePosition.y + BUFFER_Y);
        }

        public void update() {
            setPosition();
            healthBarFG.setScale(owner.health / owner.maxHealth * SCALE, SCALE);
        }

        public void render(Batch batch) {
            healthBarBG.draw(batch);
            health.draw(batch, texturePosition.x + BUFFER_X, texturePosition.y + BUFFER_Y, owner.health / owner.maxHealth * 40f, 5f);
//            healthBarFG.draw(batch);
        }
    }

    public AbstractCreep(float positionX, float positionY, Animation animation) {
        super(positionX, positionY, animation.getKeyFrames()[0]);
        this.animation = animation;
        angle = 90f;
        currentWaypoint = -1;

        healthBar = new HealthBar(this, ENEMY_BG_TEXTURE,
                ENEMY_FG_TEXTURE);
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
        if (MathUtils.isEqual(position.x, waypoints.get(currentWaypoint).x, 1f) &&
                MathUtils.isEqual(position.y, waypoints.get(currentWaypoint).y, 1f)) {
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

    public static void setCoeff(float newWaveCoeff) {
        if (newWaveCoeff > 0) {
            waveCoeff = newWaveCoeff;
        }
    }

    public static float getCoeff() {
        return waveCoeff;
    }
}
