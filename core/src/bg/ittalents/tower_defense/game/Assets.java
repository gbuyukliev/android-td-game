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

import java.util.HashMap;
import java.util.Map;

import bg.ittalents.tower_defense.game.WorldRenderer;

public class Assets implements Disposable, AssetErrorListener {
    public static final Texture UPGRADE_BUTTON_BLUE;
    public static final Texture UPGRADE_BUTTON_CLICKED_BLUE;
    public static final Texture SELL_BUTTON_BLUE;
    public static final Texture SELL_BUTTON_CLICKED_BLUE;

    public static final Texture RESUME_BUTTON;
    public static final Texture RESUME_BUTTON_CLICKED;
    public static final Texture PAUSE_BUTTON;
    public static final Texture PAUSE_BUTTON_CLICKED;

    public static final Texture FAST_FORWARD_BUTTON;
    public static final Texture FAST_FORWARD_BUTTON_CLICKED;

    // Location of description file for texture atlas
    public static final String TEXTURE_ATLAS_OBJECTS;
    public static final String TAG;
    public static final Assets instance;

    static {
        UPGRADE_BUTTON_BLUE = new Texture("upgrade_button_blue.png");
        UPGRADE_BUTTON_CLICKED_BLUE = new Texture("upgrade_button_clicked_blue.png");
        SELL_BUTTON_BLUE = new Texture("sell_button_blue.png");
        SELL_BUTTON_CLICKED_BLUE = new Texture("sell_button_clicked_blue.png");

        RESUME_BUTTON = new Texture("resume.png");
        RESUME_BUTTON_CLICKED = new Texture("resume_clicked.png");
        PAUSE_BUTTON = new Texture("pause.png");
        PAUSE_BUTTON_CLICKED = new Texture("pause_clicked.png");

        TEXTURE_ATLAS_OBJECTS = "texture/texture_atlas.txt";
        FAST_FORWARD_BUTTON = new Texture("fast_forward_btn.png");
        FAST_FORWARD_BUTTON_CLICKED = new Texture("fast_forward_btn_clicked.png");
        TAG = Assets.class.getName();
        instance = new Assets();
    }

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
            float scale;

            if (Gdx.graphics.getHeight() > Gdx.graphics.getWidth()) {
                scale = Gdx.graphics.getWidth() / WorldRenderer.VIEWPORT ;
            } else {
                scale = Gdx.graphics.getHeight() / WorldRenderer.VIEWPORT ;
            }

            // prevents unwanted downscale on devices with resolution SMALLER than 320x480
            if (scale < 1)
                scale = 1;

            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/batmfa__.ttf"));
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

        private Map<String, Animation> creeps;

        public AssetCreeps(TextureAtlas atlas) {
            creeps = new HashMap<String, Animation>();

            creeps.put("blue1", init(atlas, "blue", 1, 6));
            creeps.put("red1", init(atlas, "red", 1, 6));
            creeps.put("green1", init(atlas, "green", 1, 6));
            creeps.put("yellow1", init(atlas, "yellow", 1, 6));

            creeps.put("blue2", init(atlas, "blue", 2, 4));
            creeps.put("red2", init(atlas, "red", 2, 4));
            creeps.put("green2", init(atlas, "green", 2, 4));
            creeps.put("yellow2", init(atlas, "yellow", 2, 4));

            creeps.put("blue3", init(atlas, "blue", 3, 4));
            creeps.put("red3", init(atlas, "red", 3, 4));
            creeps.put("green3", init(atlas, "green", 3, 4));
            creeps.put("yellow3", init(atlas, "yellow", 3, 4));

            creeps.put("boss", initBoss(atlas));
        }

        private Animation initBoss(TextureAtlas atlas) {
            String path = "boss-";
            int frameCount = 4;

            TextureRegion[] frames = new TextureRegion[frameCount];

            for (int frameIndex = 1; frameIndex <= frameCount; frameIndex++) {
                frames[frameIndex - 1] = atlas.findRegion(path + frameIndex);
            }

            Animation anim = new Animation(1f / frameCount, frames);
            anim.setPlayMode(PlayMode.LOOP);
            return anim;
        }

        private Animation init(TextureAtlas atlas, String creepColor, int creepNumber, int frameCount) {
            String path = "creep-" + creepNumber + "-" + creepColor + "/";

            TextureRegion[] frames = new TextureRegion[frameCount];

            for (int frameIndex = 1; frameIndex <= frameCount; frameIndex++) {
                frames[frameIndex - 1] = atlas.findRegion(path + frameIndex);
            }

            Animation anim = new Animation(1f / frameCount, frames);
            anim.setPlayMode(PlayMode.LOOP);
            return anim;
        }

        public Animation get(String type) {
            return creeps.get(type);
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
