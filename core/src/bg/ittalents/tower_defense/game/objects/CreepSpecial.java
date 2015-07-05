package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

import bg.ittalents.tower_defense.game.Level;

public class CreepSpecial extends AbstractCreep {

    public CreepSpecial(float positionX, float positionY, Animation animation) {
        super(positionX, positionY, animation);
        typeOfCreep = "specialCreep";
        reward = 40 * (int) Level.getCoeff();
        moveSpeed = 80f * Level.getCoeff();
        health = 140f * Level.getCoeff();
        maxHealth = 140f * Level.getCoeff();
        slowedMoveSpeed = moveSpeed * SLOWED_MOVESPEED_COEFF;
        savedMoveSpeed = moveSpeed;
    }
}
