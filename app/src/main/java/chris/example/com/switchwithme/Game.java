package chris.example.com.switchwithme;

public class Game
{
    String name;
    String pic;
    
    public Game()
    {
    }
    
    public Game(String name, String pic)
    {
        this.name = name;
        this.pic = pic;
    }
    
    public Game(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getPic()
    {
        return pic;
    }
    
    public void setPic(String pic)
    {
        this.pic = pic;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        Game game = (Game)obj;
        if (game.getName().equals(getName()))
            return true;
        return false;
    }
    
    @Override
    public String toString()
    {
        return "Game{" +
                "name='" + name + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
