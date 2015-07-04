package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

public class CreepBoss extends AbstractCreep {

    public CreepBoss(float positionX, float positionY, Animation animation) {
        super(positionX, positionY, animation);
        award = 200 * (int) getCoeff();
        moveSpeed = 30f * getCoeff();
        health = 2000f * getCoeff();
        maxHealth = 2000f * getCoeff();
        isFlying = false;
    }
}