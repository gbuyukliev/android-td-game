package bg.ittalents.tower_defense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
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

public class Assets implements Disposable, AssetErrorListener {
    //buttons types
    public static final String UPGRADE_BUTTON_BLUE;
    public static final String UPGRADE_BUTTON_CLICKED_BLUE;
    public static final String SELL_BUTTON_BLUE;
    public static final String SELL_BUTTON_CLICKED_BLUE;
    public static final String RESUME_BUTTON;
    public static final String RESUME_BUTTON_CLICKED;
    public static final String PAUSE_BUTTON;
    public static final String PAUSE_BUTTON_CLICKED;
    public static final String FAST_FORWARD_BUTTON;
    public static final String FAST_FORWARD_BUTTON_CLICKED;

    //tower button types
    public static final String BASIC_TURRET = "basic_turret_btn";
    public static final String BASIC_TURRET_CLICKED = "basic_turret_btn_clicked";
    public static final String SLOW_TURRET = "slow_turret_btn";
    public static final String SLOW_TURRET_CLICKED = "slow_turret_btn_clicked";
    public static final String SPLASH_TURRET = "splash_turret_btn";
    public static final String SPLASH_TURRET_CLICKED = "splash_turret_btn_clicked";
    public static final String SPECIAL_TURRET = "special_turret_btn";
    public static final String SPECIAL_TURRET_CLICKED = "special_turret_btn_clicked";


    public static final String HEALTH_BAR_BACKGROUND = "enemyhealthbg";
    public static final String HEALTH_BAR = "enemyhealth";
    public static final String PROJECTILE = "projectile";

    //creep types
    public static final String CREEP_BLUE_1 = "blue1";
    public static final String CREEP_RED_1 = "red1";
    public static final String CREEP_GREEN_1 = "green1";
    public static final String CREEP_YELLOW_1 = "yellow1";
    public static final String CREEP_BLUE_2 = "blue2";
    public static final String CREEP_RED_2 = "red2";
    public static final String CREEP_GREEN_2 = "green2";
    public static final String CREEP_YELLOW_2 = "yellow2";
    public static final String CREEP_BLUE_3 = "blue3";
    public static final String CREEP_RED_3 = "red3";
    public static final String CREEP_GREEN_3 = "green3";
    public static final String CREEP_YELLOW_3 = "yellow3";
    public static final String CREEP_BOSS = "boss";

    public static final int TOWER_TYPE_SLOW = 0;
    public static final int TOWER_TYPE_BASIC = 1;
    public static final int TOWER_TYPE_3 = 2;
    public static final int TOWER_TYPE_SIEGE = 3;
    public static final int TOWER_TYPE_SPECIAL = 4;
    public static final int TOWER_TYPE_6 = 5;
    public static final int TOWER_TYPE_7 = 6;


    // Location of description file for texture atlas
    public static final String TEXTURE_ATLAS_OBJECTS;
    public static final String TAG;
    public static final Assets instance;
    public static final String SOUND_LASER = "laser";

    static {
        UPGRADE_BUTTON_BLUE ="upgrade_button_blue";
        UPGRADE_BUTTON_CLICKED_BLUE = "upgrade_button_clicked_blue";
        SELL_BUTTON_BLUE = "sell_button_blue";
        SELL_BUTTON_CLICKED_BLUE = "sell_button_clicked_blue";
        RESUME_BUTTON = "resume";
        RESUME_BUTTON_CLICKED = "resume_clicked";
        PAUSE_BUTTON = "pause";
        PAUSE_BUTTON_CLICKED = "pause_clicked";
        FAST_FORWARD_BUTTON = "fast_forward_btn";
        FAST_FORWARD_BUTTON_CLICKED = "fast_forward_btn_clicked";

        TEXTURE_ATLAS_OBJECTS = "texture/texture_atlas.txt";
        TAG = Assets.class.getName();
        instance = new Assets();
    }

    private AssetManager assetManager;

    private AssetSounds sounds;

    private AssetsTexture texture;
    private AssetCreeps creep;
    private AssetTower towers;
    private AssetFonts fonts;

    public class AssetsTexture {
        private Map<String, TextureRegion> textures;

