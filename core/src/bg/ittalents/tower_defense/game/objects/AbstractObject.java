package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import bg.ittalents.tower_defense.utils.Constants;

public abstract class AbstractObject {
    Vector2 position;
    TextureRegion texture;
    float rotation;
    boolean visible;

    public AbstractObject(Vector2 position, TextureRegion texture) {
        this.position = new Vector2(position);
        this.texture = texture;
        visible = true;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

//    public void setRotation(float rotation) {
//        this.rotation = rotation;
//    }

    public static double countAngle(Vector2 position, Vector2 point) {
        return Math.atan2(point.y - position.y, point.x - position.x);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y,
                Constants.TILE_WIDTH / 2, Constants.TILE_HEIGHT / 2,
                Constants.TILE_WIDTH, Constants.TILE_HEIGHT, 1f, 1f, rotation);
    }
}
