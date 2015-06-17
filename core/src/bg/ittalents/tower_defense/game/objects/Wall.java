package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Wall extends AbstractObject {
    public static TextureRegion wallTexture = new TextureRegion(Assets.instance.wall.center);

    public Wall(Vector2 position) {
        super(position, wallTexture);
    }
}
