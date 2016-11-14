package com.djdarkside.box2dapp;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.djdarkside.box2dapp.screens.*;
import com.djdarkside.box2dapp.utils.Constants;

public class Application extends Game {

    public OrthographicCamera camera;
    public SpriteBatch batch;
    public static BitmapFont font;
    public AssetManager manager;

    public LoadingScreen lStage;
    public MainMenu mStage;
    public PlayStage pStage;
    public TestStage tStage;
    public TestStage2 tStage2;
    public GameOver gOver;

    @Override
	public void create () {
        manager = new AssetManager();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.V_WIDTH / Constants.SCALE, Constants.V_HEIGHT / Constants.SCALE);
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        lStage = new LoadingScreen(this);
        mStage = new MainMenu(this);
        pStage = new PlayStage(this);
        tStage = new TestStage(this);
        tStage2 = new TestStage2(this);
        gOver = new GameOver(this);
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