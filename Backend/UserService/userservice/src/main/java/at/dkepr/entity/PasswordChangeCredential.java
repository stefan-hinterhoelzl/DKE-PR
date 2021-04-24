package at.dkepr.entity;

public class PasswordChangeCredential {
    String oldpassword;
    String newpassword;
    String newpasswordconfirm;

    
    public String getOldpassword() {
        return oldpassword;
    }
    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }
    public String getNewpassword() {
        return newpassword;
    }
    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }
    public String getNewpasswordconfirm() {
        return newpasswordconfirm;
    }
    public void setNewpasswordconfirm(String newpasswordconfirm) {
        this.newpasswordconfirm = newpasswordconfirm;
    }
    

    
  
}
