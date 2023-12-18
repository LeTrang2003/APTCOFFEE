package fpt.edu.aptcoffee.model;

public class SanPhamAPi {

    private String _id;
    private String idType;
    private String name;
    private int price;
    private String[] image;


    public SanPhamAPi(String _id, String idType, String name, int price, String[] image) {
        this._id = _id;
        this.idType = idType;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String[] getImage() {
        return image;
    }

    public void setImage(String[] image) {
        this.image = image;
    }

    public SanPhamAPi() {
    }
}
