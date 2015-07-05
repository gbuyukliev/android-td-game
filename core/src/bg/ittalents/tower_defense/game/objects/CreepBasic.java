package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

import bg.ittalents.tower_defense.game.Level;

public class CreepBasic extends AbstractCreep {

    public CreepBasic(float positionX, float positionY, Animation animation) {
        super(positionX, positionY, animation);
        typeOfCreep = "basicCreep";
        reward = 25 * (int) Level.getCoeff();
        moveSpeed = 50f * Level.getCoeff();
        health = 180f * Level.getCoeff();
        maxHealth = 180f * Level.getCoeff();
        savedMoveSpeed = moveSpeed;
    }
}