        private AssetsTexture(TextureAtlas atlas) {
            textures = new HashMap<String, TextureRegion>();

            textures.put(UPGRADE_BUTTON_BLUE, atlas.findRegion(UPGRADE_BUTTON_BLUE));
            textures.put(UPGRADE_BUTTON_CLICKED_BLUE, atlas.findRegion(UPGRADE_BUTTON_CLICKED_BLUE));
            textures.put(SELL_BUTTON_BLUE, atlas.findRegion(SELL_BUTTON_BLUE));
            textures.put(SELL_BUTTON_CLICKED_BLUE, atlas.findRegion(SELL_BUTTON_CLICKED_BLUE));
            textures.put(RESUME_BUTTON, atlas.findRegion(RESUME_BUTTON));
            textures.put(RESUME_BUTTON_CLICKED, atlas.findRegion(RESUME_BUTTON_CLICKED));
            textures.put(PAUSE_BUTTON, atlas.findRegion(PAUSE_BUTTON));
            textures.put(PAUSE_BUTTON_CLICKED, atlas.findRegion(PAUSE_BUTTON_CLICKED));
            textures.put(FAST_FORWARD_BUTTON, atlas.findRegion(FAST_FORWARD_BUTTON));
            textures.put(FAST_FORWARD_BUTTON_CLICKED, atlas.findRegion(FAST_FORWARD_BUTTON_CLICKED));

            textures.put(BASIC_TURRET, atlas.findRegion(BASIC_TURRET));
            textures.put(BASIC_TURRET_CLICKED, atlas.findRegion(BASIC_TURRET_CLICKED));
            textures.put(SLOW_TURRET, atlas.findRegion(SLOW_TURRET));
            textures.put(SLOW_TURRET_CLICKED, atlas.findRegion(SLOW_TURRET_CLICKED));
            textures.put(SPLASH_TURRET, atlas.findRegion(SPLASH_TURRET));
            textures.put(SPLASH_TURRET_CLICKED, atlas.findRegion(SPLASH_TURRET_CLICKED));
            textures.put(SPECIAL_TURRET, atlas.findRegion(SPECIAL_TURRET));
            textures.put(SPECIAL_TURRET_CLICKED, atlas.findRegion(SPECIAL_TURRET_CLICKED));

            textures.put(HEALTH_BAR_BACKGROUND, atlas.findRegion(HEALTH_BAR_BACKGROUND));
            textures.put(HEALTH_BAR, atlas.findRegion(HEALTH_BAR));
            textures.put(PROJECTILE, atlas.findRegion(PROJECTILE));
        }

        public TextureRegion get(String type) {
            return textures.get(type);
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

        private AssetTower(TextureAtlas atlas) {
            final int TOWER_TYPES_COUNT = 7;
            final int TOWER_UPGRADES_COUNT = 3;

            tower = new AtlasRegion[TOWER_TYPES_COUNT][TOWER_UPGRADES_COUNT];

            for (int type = 1; type <= TOWER_TYPES_COUNT; type++) {
                for (int strength = 1; strength <= TOWER_UPGRADES_COUNT; strength++) {
                    tower[type - 1][strength - 1] = atlas.findRegion(
                            "turret-" + type + "-" + strength);
                }
            }
        }
    }

    public class AssetCreeps {

        private Map<String, Animation> creeps;

        private AssetCreeps(TextureAtlas atlas) {
            creeps = new HashMap<>();

            creeps.put(CREEP_BLUE_1, init(atlas, "blue", 1, 6));
            creeps.put(CREEP_RED_1, init(atlas, "red", 1, 6));
            creeps.put(CREEP_GREEN_1, init(atlas, "green", 1, 6));
            creeps.put(CREEP_YELLOW_1, init(atlas, "yellow", 1, 6));

            creeps.put(CREEP_BLUE_2, init(atlas, "blue", 2, 4));
            creeps.put(CREEP_RED_2, init(atlas, "red", 2, 4));
            creeps.put(CREEP_GREEN_2, init(atlas, "green", 2, 4));
            creeps.put(CREEP_YELLOW_2, init(atlas, "yellow", 2, 4));

            creeps.put(CREEP_BLUE_3, init(atlas, "blue", 3, 4));
            creeps.put(CREEP_RED_3, init(atlas, "red", 3, 4));
            creeps.put(CREEP_GREEN_3, init(atlas, "green", 3, 4));
            creeps.put(CREEP_YELLOW_3, init(atlas, "yellow", 3, 4));

            creeps.put(CREEP_BOSS, initBoss(atlas));
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

    public class AssetSounds implements Disposable {

        private Map<String, Sound> sounds;

        public AssetSounds() {
            sounds = new HashMap<String, Sound>();
            assetManager.load("sounds/laser.wav", Sound.class);

            assetManager.finishLoading();

            sounds.put(SOUND_LASER, assetManager.get("sounds/laser.wav", Sound.class));
        }

        public Sound get(String key) {
            return sounds.get(key);
        }

        @Override
        public void dispose() {
            for (String key : sounds.keySet()) {
                sounds.get(key).dispose();
            }
        }
    }

    // singleton: prevent instantiation from other classes
    private Assets() {

    }

    public BitmapFont getFont() {
        return fonts.defaultFont;
    }

    public Animation getCreep(String type) {
        return creep.get(type);
    }

    public TextureRegion getTexture(String type) {
        return texture.get(type);
    }

    public Sound getSound(String name) {
        return sounds.get(name);
    }


    public TextureRegion[] getTower(int type) {
        return towers.tower[type];
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
        texture = new AssetsTexture(atlas);
        fonts = new AssetFonts();
        creep = new AssetCreeps(atlas);
        towers = new AssetTower(atlas);
        sounds = new AssetSounds();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        fonts.defaultFont.dispose();
        sounds.dispose();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" +
                asset.fileName + "'", throwable);
    }
}
