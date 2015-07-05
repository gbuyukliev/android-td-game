package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

import bg.ittalents.tower_defense.game.Level;

public class CreepBoss extends AbstractCreep {

    public CreepBoss(float positionX, float positionY, Animation animation) {
        super(positionX, positionY, animation);
        typeOfCreep = "bossCreep";
        reward = 200 * (int) Level.getCoeff();
        moveSpeed = 30f * Level.getCoeff();
        health = 2000f * Level.getCoeff();
        maxHealth = 2000f * Level.getCoeff();
        slowedMoveSpeed = moveSpeed * SLOWED_MOVESPEED_COEFF;
        savedMoveSpeed = moveSpeed;
    }
}