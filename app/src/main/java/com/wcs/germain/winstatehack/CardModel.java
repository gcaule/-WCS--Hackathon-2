package com.wcs.germain.winstatehack;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wilder on 21/12/17.
 */
public class CardModel implements Parcelable {

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

    protected CardModel(Parcel in) {
        id = in.readString();
        gravity = in.readInt();
        width = in.readInt();
        height = in.readInt();
        color = in.readString();
        image = in.readString();
        text = in.readString();
        creatorId = in.readString();
        type = in.readString();
        if (in.readByte() == 0x01) {
            authorizedId = new ArrayList<String>();
            in.readList(authorizedId, String.class.getClassLoader());
        } else {
            authorizedId = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(gravity);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeString(color);
        dest.writeString(image);
        dest.writeString(text);
        dest.writeString(creatorId);
        dest.writeString(type);
        if (authorizedId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(authorizedId);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<CardModel> CREATOR = new Parcelable.Creator<CardModel>() {
        @Override
        public CardModel createFromParcel(Parcel in) {
            return new CardModel(in);
        }

        @Override
        public CardModel[] newArray(int size) {
            return new CardModel[size];
        }
    };
}