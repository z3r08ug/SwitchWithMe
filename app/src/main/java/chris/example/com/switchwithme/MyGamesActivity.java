package chris.example.com.switchwithme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyGamesActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private RecyclerView rvGames;
    private MyGamesAdapter adapter;
    private FirebaseAuth m_auth;
    private DatabaseReference gamesRef;
    private String currentUserId;
    private List<Game> games;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_games);
        
        toolbar = findViewById(R.id.mainAppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Games");
    
        rvGames = findViewById(R.id.rvGames);
        rvGames.setItemAnimator(new DefaultItemAnimator());
        rvGames.setLayoutManager(new LinearLayoutManager(this));
    
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String gender = preferences.getString("gender", "");
        
        m_auth = FirebaseAuth.getInstance();
        currentUserId = m_auth.getCurrentUser().getUid();
        gamesRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Gender").child(gender).child(currentUserId).child("Games");
    
        games = new ArrayList<>();
        
        loadCurrentGames();
    }
    
    private void loadCurrentGames()
    {
        gamesRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                    while (iterable.iterator().hasNext())
                    {
                        games.add(iterable.iterator().next().getValue(Game.class));
                    }

                }
                adapter = new MyGamesAdapter(games);
                rvGames.setAdapter(adapter);
            }
            
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            
            }
        });
    }
    
    public void sendToAddGames(View view)
    {
        startActivity(new Intent(MyGamesActivity.this, AddGamesActivity.class));
        finish();
    }
}
