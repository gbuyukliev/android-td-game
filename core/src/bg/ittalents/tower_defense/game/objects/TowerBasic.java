package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import bg.ittalents.tower_defense.game.Level;

public class TowerBasic extends AbstractTower {

    public TowerBasic(float positionX, float positionY, TextureRegion[] textures, Level level) {
        super(positionX, positionY, textures, level);

        timeFromLastShot = Float.MAX_VALUE;
        typeOfTower = "basicTower";
        damage = 20;
        attackSpeed = 1f;
        range = 120f;
        rotationSpeed = 90f;
        price = 50;
        moneySpent = price;
        isUpgradable = true;
        upgradePrice = 30;
    }
}
