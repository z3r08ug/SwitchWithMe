package chris.example.com.switchwithme;

public class User
{
    String handle;
    String gender;
    String friendCode;
    String userId;
    
    public User()
    {
    }
    
    public User(String handle, String gender, String friendCode, String userId)
    {
        this.handle = handle;
        this.gender = gender;
        this.friendCode = friendCode;
        this.userId = userId;
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public String getHandle()
    {
        return handle;
    }
    
    public void setHandle(String handle)
    {
        this.handle = handle;
    }
    
    public String getGender()
    {
        return gender;
    }
    
    public void setGender(String gender)
    {
        this.gender = gender;
    }
    
    public String getFriendCode()
    {
        return friendCode;
    }
    
    public void setFriendCode(String friendCode)
    {
        this.friendCode = friendCode;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        User user = (User)obj;
        if (user.getUserId().equals(getUserId()))
            return true;
        return false;
    }
    
    @Override
    public String toString()
    {
        return "User{" +
                "handle='" + handle + '\'' +
                ", gender='" + gender + '\'' +
                ", friendCode='" + friendCode + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
