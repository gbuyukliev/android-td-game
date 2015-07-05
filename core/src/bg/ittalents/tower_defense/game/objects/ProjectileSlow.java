package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

public class ProjectileSlow extends AbstractProjectile {

    public ProjectileSlow(float positionX, float positionY, Animation animation, AbstractTower tower) {
        super(positionX, positionY, animation, tower);
        slowAmount = 0.5f;
    }
}