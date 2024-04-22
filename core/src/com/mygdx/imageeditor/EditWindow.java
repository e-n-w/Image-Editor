package com.mygdx.imageeditor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.utility.IClickable;
import com.mygdx.utility.InputManager;

public class EditWindow extends Rec2D implements IClickable{
    public Texture DoodleTexture;
    public static EditWindow Instance;
    public Pixmap DoodleMap;
    private Vector2 _previousPaintPosition;
    public Color DrawColor;

    public EditWindow(Vector2 scale, Vector2 position){
        super(scale, position, Color.SLATE);
        DoodleMap = new Pixmap((int)scale.x, (int)scale.y, Format.RGBA8888);
        DrawColor = Color.WHITE;
        DoodleMap.setColor(DrawColor);
        DoodleTexture = new Texture(DoodleMap);
        InputManager.Instance.Clickables.add(this);
        Instance = this;
    }

    private void paintAtPosition(Vector2 position){
        Vector2 paintPosition = new Vector2(
            position.x - Position.x, 
            position.y - (ImageEditor.Instance.ScreenSize.y - Scale.y - Position.y)
        );
        if(_previousPaintPosition == null){
            _previousPaintPosition = paintPosition;
        }
        int startX = (int)_previousPaintPosition.x;
        int startY = (int)_previousPaintPosition.y;
        int endX = (int)paintPosition.x;
        int endY = (int)paintPosition.y;
        DoodleMap.drawLine(startX, startY, endX, endY);
        DoodleMap.drawLine(startX + 1, startY, endX + 1, endY);
        DoodleMap.drawLine(startX - 1, startY, endX - 1, endY);
        _previousPaintPosition = paintPosition;
        DoodleTexture = new Texture(DoodleMap);
    }

    @Override
    public void onClickDown(Vector2 mousePosition) {
        paintAtPosition(mousePosition);
    }

    @Override
    public void onClickUp(Vector2 mousePosition) {
        _previousPaintPosition = null;
    }

    @Override
    public void onClickDragged(Vector2 mousePosition){
        paintAtPosition(mousePosition);
    }
}
