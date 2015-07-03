package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import bg.ittalents.tower_defense.game.Assets;

public class Background {

    private TextureRegion texture;
    private int width;
    private int height;

    public Background(int levelWidth, int levelHeight) {
        texture = new TextureRegion(new Texture(Gdx.files.internal("galaxy_background_4.jpg")));
        width = levelWidth;
        height = levelHeight;
    }

    public void render(Batch batch) {
        for (int xPosition = 0; xPosition <= width; xPosition += texture.getRegionWidth()) {
            for (int yPosition = 0; yPosition <= height; yPosition += texture.getRegionHeight()) {
                batch.draw(texture, xPosition, yPosition);
            }
        }
    }
}
