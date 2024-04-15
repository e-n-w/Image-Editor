package com.mygdx.imageeditor;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class InputManager implements InputProcessor{
    public Array<IClickable> Clickables = new Array<>();
    public Array<IHoverable> Hoverables = new Array<>();
    private IHoverable _hovered;
    private IClickable _clicked;

    public static InputManager Instance;

    public InputManager(){
        Instance = this;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        IClickable collision = CollisionManager.Instance.getClicked(
            new Vector2(screenX, ImageEditor.Instance.ScreenSize.y - screenY)
        );
        if(collision != null){
            collision.onClickDown(new Vector2(screenX, screenY));
            _clicked = collision;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(_clicked != null){
            _clicked.onClickUp(new Vector2(screenX, screenY));
            _clicked = null;
        }
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        mouseMoved(screenX, screenY);
        if(_clicked != null){
            _clicked.onClickDragged(new Vector2(screenX, screenY));
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        IHoverable collision = CollisionManager.Instance.getHovered(
            new Vector2(screenX, ImageEditor.Instance.ScreenSize.y - screenY)
        );
        if(collision != _hovered && _hovered != null){
            _hovered.onHoverExit();
            _hovered = null;
        }
        if(collision != null){
            if(collision != _hovered)
                collision.onHovered();
            _hovered = collision;
        }
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
    
}
