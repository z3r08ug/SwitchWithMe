package chris.example.com.switchwithme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        toolbar = findViewById(R.id.tbMainPage);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
    }
    
    public void sendToViewGames(View view)
    {
        Intent intent = new Intent(MainActivity.this, MyGamesActivity.class);
        startActivity(intent);
    }
    
    public void sendToFindFriends(View view)
    {
        Intent intent = new Intent(MainActivity.this, FindFriendsActivity.class);
        startActivity(intent);
    }
}
