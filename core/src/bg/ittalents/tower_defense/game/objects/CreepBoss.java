package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

public class CreepBoss extends AbstractCreep {

    public CreepBoss(float positionX, float positionY, Animation animation) {
        super(positionX, positionY, animation);
        award = 100 * (int) getCoeff();
        moveSpeed = 30f * getCoeff();
        health = 170f * getCoeff();
        maxHealth = 170f * getCoeff();
        isFlying = false;
    }
}