package com.mygdx.imageeditor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class ImageEditor extends ApplicationAdapter {
	SpriteBatch batch;
	Rec2D rectangle;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		rectangle = new Rec2D(new Vector2(200, 100), new Vector2(0, 0), new Vector2(3, 2), Color.BLUE);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		batch.draw(rectangle.RecTexture, rectangle.Position.x, rectangle.Position.y);
		batch.end();
		rectangle.update();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		rectangle.RecTexture.dispose();
	}
}
