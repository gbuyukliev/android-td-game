package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

public class CreepFlying extends AbstractCreep {

    public CreepFlying(float positionX, float positionY, Animation animation) {
        super(positionX, positionY, animation);
        award = 40 * (int) getCoeff();
        moveSpeed = 80f * getCoeff();
        health = 140f * getCoeff();
        maxHealth = 140f * getCoeff();
        isFlying = true;
    }
}
