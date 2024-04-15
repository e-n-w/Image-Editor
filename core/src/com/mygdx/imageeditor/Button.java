package com.mygdx.imageeditor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Button extends Rec2D implements IClickable, IHoverable{
    private Color _startColor;
    private Color _hoveredColor;
    public enum ButtonState{Clicked, Hovered, None};
    private ButtonState _state;
    
    public Button(Vector2 scale, Vector2 position, Color recColor){
        super(scale, position, recColor);
        _state = ButtonState.None;
        _startColor = recColor;
        _hoveredColor = new Color(recColor.r/2.0f, recColor.g/2.0f, recColor.b/2.0f, 1f);
        InputManager.Instance.Clickables.add(this);
        InputManager.Instance.Hoverables.add(this);
    }

    @Override
    public void onClickDown(Vector2 position){
        _state = ButtonState.Clicked;
        changeColor(new Color(_startColor.r/4.0f, _startColor.g/4.0f, _startColor.b/4.0f, 1f));
        generateTexture();
    }

    @Override
    public void onClickUp(Vector2 mousePosition){
        _state = ButtonState.Hovered;
        changeColor(_hoveredColor);
        generateTexture();
    }

    @Override
    public void onHovered(){
        if(_state == ButtonState.Clicked) return;
        _state = ButtonState.Hovered;
        changeColor(_hoveredColor);
        generateTexture();
    }

    @Override
    public void onHoverExit(){
        _color = _startColor;
        _state = ButtonState.None;
        generateTexture();
    }

    @Override
    public void onClickDragged(Vector2 mousePosition) {

    }
}
