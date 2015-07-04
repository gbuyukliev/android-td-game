package bg.ittalents.tower_defense.game.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class MyButton extends ImageButton
{
    public MyButton(TextureRegion texture_up, TextureRegion texture_down)
    {
        super(new SpriteDrawable(new Sprite(texture_up)),
                new SpriteDrawable(new Sprite(texture_down)));
    }

    public MyButton(TextureRegion texture_up, TextureRegion texture_down, TextureRegion background)
    {
        this(texture_up, texture_down);
        this.setBackground(new SpriteDrawable(new Sprite(background)));
    }
}