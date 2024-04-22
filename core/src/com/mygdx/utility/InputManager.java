package com.mygdx.utility;

import com.badlogic.gdx.Input.Keys;

import java.io.IOException;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.imageeditor.ImageEditor;

public class InputManager implements InputProcessor{
    public Array<IClickable> Clickables = new Array<>();
    public Array<IHoverable> Hoverables = new Array<>();
    private IHoverable _hovered;
    private IClickable _clicked;
    private boolean _controlPressed;

    public static InputManager Instance;

    public InputManager(){
        Instance = this;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(_controlPressed && keycode == Keys.S){
            if(ImageInputOutput.Instance.ImageFolderLocation == null) return false;
            try{ImageInputOutput.Instance.saveImage(ImageInputOutput.Instance.ImageFolderLocation + "\\output.bmp");}
            catch(IOException e){}
        }
        if(keycode == Keys.CONTROL_LEFT) _controlPressed = true;
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Keys.CONTROL_LEFT) _controlPressed = false;
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
        if(_hovered != null && _hovered != collision) _hovered.onHoverExit();
        if(collision != null){
            collision.onHovered();
            if(collision != _hovered){
                _clicked = null;
            }
            _hovered = collision;
        }
        if(collision != _hovered){
            _clicked = null;
        }
        if(collision == null){
            _hovered = null;
        }
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
    
}
