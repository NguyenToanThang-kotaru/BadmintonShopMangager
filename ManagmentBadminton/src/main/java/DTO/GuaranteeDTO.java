package DTO;

public class GuaranteeDTO {

    private String BaohanhID;
    private String SerialID;
    private String trangthai;
    private String TGBH;
    private String lydo;

    public GuaranteeDTO() {
        this.BaohanhID = "";
        this.SerialID = "";
        this.lydo = "";
        this.trangthai = "";
    }

    public GuaranteeDTO(String BaohanhID, String SerialID, String lydo, String trangthai) {
        this.BaohanhID = BaohanhID;
        this.SerialID = SerialID;
        this.lydo = lydo;
        this.trangthai = trangthai;
    }

    public String getBaohanhID() {
        return BaohanhID;
    }

    public void setBaohanhID(String BaohanhID) {
        this.BaohanhID = BaohanhID;
    }

    public String getSerialID() {
        return SerialID;
    }

    public void setSerialID(String SerialID) {
        this.SerialID = SerialID;
    }

    public String gettrangthai() {
        return trangthai;
    }

    public void settrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
    public String getLydo() {
        return lydo;
    }

    public void setLydo(String lydo) {
        this.lydo = lydo;
    }
}
