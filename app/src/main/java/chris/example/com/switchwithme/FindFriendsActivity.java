package chris.example.com.switchwithme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FindFriendsActivity extends AppCompatActivity
{
    public static final String TAG = FindFriendsActivity.class.getSimpleName()+"_TAG";
    
    private Toolbar toolbar;
    private ImageView ivGuys, ivGirls, ivBoth;
    private Button btnReset;
    private RecyclerView rvFriendsList;
    private UserAdapter adapter;
    private TextView tvLooking;
    private List<AdvancedUser> users;
    private FirebaseAuth m_auth;
    private DatabaseReference usersRef;
    private String currUserId;
    private boolean both;
    HashMap<String, Object> usermap;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);
        
        toolbar = findViewById(R.id.mainAppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Find Friends");
        
        ivGuys = findViewById(R.id.ivGuys);
        ivGirls = findViewById(R.id.ivGirls);
        ivBoth = findViewById(R.id.ivBoth);
        btnReset = findViewById(R.id.btnReset);
        rvFriendsList = findViewById(R.id.rvFriendList);
        rvFriendsList.setItemAnimator(new DefaultItemAnimator());
        rvFriendsList.setLayoutManager(new LinearLayoutManager(this));
        tvLooking = findViewById(R.id.tvLookingFor);
        
        both = false;
        
        m_auth = FirebaseAuth.getInstance();
        currUserId = m_auth.getCurrentUser().getUid();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        
        users = new ArrayList<>();
    }
    
    public void resetSearch(View view)
    {
        rvFriendsList.setVisibility(View.GONE);
        btnReset.setVisibility(View.GONE);
        
        tvLooking.setVisibility(View.VISIBLE);
        ivGuys.setVisibility(View.VISIBLE);
        ivGirls.setVisibility(View.VISIBLE);
        ivBoth.setVisibility(View.VISIBLE);
    }
    
    public void search(View view)
    {
        rvFriendsList.setVisibility(View.VISIBLE);
        btnReset.setVisibility(View.VISIBLE);
        
        tvLooking.setVisibility(View.GONE);
        ivGuys.setVisibility(View.GONE);
        ivGirls.setVisibility(View.GONE);
        ivBoth.setVisibility(View.GONE);
        
        switch (view.getId())
        {
            case R.id.ivGuys:
            {
                loadUsers("Male");
                break;
            }
            case R.id.ivGirls:
            {
                loadUsers("Female");
                break;
            }
            default:
            {
                both = true;
                loadUsers("Male");
                break;
            }
        }
    }
    
    private void loadUsers(String gender)
    {
        usersRef.child("Gender").child(gender).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                    while (iterable.iterator().hasNext())
                    {
                        
                        usermap = (HashMap<String, Object>) iterable.iterator().next().getValue();
                        HashMap<String, String> info = (HashMap<String, String>)usermap.get("Info");
                        HashMap<String, Game> g = (HashMap<String, Game>) usermap.get("Games");
                        AdvancedUser u = new AdvancedUser(g, info);
                        Log.d(TAG, "onDataChange: "+u.toString());
                        users.add(u);
                    }
    
                    
                    
                    if (!both && users.size() > 0)
                    {
                        adapter = new UserAdapter(users);
                        rvFriendsList.setAdapter(adapter);
                    }
                    else
                    {
                        loadUsers("Female");
                        both = false;
                    }
                }
            }
            
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            
            }
        });
    }
}
