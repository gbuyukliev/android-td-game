package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

public class CreepBasic extends AbstractCreep {

    public CreepBasic(float positionX, float positionY, Animation animation) {
        super(positionX, positionY, animation);
        award = 15 * (int) getCoeff();
        moveSpeed = 50f * getCoeff();
        health = 180f * getCoeff();
        maxHealth = 180f * getCoeff();
        isFlying = false;
    }
}
