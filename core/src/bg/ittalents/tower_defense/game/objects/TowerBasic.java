package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import bg.ittalents.tower_defense.game.Assets;
import bg.ittalents.tower_defense.game.Level;

public class TowerBasic extends AbstractTower {

    public TowerBasic(float positionX, float positionY, TextureRegion[] textures, Level level) {
        super(positionX, positionY, textures, level);
        typeOfTower = "basicTower";
        projectileAnimation = Assets.instance.getProjectile(Assets.PROJECTILE_GRAY);
        damage = 20;
        fireRate = 1f;
        range = 120f;
        price = 50;
        upgradePrice = 30;
        moneySpent = price;
    }
}
