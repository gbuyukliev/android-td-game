package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

public class CreepFlying extends AbstractCreep {

    public CreepFlying(float positionX, float positionY, Animation animation) {
        super(positionX, positionY, animation);
        reward = 25 * (int) getCoeff();
        speed = 90f * getCoeff();
        health = 750f * getCoeff();
        maxHealth = 750f * getCoeff();
        isFlying = true;
    }
}
