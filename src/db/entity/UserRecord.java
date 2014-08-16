package db.entity;


public class UserRecord {
    private  int globalUserID;
    private String name;
    private String date;
    private double k_factor;

    public void setK_factor(double k_factor) {
        this.k_factor = k_factor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public double getK_factor() {
        return k_factor;
    }

    public int getGlobalUserID() {
        return globalUserID;
    }

    public void setGlobalUserID(int globalUserID) {
        this.globalUserID = globalUserID;
    }

}
