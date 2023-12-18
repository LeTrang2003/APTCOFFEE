package fpt.edu.aptcoffee.model;

import android.content.Intent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class BanApi implements Serializable {

    @SerializedName("_id")
    @Expose
    private String _id;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("status")
    @Expose
    private int status;

    public BanApi(String _id, String name, int status) {
        this._id = _id;
        this.name = name;
        this.status = status;
    }

    public BanApi() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
