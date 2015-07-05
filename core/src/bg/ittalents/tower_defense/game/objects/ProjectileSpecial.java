package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

public class ProjectileSpecial extends AbstractProjectile {

    public ProjectileSpecial(float positionX, float positionY, Animation animation, AbstractTower tower) {
        super(positionX, positionY, animation, tower);
        isSpecial = true;
    }
}