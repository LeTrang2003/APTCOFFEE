package fpt.edu.aptcoffee.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class NguoiDungAPI {

    @SerializedName("_id")
    @Expose
    private String _id;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("avatar")
    @Expose
    private String avatar;

    @SerializedName("rank")
    @Expose
    private String rank;


    public enum Status {
        admin(0), employee(1), diner(2);

        private int value;

        Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


    public NguoiDungAPI() {
    }

    public NguoiDungAPI(String _id, String email, String username, String password, String avatar, String rank) {
        this._id = _id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.rank = rank;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "NguoiDungAPI{" +
                "_id='" + _id + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", rank='" + rank + '\'' +
                '}';
    }
}
