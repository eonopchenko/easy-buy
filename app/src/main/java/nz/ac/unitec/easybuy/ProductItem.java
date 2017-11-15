package nz.ac.unitec.easybuy;

import com.google.android.gms.maps.model.Marker;

import java.util.Date;

/**
 * Created by eugene on 14/11/2017.
 */

public class ProductItem {

    @com.google.gson.annotations.SerializedName("id")
    private String mId;

    @com.google.gson.annotations.SerializedName("barcode")
    private String mBarcode;

    @com.google.gson.annotations.SerializedName("name")
    private String mName;

    @com.google.gson.annotations.SerializedName("price")
    private float mPrice;

    @com.google.gson.annotations.SerializedName("lat")
    private double mLat;

    @com.google.gson.annotations.SerializedName("lng")
    private double mLng;

    @com.google.gson.annotations.SerializedName("updatedAt")
    private Date mDate;

    private Marker mMarker;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getBarcode() {
        return mBarcode;
    }

    public void setBarcode(String barcode) {
        this.mBarcode = barcode;
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

    public double getLat() {
        return mLat;
    }

    public void setLat(double lat) {
        this.mLat = lat;
    }

    public double getLng() {
        return mLng;
    }

    public void setLng(double lng) {
        this.mLng = lng;
    }

    public Marker getMarker() {
        return mMarker;
    }

    public void setMarker(Marker marker) {
        mMarker = marker;
    }

    public java.util.Date getDate() {
        return mDate;
    }

    public void setDate(java.util.Date date) {
        this.mDate = date;
    }
}
