package com.mygdx.imageeditor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.math.Vector2;

public class EditWindow extends Rec2D implements IClickable{
    public Texture DoodleTexture;
    private Pixmap _doodleMap;
    private Vector2 _previousPaintPosition;

    public EditWindow(Vector2 scale, Vector2 position, Color backgroundColor){
        super(scale, position, backgroundColor);
        _doodleMap = new Pixmap((int)scale.x, (int)scale.y, Format.RGBA8888);
        _doodleMap.setColor(Color.ORANGE);
        DoodleTexture = new Texture(_doodleMap);
        InputManager.Instance.Clickables.add(this);
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
        _doodleMap.drawLine(startX, startY, endX, endY);
        _doodleMap.drawLine(startX + 1, startY, endX + 1, endY);
        _doodleMap.drawLine(startX - 1, startY, endX - 1, endY);
        _previousPaintPosition = paintPosition;
        DoodleTexture = new Texture(_doodleMap);
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
