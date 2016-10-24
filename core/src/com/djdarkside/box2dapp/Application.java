package com.djdarkside.box2dapp;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.djdarkside.box2dapp.screens.LoadingScreen;
import com.djdarkside.box2dapp.screens.MainMenu;
import com.djdarkside.box2dapp.screens.PlayStage;
import com.djdarkside.box2dapp.utils.Constants;

public class Application extends Game {

    public PlayStage pStage;
    public LoadingScreen lStage;
    public MainMenu mStage;

    public OrthographicCamera camera;
    public SpriteBatch batch;
    public static BitmapFont font;
    public AssetManager manager;

	@Override
	public void create () {
        manager = new AssetManager();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.V_WIDTH / Constants.SCALE, Constants.V_HEIGHT / Constants.SCALE);
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        pStage = new PlayStage(this);
        lStage = new LoadingScreen(this);
        mStage = new MainMenu(this);
        this.setScreen(lStage);
	}

	@Override
	public void render () {
        super.render();
	}

    @Override
	public void resize(int width, int height) {
        //camera.setToOrtho(false, width / Constants.SCALE, height / Constants.SCALE);
    }
	
	@Override
	public void dispose () {
        manager.dispose();
        batch.dispose();
        font.dispose();
	}

	public SpriteBatch getBatch() {
        return batch;
    }
}