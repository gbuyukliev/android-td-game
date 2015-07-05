package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

import bg.ittalents.tower_defense.game.Level;

public class CreepBasic extends AbstractCreep {

    public CreepBasic(float positionX, float positionY, Animation animation) {
        super(positionX, positionY, animation);
        setTypeOfCreep("basicCreep");
        setReward(25 * (int) Level.getCoeff());
        setMoveSpeed(50f * Level.getCoeff());
        setHealth(180f * Level.getCoeff());
        maxHealth = health;
        slowedMoveSpeed = moveSpeed * Projectile.SLOW_AMOUNT;
        savedMoveSpeed = moveSpeed;
    }
}
