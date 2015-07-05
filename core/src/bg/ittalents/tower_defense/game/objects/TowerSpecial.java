package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import bg.ittalents.tower_defense.game.Level;

public class TowerSpecial extends AbstractTower {

    public TowerSpecial(float positionX, float positionY, TextureRegion[] textures, Level level) {
        super(positionX, positionY, textures, level);
        typeOfTower = "specialTower";
        damage = 30;
        fireRate = 0.9f;
        range = 170f;
        price = 100;
        upgradePrice = 50;
        moneySpent = price;
    }
}