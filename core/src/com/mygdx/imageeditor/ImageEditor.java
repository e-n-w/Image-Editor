package com.mygdx.imageeditor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.buttons.Button;
import com.mygdx.buttons.ClearDoodleButton;
import com.mygdx.buttons.ColorButton;
import com.mygdx.buttons.ExitButton;
import com.mygdx.buttons.SaveButton;
import com.mygdx.utility.CollisionManager;
import com.mygdx.utility.ImageInputOutput;
import com.mygdx.utility.InputManager;

public class ImageEditor extends ApplicationAdapter {
	public static ImageEditor Instance;

	SpriteBatch batch;
	Array<Rec2D> Rectangles = new Array<>();
	EditWindow editWindow;

	public Vector2 ScreenSize;

	private BitmapFont _font;
	
	@Override
	public void create () {
		Instance = this;
		initializeUtilityClasses();
		createGraphicalElements();
	}

	private void initializeUtilityClasses(){
		new CollisionManager();
		new ImageInputOutput();
		InputManager inputManager = new InputManager();
		Gdx.input.setInputProcessor(inputManager);
		_font = new BitmapFont();
	}

	private void createGraphicalElements(){
		ScreenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		Vector2 editWindowScale = new Vector2(500, ScreenSize.y - 40);
		editWindow = new EditWindow(
			editWindowScale, new Vector2(ScreenSize.x - editWindowScale.x, 0)
		);
		new ColorButton(new Vector2(42,42), new Vector2(), Color.BLUE);
		new ColorButton(new Vector2(42,42), new Vector2(42, 0), Color.PURPLE);
		new ColorButton(new Vector2(42, 42), new Vector2(0, 42), Color.MAGENTA);
		new ColorButton(new Vector2(42, 42), new Vector2(42, 42), Color.RED);
		new ColorButton(new Vector2(42, 42), new Vector2(0, 84), Color.ORANGE);
		new ColorButton(new Vector2(42, 42), new Vector2(42, 84), Color.YELLOW);
		new ColorButton(new Vector2(42, 42), new Vector2(0, 126), Color.GREEN);
		new ColorButton(new Vector2(42, 42), new Vector2(42, 126), Color.FOREST);
		new ColorButton(new Vector2(42, 42), new Vector2(0, 168), Color.CYAN);
		new ColorButton(new Vector2(42, 42), new Vector2(42, 168), Color.GRAY);
		new ColorButton(new Vector2(42, 42), new Vector2(0, 210), Color.WHITE);
		new ColorButton(new Vector2(42, 42), new Vector2(42, 210), Color.BLACK);

		new SaveButton(new Vector2(75, 25), new Vector2(0, ScreenSize.y - 25), Color.GRAY);
		new ExitButton(new Vector2(75, 25), new Vector2(75, ScreenSize.y - 25), Color.GRAY);
		new ClearDoodleButton(new Vector2(75, 25), new Vector2(150, ScreenSize.y - 25), Color.GRAY);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		for(Rec2D rectangle : Rectangles){
			batch.draw(rectangle.RecTexture, rectangle.Position.x, rectangle.Position.y, rectangle.Scale.x, rectangle.Scale.y);
			batch.draw(rectangle.Outline.OutlineTexture, rectangle.Position.x, rectangle.Position.y);
		}
		batch.draw(editWindow.DoodleTexture, editWindow.Position.x, editWindow.Position.y, editWindow.Scale.x, editWindow.Scale.y);
		for(Rec2D rectangle : Rectangles){
			if(rectangle instanceof Button){
				Button button = (Button)rectangle;
				if(button.ButtonText == null) continue;
				_font.draw(batch, button.ButtonText, button.Position.x, button.Position.y + button.Scale.y * 0.75f,
					button.Scale.x, Align.center, false);
			}
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	public void filesImported(String[] files){
		Pixmap map = ImageInputOutput.Instance.loadImage(files[0]);
		if(map == null) return;
		editWindow.RecTexture = new Texture(Util.scalePixmap(map, editWindow.Scale));
	}
}
