package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import bg.ittalents.tower_defense.game.Assets;
import bg.ittalents.tower_defense.game.Level;

public class TowerSlow extends AbstractTower {

    public TowerSlow(float positionX, float positionY, TextureRegion[] textures, Level level) {
        super(positionX, positionY, textures, level);
        projectileAnimation = Assets.instance.getProjectile(Assets.PROJECTILE_ICE);
        typeOfTower = "slowTower";
        damage = 10;
        fireRate = 1f;
        range = 120f;
        price = 30;
        upgradePrice = 20;
        moneySpent = price;
    }
}