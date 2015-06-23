package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

public class Creep extends AbstractCreep {

    public Creep(float positionX, float positionY, Animation animation) {
        super(positionX, positionY, animation);
        reward = 5;
        speed = 50f;
        health = 200;
        maxHealth = 200;
    }
}
