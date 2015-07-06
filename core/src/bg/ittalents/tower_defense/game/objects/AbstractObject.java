package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractObject {
//    position of center
    Vector2 position;

    float angle;
    boolean visible;
    TextureRegion texture;

    public AbstractObject(float positionX, float positionY, TextureRegion texture) {
        position = new Vector2(positionX, positionY);
        this.texture = texture;
        visible = true;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public static double countAngle(Vector2 position, Vector2 targetPosition) {
        return Math.atan2(targetPosition.y - position.y, targetPosition.x - position.x);
    }

    protected float getTextureX() {
        return position.x - texture.getRegionWidth() / 2;
    }


    protected float getTextureY() {
        return position.y - texture.getRegionHeight() / 2;
    }

    protected void updatePosition(float x, float y) {
        position.x = x;
        position.y = y;
    }

    public void render(Batch batch) {
        batch.draw(texture, getTextureX(), getTextureY(),
                texture.getRegionWidth() / 2, texture.getRegionWidth() / 2,
                texture.getRegionWidth(), texture.getRegionHeight(), 1f, 1f, angle);
    }
}
