package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

import bg.ittalents.tower_defense.game.Level;

public class CreepSlow extends AbstractCreep {

    public CreepSlow(float positionX, float positionY, Animation animation) {
        super(positionX, positionY, animation);
        setTypeOfCreep("slowCreep");
        setReward(60 * (int) Level.getCoeff());
        setMoveSpeed(40f * Level.getCoeff());
        setHealth(600f * Level.getCoeff());
        maxHealth = health;
        slowedMoveSpeed = moveSpeed * Projectile.SLOW_AMOUNT;
        savedMoveSpeed = moveSpeed;
    }
}