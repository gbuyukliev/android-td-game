package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

import bg.ittalents.tower_defense.game.Level;

public class CreepFlying extends AbstractCreep {

    public CreepFlying(float positionX, float positionY, Animation animation) {
        super(positionX, positionY, animation);
        award = 40 * (int) Level.getCoeff();
        moveSpeed = 80f * Level.getCoeff();
        health = 140f * Level.getCoeff();
        maxHealth = 140f * Level.getCoeff();
        isFlying = true;
    }
}
