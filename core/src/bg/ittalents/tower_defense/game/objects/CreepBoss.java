package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

import bg.ittalents.tower_defense.game.Level;

public class CreepBoss extends AbstractCreep {

    public CreepBoss(float positionX, float positionY, Animation animation) {
        super(positionX, positionY, animation);
        setTypeOfCreep("bossCreep");
        setReward(200 * (int) Level.getCoeff());
        setMoveSpeed(30f * Level.getCoeff());
        setHealth(2000f * Level.getCoeff());
        maxHealth = health;
        slowedMoveSpeed = moveSpeed * Projectile.SLOW_AMOUNT;
        savedMoveSpeed = moveSpeed;
    }
}