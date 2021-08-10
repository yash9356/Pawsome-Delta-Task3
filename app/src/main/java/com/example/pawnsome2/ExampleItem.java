package com.example.pawnsome2;

public class ExampleItem {
    private String mImageUrl;
    private String mBreedName;
    private int mId;

    public ExampleItem(String breedName,String imageUrl,int mId) {
        this.mId = mId;
        this.mBreedName= breedName;
        this.mImageUrl =imageUrl;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getBreedName() {
        return mBreedName;
    }

    public void setBreedName(String mBreedName) {
        this.mBreedName = mBreedName;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }
}
