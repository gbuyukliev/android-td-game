package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

public class Creep extends AbstractCreep {

    public Creep(Vector2 position, Animation animation) {
        super(position, animation);
        speed = 50f;
    }
}
