package com.example.playsmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText email;
    EditText psw_log;
    Button login;
    FirebaseAuth f_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        psw_log = findViewById(R.id.password_reg);
        login = findViewById(R.id.sign_in);
        f_out = FirebaseAuth.getInstance();

        if(f_out.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), Dashboard.class));
            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailInput = email.getText().toString().trim();
                String psw = psw_log.getText().toString().trim();


                f_out.signInWithEmailAndPassword(emailInput, psw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(), Dashboard.class));
                        }else{
                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }

    public void register(View view) {
        startActivity(new Intent(MainActivity.this, activity_register.class));
    }

    public void forgot_password(View view) {
        EditText reset_psw = new EditText(view.getContext());
        AlertDialog.Builder psw_reset_dialogue = new AlertDialog.Builder(view.getContext());
        psw_reset_dialogue.setTitle("Password Dimenticata");
        psw_reset_dialogue.setMessage("Inserisci l'indirizzo email per ricevere il link di reset");
        psw_reset_dialogue.setView(reset_psw);
        psw_reset_dialogue.setPositiveButton("Conferma", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String mail = reset_psw.getText().toString().trim();
                if (!mail.isEmpty()) {
                    f_out.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MainActivity.this, "Link di reset inviato", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Impossibile inviare il link" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(MainActivity.this, "Inserisci una mail valida", Toast.LENGTH_SHORT).show();
                }
            }
        });

        psw_reset_dialogue.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        psw_reset_dialogue.create().show();

    }
}
