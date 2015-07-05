package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

import bg.ittalents.tower_defense.game.Level;

public class CreepSlow extends AbstractCreep {

    public CreepSlow(float positionX, float positionY, Animation animation) {
        super(positionX, positionY, animation);
        typeOfCreep = "slowCreep";
        reward = 60 * (int) Level.getCoeff();
        moveSpeed = 40f * Level.getCoeff();
        health = 600f * Level.getCoeff();
        maxHealth = 600f * Level.getCoeff();
        slowedMoveSpeed = moveSpeed * SLOWED_MOVESPEED_COEFF;
        savedMoveSpeed = moveSpeed;
    }
}