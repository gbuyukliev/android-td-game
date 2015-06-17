package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Tower extends AbstractTower {

    public Tower(Vector2 position, TextureRegion[] textures) {
        super(position, textures);

        range = 100f;
    }
}
