package com.example.login;

public class YourPointClass {
    private String Remark;
    private String Weight;
    private String Points;
    String documentId;
    //Class activityName;
public YourPointClass(){

}
    public YourPointClass(String Remark, String Weight, String Points) {
        this.Remark = Remark;
        this.Weight = Weight;
        this.Points = Points;
       // this.activityName = activityName;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getPoints() {
        return Points + " Points";
    }

    public void setPoints(String points) {
        Points = points;
    }

    public String getRemark() {
        return "Type: "+ Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getWeight() {
        return "Weight: "+ Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }
}

