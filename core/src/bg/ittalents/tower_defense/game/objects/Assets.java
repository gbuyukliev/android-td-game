package bg.ittalents.tower_defense.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

import bg.ittalents.tower_defense.utils.Constants;

public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();

    public static final Assets instance = new Assets();

    private AssetManager assetManager;
    public AssetWall wall;
    public AssetCreeps creep;
    public AssetTower towers;

    public class AssetWall {
        public final AtlasRegion corner;
        public final AtlasRegion middle;
        public final AtlasRegion center;

        public AssetWall(TextureAtlas atlas) {
            corner = atlas.findRegion("tiles/tile-1-corner-left-bottom");
            middle = atlas.findRegion("tiles/tile-1-horizontal");
            center = atlas.findRegion("tiles/tile-1-center");
        }
    }

    public class AssetTower {
        public AtlasRegion[][] tower;

        public AssetTower(TextureAtlas atlas) {
            tower = new AtlasRegion[7][3];

            for (int type = 1; type <= 7; type++) {
                for (int strength = 1; strength <= 3; strength++) {
                    tower[type - 1][strength - 1] = atlas.findRegion(
                            "tower-defense-turrets/turret-" + type + "-" + strength);
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
            String path = "creep/creep-" + creepNumber + "-" + creepColor + "/";

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
        assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS,
                TextureAtlas.class);
        // start loading assets and wait until finished
        assetManager.finishLoading();
        Gdx.app.debug(TAG, "# of assets loaded: "
                + assetManager.getAssetNames().size);
        for (String a : assetManager.getAssetNames())
            Gdx.app.debug(TAG, "asset: " + a);

        TextureAtlas atlas =
                assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
        // enable texture filtering for pixel smoothing
        for (Texture t : atlas.getTextures()) {
            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        // create game resource objects
        wall = new AssetWall(atlas);
        creep = new AssetCreeps(atlas);
        towers = new AssetTower(atlas);
    }


    @Override
    public void dispose() {
        assetManager.dispose();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" +
                asset.fileName + "'", throwable);
    }
}
