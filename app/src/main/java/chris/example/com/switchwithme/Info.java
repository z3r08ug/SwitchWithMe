package chris.example.com.switchwithme;

public class Info
{
    String handle;
    String gender;
    String friendCode;
    String userId;
    
    public Info(String handle, String gender, String friendCode, String userId)
    {
        this.handle = handle;
        this.gender = gender;
        this.friendCode = friendCode;
        this.userId = userId;
    }
    
    public Info()
    {
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
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    @Override
    public String toString()
    {
        return "Info{" +
                "handle='" + handle + '\'' +
                ", gender='" + gender + '\'' +
                ", friendCode='" + friendCode + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
