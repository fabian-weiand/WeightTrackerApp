package snhu.fabianweiand.weighttrackerapp;

//HEADER INCLUSIONS
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdatePassword extends AppCompatActivity {

    //SET LOCAL VARIABLES
    private EditText upPass;
    private EditText upPassCon;
    private Button butSubPass;
    private TextView uName;
    DatabaseUsers passdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        //TIE ACTIVITY ID'S TO LOCAL VARIABLES
        upPass = findViewById(R.id.passwordUpdate);
        upPassCon = findViewById(R.id.passwordUpdateConfirm);
        butSubPass = findViewById(R.id.buttonSubmitNewPass);
        uName = findViewById(R.id.username);
        passdb = new DatabaseUsers(this);
        Intent intent = getIntent();
        uName.setText(intent.getStringExtra("username"));

        //SUBMIT NEW PASSWORD
        butSubPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = uName.getText().toString();
                String password = upPass.getText().toString();
                String passwordCon = upPassCon.getText().toString();

                //IF PASSWORDS MATCH, PASS NEW PASSWORD TO DATABASE
                if(password.equals(passwordCon)){
                    boolean checkPassUpdate = passdb.updatepassword(user, password);
                    if(checkPassUpdate==true){
                        toLogin();
                        toastMessage("Your Password has been updated");
                    }
                    else{
                        toastMessage("Your Password did not update, please try again");
                    }
                }
                else{
                    toastMessage("Your Passwords do not Match");
                }
            }
        });
    }

    //TOAST METHOD
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //OPEN LOGIN ACTIVITY METHOD
    public void toLogin(){
        Intent intent = new Intent (this, LoginScreen.class);
        startActivity(intent);
    }
}