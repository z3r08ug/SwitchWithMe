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
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddGamesActivity extends AppCompatActivity
{
    public static final String TAG = AddGamesActivity.class.getSimpleName() + "_TAG";
    
    private Toolbar toolbar;
    private RecyclerView rvGames;
    private GamesAdapter gamesAdapter;
    private List<Game> games, savedGames;
    private FirebaseAuth m_auth;
    private DatabaseReference gamesRef;
    private String currentUserId;
    private int cnt;
    private boolean saving;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_games);
        
        toolbar = findViewById(R.id.mainAppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select games");
    
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String gender = preferences.getString("gender", "");
        
        if (gender.isEmpty())
        {
            Toast.makeText(this, "Gender is empty", Toast.LENGTH_SHORT).show();
        }
        
        m_auth = FirebaseAuth.getInstance();
        currentUserId = m_auth.getCurrentUser().getUid();
        gamesRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Gender").child(gender).child(currentUserId).child("Games");
    
        rvGames = findViewById(R.id.rvGames);
        rvGames.setItemAnimator(new DefaultItemAnimator());
        rvGames.setLayoutManager(new LinearLayoutManager(this));
        
        cnt = 0;
        saving = false;
        games = new ArrayList<>();
        savedGames = new ArrayList<>();
        fillGames();
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
                        savedGames.add(iterable.iterator().next().getValue(Game.class));
                    }
    
                    gamesAdapter = new GamesAdapter(games, savedGames);
                    rvGames.setAdapter(gamesAdapter);
                }
                else
                {
                    gamesAdapter = new GamesAdapter(games, savedGames);
                    rvGames.setAdapter(gamesAdapter);
                }
            }
    
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
        
            }
        });
    }
    
    private void fillGames()
    {
        games.add(new Game("Super Smash Bros. Ultimate", "https://firebasestorage.googleapis.com/v0/b/switch-with-me.appspot.com/o/ssbu.jpg?alt=media&token=1a8812ba-0176-4ca0-943c-84b2caa8b6a2"));
        games.add(new Game("Mario Kart 8 Deluxe", "https://firebasestorage.googleapis.com/v0/b/switch-with-me.appspot.com/o/logo-mk8.png?alt=media&token=8b26d422-8bd8-4e63-9bf0-ff22a04d2448"));
        games.add(new Game("Stardew Valley", "https://firebasestorage.googleapis.com/v0/b/switch-with-me.appspot.com/o/sdv.png?alt=media&token=db26d808-2b71-4ece-a1b8-c0f693d28f4e"));
        games.add(new Game("Splatoon 2", "https://firebasestorage.googleapis.com/v0/b/switch-with-me.appspot.com/o/sp2.png?alt=media&token=6848adac-2204-4d76-9c12-657c4c164b68"));
    }
    
    public void saveGames(View view)
    {
        if (!saving)
        {
            saving = true;
            cnt = 0;
            List<Game> selectedGames = gamesAdapter.getSelectedGames();
    
            if (selectedGames.size() == 0)
            {
                saving = false;
                sendUserToGames();
            }
    
            gamesRef.removeValue().addOnCompleteListener(task ->
            {
                if (task.isSuccessful())
                {
                    Log.d(TAG, "saveGames: Games Removed.");
                }
            });
    
            for (int x = 0; x < selectedGames.size(); x++)
            {
                gamesRef.push().setValue(selectedGames.get(x)).addOnCompleteListener(task ->
                {
                    if (task.isSuccessful())
                    {
                        cnt++;
                        if (cnt == selectedGames.size())
                        {
                            Log.d(TAG, "saveGames: finished saving last game");
                            Toast.makeText(AddGamesActivity.this, "Games were saved.", Toast.LENGTH_SHORT).show();
                            saving = false;
                            sendUserToGames();
                        }
                    }
                    else
                    {
                        Toast.makeText(AddGamesActivity.this, "Unable to save games. Please try again!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
    
    private void sendUserToGames()
    {
        Intent intent = new Intent(AddGamesActivity.this, MyGamesActivity.class);
        startActivity(intent);
        finish();
    }
}
