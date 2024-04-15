package com.mygdx.imageeditor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class ImageEditor extends ApplicationAdapter {
	public static ImageEditor Instance;

	SpriteBatch batch;
	Array<Rec2D> Rectangles = new Array<>();
	EditWindow editWindow;
	Button testButton;

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
		
		Vector2 editWindowScale = new Vector2(500, ScreenSize.y - 40);
		editWindow = new EditWindow(
			editWindowScale, new Vector2(ScreenSize.x - editWindowScale.x, 0), Color.GRAY
		);
		testButton = new Button(new Vector2(80,80), new Vector2(), Color.BLUE);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		for(Rec2D rectangle : Rectangles){
			batch.draw(rectangle.RecTexture, rectangle.Position.x, rectangle.Position.y);
		}
		batch.draw(editWindow.DoodleTexture, editWindow.Position.x, editWindow.Position.y, editWindow.Scale.x, editWindow.Scale.y);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
