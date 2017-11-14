package nz.ac.unitec.easybuy;

/**
 * Created by eugene on 14/11/2017.
 */

public class ProductItem {

    /**
     * Item Id
     */
    @com.google.gson.annotations.SerializedName("id")
    private String mId;

    /**
     * Item name
     */
    @com.google.gson.annotations.SerializedName("name")
    private String mName;

    /**
     * Item price
     */
    @com.google.gson.annotations.SerializedName("price")
    private float mPrice;

    /**
     * Item lat
     */
    @com.google.gson.annotations.SerializedName("lat")
    private float mLat;

    /**
     * Item lng
     */
    @com.google.gson.annotations.SerializedName("lng")
    private float mLng;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float price) {
        this.mPrice = price;
    }

    public float getLat() {
        return mLat;
    }

    public void setLat(float lat) {
        this.mLat = lat;
    }

    public float getLng() {
        return mLng;
    }

    public void setLng(float lng) {
        this.mLng = lng;
    }
}
