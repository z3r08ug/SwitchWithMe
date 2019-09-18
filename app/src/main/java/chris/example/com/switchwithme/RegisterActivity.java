package chris.example.com.switchwithme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private EditText etEmail, etPassword, etConfirm;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        mAuth = FirebaseAuth.getInstance();
        
        etEmail = findViewById(R.id.etRegisterEmail);
        etPassword = findViewById(R.id.etRegisterPassword);
        etConfirm = findViewById(R.id.etConfirmPassword);
    }
    
    @Override
    protected void onStart()
    {
        super.onStart();
        
        FirebaseUser currentUser = mAuth.getCurrentUser();
        
        if (currentUser != null)
        {
            sendUserToMainActivity();
        }
    }
    
    private void sendUserToSetupActivity()
    {
        Intent intent = new Intent(RegisterActivity.this, SetupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    
    private void sendUserToMainActivity()
    {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    
    public void verifyRegistration(View view)
    {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String confirm = etConfirm.getText().toString();
        
        if (email.isEmpty())
        {
            Toast.makeText(this, "An email is required...", Toast.LENGTH_SHORT).show();
        }
        else if (password.isEmpty())
        {
            Toast.makeText(this, "A password is required...", Toast.LENGTH_SHORT).show();
        }
        else if (confirm.isEmpty())
        {
            Toast.makeText(this, "Please confirm your password...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (password.length() < 6)
            {
                Toast.makeText(this, "Password is too short...", Toast.LENGTH_SHORT).show();
            }
            else if (password.equals(confirm))
            {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(RegisterActivity.this, "Successfully registered!", Toast.LENGTH_SHORT).show();
                                    
                                    sendUserToSetupActivity();
                                }
                                else
                                {
                                    String message = task.getException().getMessage();
                                    Toast.makeText(RegisterActivity.this, String.format("Error occurred: %s", message), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    }
}
