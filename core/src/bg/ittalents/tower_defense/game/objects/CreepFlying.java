package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

public class CreepFlying extends AbstractCreep {

    public CreepFlying(float positionX, float positionY, Animation animation) {
        super(positionX, positionY, animation);
        reward = 7 * (int) getCoeff();
        speed = 90f * getCoeff();
        health = 100f * getCoeff();
        maxHealth = 100f * getCoeff();
        isFlying = true;
    }
}
