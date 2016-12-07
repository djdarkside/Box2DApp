package com.djdarkside.box2dapp.input;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

//NOT BEING USED

public class Gamepad {//implements ControllerListener {
/*
	SpriteBatch batch;
   Sprite sprite;
   BitmapFont font;
   boolean hasControllers = true;
   String message = "Please install a controller";


   public void Gamepad () {
	  batch = new SpriteBatch();
	  sprite = new Sprite(new Texture("badlogic.jpg"));
	   sprite.setPosition(Gdx.graphics.getWidth()/2 -sprite.getWidth()/2,
						   Gdx.graphics.getHeight()/2-sprite.getHeight()/2);

		// Listen to all controllers, not just one
		Controllers.addListener(this);

		font = new BitmapFont();
		font.setColor(Color.WHITE);


		if(Controllers.getControllers().size == 0)
		{
			hasControllers = false;
		}
	}

   public void update(float delta) {
	   Gdx.graphics.getDeltaTime();
	   buttonDown();
   }


   public void render () {
	  Gdx.gl.glClearColor(0, 0, 0, 0);
	  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	  batch.begin();
		  batch.draw(sprite, sprite.getX(), sprite.getY(),sprite.getOriginX(),sprite.getOriginY(),
					sprite.getWidth(),sprite.getHeight(),
					sprite.getScaleX(),sprite.getScaleY(),sprite.getRotation());
	  batch.end();
   }

	// connected and disconnect dont actually appear to work for XBox 360 controllers.
	public void connected(Controller controller) {
		hasControllers = true;
	}

	public void disconnected(Controller controller) {
		hasControllers = false;
	}

	public boolean buttonDown(Controller controller, int buttonCode) {
		if(buttonCode == XBox360Pad.BUTTON_Y)
			sprite.setY(sprite.getY() + 1);
		if(buttonCode == XBox360Pad.BUTTON_A)
			sprite.setY(sprite.getY()-1);
		if(buttonCode == XBox360Pad.BUTTON_X)
			sprite.setX(sprite.getX() - 1);
		if(buttonCode == XBox360Pad.BUTTON_B)
			sprite.setX(sprite.getX() + 1);

		if(buttonCode == XBox360Pad.BUTTON_LB)
			sprite.scale(-0.1f);
		if(buttonCode == XBox360Pad.BUTTON_RB)
			sprite.scale(0.1f);
		return false;
	}

	public boolean buttonUp(Controller controller, int buttonCode) {
		return false;
	}

	public boolean axisMoved(Controller controller, int axisCode, float value) {
		// This is your analog stick
		// Value will be from -1 to 1 depending how far left/right, up/down the stick is
		// For the Y translation, I use a negative because I like inverted analog stick
		// Like all normal people do! ;)

		// Left Stick
		if(axisCode == XBox360Pad.AXIS_LX)
			sprite.translateX(10f * value);
		if(axisCode == XBox360Pad.AXIS_LY)
			sprite.translateY(-10f * value);

		// Right stick
		if(axisCode == XBox360Pad.AXIS_RX)
			sprite.rotate(10f * value);
		return false;
	}

	@Override
	public boolean povMoved(Controller controller, int povCode, PovDirection value) {
		// This is the dpad
		if(value == XBox360Pad.BUTTON_DPAD_LEFT)
			sprite.translateX(-10f);
		if(value == XBox360Pad.BUTTON_DPAD_RIGHT)
			sprite.translateX(10f);
		if(value == XBox360Pad.BUTTON_DPAD_UP)
			sprite.translateY(10f);
		if(value == XBox360Pad.BUTTON_DPAD_DOWN)
			sprite.translateY(-10f);
		return false;
	}

	public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
		return false;
	}

	public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
		return false;
	}

	public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
		return false;
	}


	public boolean buttonDown() {
		// TODO Auto-generated method stub
		return false;
	}
*/
}
