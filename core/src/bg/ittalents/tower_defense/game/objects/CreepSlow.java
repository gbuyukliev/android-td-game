package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

import bg.ittalents.tower_defense.game.Level;

public class CreepSlow extends AbstractCreep {

    public CreepSlow(float positionX, float positionY, Animation animation) {
        super(positionX, positionY, animation);
        award = 60 * (int) Level.getCoeff();
        moveSpeed = 40f * Level.getCoeff();
        health = 600f * Level.getCoeff();
        maxHealth = 600f * Level.getCoeff();
        isFlying = false;
    }
}