package snhu.fabianweiand.weighttrackerapp;


//HEADER INCLUSIONS
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginScreen extends AppCompatActivity {

    //SET LOCAL VARIABLES
    private EditText mTextUserName;
    private EditText mTextPassword;
    private TextView mTextLogin;
    private TextView mForgotPassword;
    private TextView mNewMember;
    private DatabaseUsers db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        //TIE ACTIVITY ID'S TO LOCAL VARIABLES
        db = new DatabaseUsers(this);
        mTextUserName = findViewById(R.id.userSubmitText);
        mTextPassword = findViewById(R.id.passwordSubmitText);
        mTextLogin = findViewById(R.id.textLogin);
        mForgotPassword = findViewById(R.id.forgotpwText);
        mNewMember = findViewById(R.id.signupText);

        //CALL REGISTER NEW MEMBER
        mNewMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewMember();
            }
        });

        //ACCESS REQUEST TO HOME PAGE
        mTextLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mTextUserName.getText().toString().trim();
                String password = mTextPassword.getText().toString().trim();
                Boolean result = db.checkLoginCreds(user, password);
                if(result == true){
                    grantMainAct();
                }
                else{
                    toastMessage("Login Credentials are Incorrect");
                }
            }
        });

        //OPEN FORGOT PASSWORD
        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPass();
            }
        });
    }

    //METHOD FOR OPENING CREATE ACCOUNT ACTIVITY
    public void openNewMember(){
        Intent intent = new Intent (this, CreateAccount.class);
        startActivity(intent);
    }

    //METHOD FOR OPENING HOME PAGE ACTIVITY
    public void grantMainAct(){
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }

    //METHOD FOR OPENING RESET PASSWORD
    public void resetPass(){
        Intent intent = new Intent (this, ResetPassword.class);
        startActivity(intent);
    }

    //TOAST METHOD
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}