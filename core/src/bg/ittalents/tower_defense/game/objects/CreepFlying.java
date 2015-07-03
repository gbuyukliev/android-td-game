package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

public class CreepFlying extends AbstractCreep {

    public CreepFlying(float positionX, float positionY, Animation animation) {
        super(positionX, positionY, animation);
        award = 25 * (int) getCoeff();
        moveSpeed = 75f * getCoeff();
        health = 750f * getCoeff();
        maxHealth = 750f * getCoeff();
        isFlying = true;
    }
}
