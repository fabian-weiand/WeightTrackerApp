package snhu.fabianweiand.weighttrackerapp;

//HEADER INCLUSIONS
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateAccount extends AppCompatActivity {

    //SET LOCAL VARIABLES
    private EditText mCreateUserName;
    private EditText mCreatePassword;
    private EditText mConfirmPassword;
    private EditText mPhoneNumber;
    private TextView mTextCreate;
    private DatabaseUsers db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //CONNECT ACTIVITY ID'S WITH LOCAL VARIABLES
        db = new DatabaseUsers(this);
        mCreateUserName = findViewById(R.id.createUserName);
        mCreatePassword = findViewById(R.id.createUserPassword);
        mConfirmPassword = findViewById(R.id.createConfPassword);
        mPhoneNumber = findViewById(R.id.createCellNumber);
        mTextCreate = findViewById(R.id.CreateLoginSubmitText);

        //CREATE USER INFORMATION AND PASS IT TO CREDENTIAL DATABASE
        mTextCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mCreateUserName.getText().toString().trim();
                String password = mCreatePassword.getText().toString().trim();
                String confpassword = mConfirmPassword.getText().toString().trim();
                String cellnumber = mPhoneNumber.getText().toString().trim();

                //CONFIRM THAT PASSWORDS MATCH AND SEND INFORMATION TO DATABASE HANDLER
                if(password.equals(confpassword)){
                    long checkdb = db.addNewMember(user, password, cellnumber);
                    SharedPreferences saveCell = getSharedPreferences("myPref", 0);
                    SharedPreferences.Editor editor = saveCell.edit();
                    editor.putString("Key_3", cellnumber);
                    editor.apply();
                    if(checkdb>0){
                        toastMessage("You Are Now Registered");
                        openLoginScreen();
                    }
                    else{
                        toastMessage("Registration Not Valid, PLease try again");
                    }
                }
                else{
                    toastMessage("Your Passwords Do Not Match");
                }
            }
        });
    }

    //METHOD TO OPEN LOGIN SCREEN
    public void openLoginScreen(){
        Intent intent = new Intent (this, LoginScreen.class);
        startActivity(intent);
    }

    //TOAST METHOD
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}