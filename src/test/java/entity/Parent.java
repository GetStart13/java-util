package entity;

public class Parent implements Root {
    private String parent;
    private boolean iiFlag;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public boolean isIiFlag() {
        return iiFlag;
    }

    public void setIiFlag(boolean iiFlag) {
        this.iiFlag = iiFlag;
    }
}
