package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public abstract class AbstractCreep extends AbstractObject {

    Animation animation;
    float speed;
    float health;
    private float stateTime;

    int currentWaypoint;
    Array<Vector2> waypoints;

    public AbstractCreep(Vector2 position, Animation animation) {
        super(position, null);
        this.animation = animation;
        rotation = 90f;
        currentWaypoint = -1;

//        Gdx.app.debug("Creep pos", position.toString());
    }

    public void setWaypoints(Array<Vector2> waypoints) {
        this.waypoints = waypoints;
        this.currentWaypoint = 0;
    }

    private void getNextWaypoint() {
        if (++this.currentWaypoint >= waypoints.size) {
            setVisible(false);
        } else {
            Gdx.app.debug("Waypoint", waypoints.get(currentWaypoint).toString());
        }
    }

    private boolean isWaypointReached() {
        if (MathUtils.isEqual(position.x, waypoints.get(currentWaypoint).x, 1f) &&
                MathUtils.isEqual(position.y, waypoints.get(currentWaypoint).y, 1f)) {
//        if (position.equals(waypoints.get(currentWaypoint))) {
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
//            float dx = waypoints.get(currentWaypoint).x - position.x;
//            float dy = waypoints.get(currentWaypoint).y - position.y;
//
//            double radRotation = Math.atan(dy / dx);
//            if (MathUtils.isEqual(dy, 0f) && dx < 0) {
//                radRotation = Math.PI;
//            }

            double radRotation = AbstractCreep.countAngle(position, waypoints.get(currentWaypoint));
            rotation = (float) Math.toDegrees(radRotation);

            position.x += speed * deltaTime * Math.cos(radRotation);
            position.y += speed * deltaTime * Math.sin(radRotation);
        }


//        Gdx.app.debug("Rotation", "" + rotation);
//       Gdx.app.debug("Position", position.toString() + ", " +waypoints.get(currentWaypoint).toString());
    }

    public void update(float deltaTime) {
        updatePosition(deltaTime);
        stateTime += deltaTime;
        texture = animation.getKeyFrame(stateTime);
    }
}
