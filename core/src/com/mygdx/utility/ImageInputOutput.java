package com.mygdx.utility;

import java.io.FileOutputStream;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.StringBuilder;
import com.mygdx.imageeditor.EditWindow;
import com.mygdx.imageeditor.Util;

public class ImageInputOutput {
    public static ImageInputOutput Instance;
    private byte[] _fileHeader;
    private Pixmap _pixels;
    public String ImageFolderLocation;

    public ImageInputOutput(){
        Instance = this;
    }

    public Pixmap loadImage(String filePath){
        ImageFolderLocation = scrapeImageLocation(filePath);
        byte[] fileBytes = Gdx.files.internal(filePath).readBytes();
        int[] fileIntData = Util.unsignBytes(fileBytes);
        if(fileBytes[0] != 'B' || fileBytes[1] != 'M') return null;
        byte[] fileSize = {fileBytes[2], fileBytes[3], fileBytes[4], fileBytes[5]};
        byte[] start = {fileBytes[10], fileBytes[11], fileBytes[12], fileBytes[13]};
        byte[] widthBytes = {fileBytes[18], fileBytes[19], fileBytes[20], fileBytes[21]};
        byte[] heightBytes = {fileBytes[22], fileBytes[23], fileBytes[24], fileBytes[25]};
        byte[] bitsPerPixel = {fileBytes[28], fileBytes[29]};
        int startPoint = Util.bytesToInt(start);
        int width = Util.bytesToInt(widthBytes);
        int height = Util.bytesToInt(heightBytes);
        if(Util.bytesToInt(bitsPerPixel) != 24) return null;

        _fileHeader = new byte[startPoint];
        for (int i = 0; i < startPoint; i++){
            _fileHeader[i] = fileBytes[i];
        }

        Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
        int r, g, b;
        int x = 0;
        int y = height - 1;
        for(int i = startPoint; i < fileIntData.length - 3; i += 3){
            b = fileIntData[i];
            g = fileIntData[i+1];
            r = fileIntData[i+2];
            float bFloat = b/255.0f;
            float gFloat = g/255.0f;
            float rFloat = r/255.0f;
            pixmap.setColor(new Color(rFloat, gFloat, bFloat, 1.0f));
            pixmap.drawPixel(x, y);
            x++;
            if(x >= width){
                x = 0;
                y--;
            }
        }
        _pixels = pixmap;
        return pixmap;
    }

    public void saveImage(String path) throws IOException{
        FileOutputStream output = new FileOutputStream(path);
        byte[] color;
        int colorIndex = 0;
        byte[] colorData = new byte[_pixels.getHeight() * _pixels.getWidth() * 3];
        for(int y = _pixels.getHeight() - 1; y >= 0; y--){
            for(int x = 0; x < _pixels.getWidth(); x++){
                color = Util.intToSignedBytes(_pixels.getPixel(x, y));
                colorData[colorIndex] = color[2];
                colorData[colorIndex + 1] = color[1];
                colorData[colorIndex + 2] = color[0];
                colorIndex += 3;
            }
        }
        Pixmap doodle = Util.scalePixmap(
            EditWindow.Instance.DoodleMap, new Vector2(_pixels.getWidth(), _pixels.getHeight())
        );
        colorIndex = 0;
        for(int y = doodle.getHeight() - 1; y >= 0; y--){
            for(int x = 0; x < doodle.getWidth(); x++){
                color = Util.intToSignedBytes(doodle.getPixel(x, y));
                if(color[3] != -1) {colorIndex += 3; continue;}
                colorData[colorIndex] = color[2];
                colorData[colorIndex + 1] = color[1];
                colorData[colorIndex + 2] = color[0];
                colorIndex += 3;
            }
        }
        output.write(_fileHeader);
        output.write(colorData);
        output.close();
    }

    private String scrapeImageLocation(String filepath){
        StringBuilder builder = new StringBuilder(filepath);
        for(int i = filepath.length() - 1; i >= 0; i--){
            if(filepath.charAt(i) != '\\') continue;
            return builder.substring(0, i);
        }
        return null;
    }

}
