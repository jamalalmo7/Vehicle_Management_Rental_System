
package vehicle_management_rental_system;

/*  This class is only for 'self -contained operations 
as an entity for itself to make only related op for it
====================
However, class UserManager has the ability to see comprehensively above and 
determine what to manage without confilcting with the user obj itself
like if the ahmed user needs to store lists cannot has it inside himself needs
a place to store and manage there as well as deleting another user etc...

        */
public class User {
    private String userName;
    private String password;
    private Role role;// enum
    
    private boolean loggedIn;// for logout method 
    
    public User (String userName, String password, Role role){
        this.userName = userName;
        this.password = password;
        this.role = role;
       
    
    
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    
    /*Methods ==============================
    ========================================
    */
    
    public boolean changePassword(String oldPassword, String newPassword){
        if (this.password.equals(oldPassword)){
        this.password = newPassword;
        return true;
        }
        return false;
        
    }
    
    public void changeUserName(String username){
    this.userName = username;
    // can be enhanced ********
    
    }
    
     public boolean login(String username , String password){
     return this.userName.equals(username) && this.password.equals(password);
     // after DBS it will be searched in UserManager to find the user then here to check 
     
     }
    
    public void logout(){  // Now we need a private boolean loggedIn property
    this.loggedIn = false;
    
    }
    
    
    
    
    
}
