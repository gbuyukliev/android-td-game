package bg.ittalents.tower_defense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;

import bg.ittalents.tower_defense.game.WorldRenderer;

public class Assets implements Disposable, AssetErrorListener {
    // Location of description file for texture atlas
    public static final String TEXTURE_ATLAS_OBJECTS = "texture/texture.pack";

    public static final String TAG = Assets.class.getName();

    public static final Assets instance = new Assets();

    private AssetManager assetManager;

    public AssetCreeps creep;
    public AssetTower towers;
    public AssetBackground background;
    public AssetFonts fonts;

    public class AssetBackground {
        public final AtlasRegion background;

        public AssetBackground(TextureAtlas atlas) {
            background = atlas.findRegion("tower-defense-background-stars");
        }
    }

    public class AssetFonts {
        public final BitmapFont defaultFont;

        public AssetFonts() {

            // how much bigger is the real device screen, compared to the defined viewport
            float scale = Gdx.graphics.getHeight() / WorldRenderer.VIEWPORT ;

            // prevents unwanted downscale on devices with resolution SMALLER than 320x480
            if (scale < 1)
                scale = 1;

            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/Roboto-Bold.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            fontParameter.size = (int) (12 * scale);
            fontParameter.flip = true;
            // 12 is the size i want to give for the font on all devices
            // bigger font textures = better results
            defaultFont = generator.generateFont(fontParameter);

            //Apply Linear filtering; best choice to keep everything looking sharp
            defaultFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
    }

    public class AssetTower {
        public final AtlasRegion[][] tower;

        public AssetTower(TextureAtlas atlas) {
            tower = new AtlasRegion[7][3];

            for (int type = 1; type <= 7; type++) {
                for (int strength = 1; strength <= 3; strength++) {
                    tower[type - 1][strength - 1] = atlas.findRegion(
                            "turret-" + type + "-" + strength);
                }
            }
        }
    }

    public class AssetCreeps {

        public final Animation creep1blue;
        public final Animation creep1green;
        public final Animation creep1red;
        public final Animation creep1yellow;

        public final Animation creep2blue;
        public final Animation creep2green;
        public final Animation creep2red;
        public final Animation creep2yellow;

        public final Animation creep3blue;
        public final Animation creep3green;
        public final Animation creep3red;
        public final Animation creep3yellow;

        public AssetCreeps(TextureAtlas atlas) {
            this.creep1blue = init(atlas, "blue", 1);
            this.creep1green = init(atlas, "green", 1);
            this.creep1red = init(atlas, "red", 1);
            this.creep1yellow = init(atlas, "yellow", 1);

            this.creep2blue = init(atlas, "blue", 3);
            this.creep2green = init(atlas, "green", 3);
            this.creep2red = init(atlas, "red", 3);
            this.creep2yellow = init(atlas, "yellow", 3);

            this.creep3blue = init(atlas, "blue", 3);
            this.creep3green = init(atlas, "green", 3);
            this.creep3red = init(atlas, "red", 3);
            this.creep3yellow = init(atlas, "yellow", 3);
        }

        private Animation init(TextureAtlas atlas, String creepColor, int creepNumber) {
            String path = "creep-" + creepNumber + "-" + creepColor + "/";

            int frameCount = 4;

            if (creepNumber == 1) {
                frameCount = 6;
            }

            TextureRegion[] frames = new TextureRegion[frameCount];

            for (int frameIndex = 1; frameIndex <= frameCount; frameIndex++) {
                frames[frameIndex - 1] = atlas.findRegion(path + frameIndex);
            }

            Animation anim = new Animation(1f / frameCount, frames);
            anim.setPlayMode(PlayMode.LOOP);
            return anim;
        }
    }

    // singleton: prevent instantiation from other classes
    private Assets() {
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        // set asset manager error handler
        assetManager.setErrorListener(this);
        // load texture atlas
        assetManager.load(Assets.TEXTURE_ATLAS_OBJECTS,
                TextureAtlas.class);
        // start loading assets and wait until finished
        assetManager.finishLoading();
        Gdx.app.debug(TAG, "# of assets loaded: "
                + assetManager.getAssetNames().size);
        for (String a : assetManager.getAssetNames())
            Gdx.app.debug(TAG, "asset: " + a);

        TextureAtlas atlas =
                assetManager.get(Assets.TEXTURE_ATLAS_OBJECTS);
        // enable texture filtering for pixel smoothing
        for (Texture t : atlas.getTextures()) {
            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        // create game resource objects
        fonts = new AssetFonts();
        background = new AssetBackground(atlas);
        creep = new AssetCreeps(atlas);
        towers = new AssetTower(atlas);
    }


    @Override
    public void dispose() {
        assetManager.dispose();
        fonts.defaultFont.dispose();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" +
                asset.fileName + "'", throwable);
    }
}
