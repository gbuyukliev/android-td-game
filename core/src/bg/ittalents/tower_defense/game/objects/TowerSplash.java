package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import bg.ittalents.tower_defense.game.Level;

public class TowerSplash extends AbstractTower {

    public TowerSplash(float positionX, float positionY, TextureRegion[] textures, Level level) {
        super(positionX, positionY, textures, level);
        typeOfTower = "splashTower";
        damage = 40;
        fireRate = 1.6f;
        range = 100f;
        price = 80;
        upgradePrice = 40;
        moneySpent = price;
    }
}