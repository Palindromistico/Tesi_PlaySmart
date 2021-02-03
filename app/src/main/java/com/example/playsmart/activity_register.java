package com.example.playsmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class activity_register extends AppCompatActivity {

    EditText Mail;
    EditText Psw;
    EditText ConfirmPsw;
    EditText Address;
    EditText UserName;
    RadioButton GoalKeeper;
    FirebaseAuth f_out;
    FirebaseFirestore fStore;
    String userID;
    String Portiere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Mail = findViewById(R.id.email);
        Psw = findViewById(R.id.password_reg);
        ConfirmPsw = findViewById(R.id.confspw);
        f_out = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        Address = findViewById(R.id.address);
        UserName = findViewById(R.id.nickname);
        GoalKeeper = findViewById(R.id.keeper);
        Portiere = "false";


        Psw.setTransformationMethod(new PasswordTransformationMethod());
        ConfirmPsw.setTransformationMethod(new PasswordTransformationMethod());

        //TODO da spostare in login

    }


    public void signIn(View view) {

        String address = Address.getText().toString().trim();
        String Name = UserName.getText().toString().trim();
        RadioButton goalKeeper = GoalKeeper;
        String portiere = Portiere;


        String mail = Mail.getText().toString().trim();
        String pass = Psw.getText().toString().trim();
        String cpass = ConfirmPsw.getText().toString().trim();

        //TODO controllo formato mail
        if(TextUtils.isEmpty(mail)){
            Mail.setError("inserire mail");
            return;
        }

        //TODO asterischi
        if(TextUtils.isEmpty(pass)){
            Psw.setError("inserire password");
            return;
        }

        //TODO asterischi
        if(TextUtils.isEmpty(cpass) || !cpass.equals(pass)){
            ConfirmPsw.setError("password diverse");
            return;
        }

        if(TextUtils.isEmpty(Name)){
            UserName.setError("inserire nome");
            return;
        }

        //TODO controllo indirizzo esistente
        if(TextUtils.isEmpty(address)){
            Address.setError("inserire indirizzo");
            return;
        }

        if (goalKeeper.isChecked()) {
            portiere = "true";

            //Toast.makeText(getApplicationContext(), user.username, Toast.LENGTH_SHORT).show();
        }

        String finalPortiere = portiere;
        f_out.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(activity_register.this, "Account creato", Toast.LENGTH_SHORT).show();
                    userID = f_out.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("users").document(userID);
                    Map<String,Object> user = new HashMap<>();
                    user.put("UserName",Name);
                    user.put("eMail",mail);
                    user.put("Address",address);
                    user.put("GoalKeeper", finalPortiere);
                    documentReference.set(user);
                    startActivity(new Intent(getApplicationContext(), Dashboard.class));
                }else{
                    Toast.makeText(activity_register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}