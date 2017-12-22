package com.wcs.germain.winstatehack;

import android.graphics.drawable.Drawable;

import java.util.List;

/**
 * Created by wilder on 21/12/17.
 */

public class CardModel {

    private String id;
    private int gravity;
    private int width;
    private int height;
    private String color;
    private String image;
    private String text;
    private String creatorId;
    private String type;
    private List<String> authorizedId;

    public CardModel() {
    }

    public CardModel(String id, int gravity, int width, int height, String color, String image, String text, String creatorId, String type, List<String> authorizedId) {
        this.id = id;
        this.gravity = gravity;
        this.width = width;
        this.height = height;
        this.color = color;
        this.image = image;
        this.text = text;
        this.creatorId = creatorId;
        this.type = type;
        this.authorizedId = authorizedId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getAuthorizedId() {
        return authorizedId;
    }

    public void setAuthorizedId(List<String> authorizedId) {
        this.authorizedId = authorizedId;
    }
}