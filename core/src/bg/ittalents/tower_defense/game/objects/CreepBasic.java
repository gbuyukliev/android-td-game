package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

public class CreepBasic extends AbstractCreep {

    public CreepBasic(float positionX, float positionY, Animation animation) {
        super(positionX, positionY, animation);
        award = 20 * (int) getCoeff();
        moveSpeed = 50f * getCoeff();
        health = 150f * getCoeff();
        maxHealth = 150f * getCoeff();
        isFlying = false;
    }
}
