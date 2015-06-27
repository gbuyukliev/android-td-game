package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractObject {
    //position of center
    Vector2 position;
    //position to render
    Vector2 texturePosition;
    float angle;
    boolean visible;
    TextureRegion texture;

    public AbstractObject(float positionX, float positionY, TextureRegion texture) {
        position = new Vector2(positionX, positionY);
        texturePosition = new Vector2(positionX - (texture.getRegionWidth() / 2),
                positionY - (texture.getRegionHeight() / 2));
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

    protected void updatePosition(float x, float y) {
        position.x = x;
        position.y = y;
        texturePosition.x = x - texture.getRegionWidth() / 2;
        texturePosition.y = y - texture.getRegionHeight() / 2;
    }

    public void render(Batch batch) {
        batch.draw(texture, texturePosition.x, texturePosition.y,
                texture.getRegionWidth() / 2, texture.getRegionWidth() / 2,
                texture.getRegionWidth(), texture.getRegionHeight(), 1f, 1f, angle);
    }
}
