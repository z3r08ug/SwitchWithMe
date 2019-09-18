package chris.example.com.switchwithme;

import java.util.HashMap;

public class AdvancedUser
{
    private HashMap<String, String> info;
    private HashMap<String, Game> games;
    
    public AdvancedUser(HashMap<String, Game> games, HashMap<String, String> info)
    {
        this.info = info;
        this.games = games;
    }
    
    public AdvancedUser()
    {
    }
    
    public HashMap<String, String> getInfo()
    {
        return info;
    }
    
    public void setInfo(HashMap<String, String> info)
    {
        this.info = info;
    }
    
    public HashMap<String, Game> getGames()
    {
        return games;
    }
    
    public void setGames(HashMap<String, Game> games)
    {
        this.games = games;
    }
    
    @Override
    public String toString()
    {
        return "AdvancedUser{" +
                "info=" + info +
                ", games=" + games +
                '}';
    }
}
