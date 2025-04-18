package DTO;

public class TypeProductDTO {
    private String typeID;
    private String typeName;

    public TypeProductDTO() {
    }

    public TypeProductDTO(String typeID, String typeName) {
        this.typeID = typeID;
        this.typeName = typeName;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
