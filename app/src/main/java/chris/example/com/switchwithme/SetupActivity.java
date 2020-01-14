package chris.example.com.switchwithme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

public class SetupActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private EditText etFriendCode, etHandle;
    private RadioButton btnMale, btnFemale;
    private String currentUserId;
    private DatabaseReference usersRef;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        
        etFriendCode = findViewById(R.id.etFriendCode);
        PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder("####-####-####")
            .fillExtraCharactersAutomatically(true)
            .deleteExtraCharactersAutomatically(true)
            .specialChar("#")
            .respectPatternLength(true)
            .saveAllInput(false)
            .build();
        etFriendCode.addTextChangedListener(patternedTextWatcher);

        etHandle = findViewById(R.id.etHandle);
        
        btnMale = findViewById(R.id.btnMale);
        btnFemale = findViewById(R.id.btnFemale);
    }
    
    public void saveData(View view)
    {
        String handle = etHandle.getText().toString();
        String friendCode = etFriendCode.getText().toString();
        
        String gender = "";
        if (btnMale.isChecked())
        {
            gender = "Male";
        }
        else if (btnFemale.isChecked())
        {
            gender = "Female";
        }
        
        if (friendCode.isEmpty())
        {
            Toast.makeText(this, "A friend code is required to continue...", Toast.LENGTH_SHORT).show();
        }
        else if (gender.isEmpty())
        {
            Toast.makeText(this, "Please select a gender...", Toast.LENGTH_SHORT).show();
        }
        else if (handle.isEmpty())
        {
            Toast.makeText(this, "Please enter your Gamer Tag...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            User user = new User(handle, gender, friendCode, currentUserId);
    
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("gender", gender);
            editor.apply();
            
            usersRef.child("Gender").child(gender).child(currentUserId).child("Info").setValue(user).addOnCompleteListener(task ->
            {
                if (task.isSuccessful())
                {
                    sendUserToMainActivity();
                }
                else
                {
                    String message = task.getException().getMessage();
                    Toast.makeText(SetupActivity.this, String.format("An error has occurred: %s", message), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    
    private void sendUserToMainActivity()
    {
        Intent intent = new Intent(SetupActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    
    public void toggleButtons(View view)
    {
        if (view.getId() == R.id.btnMale)
        {
            btnFemale.setChecked(false);
        }
        else
        {
            btnMale.setChecked(false);
        }
    }
}
