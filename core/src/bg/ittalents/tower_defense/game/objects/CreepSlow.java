package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

public class CreepSlow extends AbstractCreep {

    public CreepSlow(float positionX, float positionY, Animation animation) {
        super(positionX, positionY, animation);
        award = 60 * (int) getCoeff();
        moveSpeed = 40f * getCoeff();
        health = 600f * getCoeff();
        maxHealth = 600f * getCoeff();
        isFlying = false;
    }
}