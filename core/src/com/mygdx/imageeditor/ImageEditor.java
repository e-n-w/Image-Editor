package com.mygdx.imageeditor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class ImageEditor extends ApplicationAdapter {
	public static ImageEditor Instance;

	SpriteBatch batch;
	Button button;
	Button button2;
	Button button3;
	Button button4;
	Button button5;

	public Vector2 ScreenSize;

	public ImageEditor(){
		Instance = this;
	}
	
	@Override
	public void create () {
		ScreenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();

		InputManager inputManager = new InputManager();
		Gdx.input.setInputProcessor(inputManager);

		CollisionManager collisionManager = new CollisionManager();

		Vector2 rectangleScale = new Vector2(100, 100);
		button = new Button(rectangleScale, 
			new Vector2(
				40,
				ScreenSize.y - rectangleScale.y - 40), 
			Color.RED);
		button2 = new Button(rectangleScale, 
			new Vector2(
				40,
				40), 
			Color.ORANGE);
		button3 = new Button(rectangleScale, 
			new Vector2(
				ScreenSize.x - rectangleScale.x - 40,
				ScreenSize.y - rectangleScale.y - 40), 
			Color.BLUE);
		button4	= new Button(rectangleScale, 
			new Vector2(
				ScreenSize.x - rectangleScale.x - 40,
				40), 
			Color.GREEN);
		button5 = new Button(rectangleScale,
			new Vector2(
				ScreenSize.x/2.0f - rectangleScale.x/2.0f,
				ScreenSize.y/2.0f - rectangleScale.y/2.0f),
			Color.WHITE);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		batch.draw(button.RecTexture, button.Position.x, button.Position.y);
		batch.draw(button2.RecTexture, button2.Position.x, button2.Position.y);
		batch.draw(button3.RecTexture, button3.Position.x, button3.Position.y);
		batch.draw(button4.RecTexture, button4.Position.x, button4.Position.y);
		batch.draw(button5.RecTexture, button5.Position.x, button5.Position.y);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
