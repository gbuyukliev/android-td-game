package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

import bg.ittalents.tower_defense.game.Level;

public class CreepSpecial extends AbstractCreep {

    public CreepSpecial(float positionX, float positionY, Animation animation) {
        super(positionX, positionY, animation);
        setTypeOfCreep("specialCreep");
        setReward(40 * (int) Level.getCoeff());
        setMoveSpeed(80f * Level.getCoeff());
        setHealth(140f * Level.getCoeff());
        maxHealth = health;
        slowedMoveSpeed = moveSpeed * Projectile.SLOW_AMOUNT;
        savedMoveSpeed = moveSpeed;
    }
}
