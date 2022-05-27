package snhu.fabianweiand.weighttrackerapp;

//HEADER INCLUSIONS
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ResetPassword extends AppCompatActivity {


    private EditText userName;
    private TextView submitUName;
    private ImageView homeLogo;
    DatabaseUsers userpassdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        //TIE ACTIVITY ID'S TO LOCAL VARIABLES
        homeLogo = findViewById(R.id.imageLogo);
        submitUName = findViewById(R.id.resetPasswordSubmit);
        userName = findViewById(R.id.userUsername);
        userpassdb = new DatabaseUsers(this);

        //LOGO CLICK TO OPEN HOME PAGE
        homeLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMainAct();
            }
        });

        //SUBMIT USER NAME TO RESET PASSWORD
        submitUName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = userName.getText().toString();
                Boolean checkUser = userpassdb.checkUsername(user);
                if(checkUser==true){
                    toUpdatePass(user);
                }
                else{
                    toastMessage("The Username Does Not Exist");
                }
            }
        });
    }

    //METHOD TO OPEN HOME PAGE
    public void toMainAct(){
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }

    //METHOD TO UPDATE PASSWORD
    public void toUpdatePass(String user){
        Intent intent = new Intent (this, UpdatePassword.class);
        intent.putExtra("username", user);
        startActivity(intent);
    }

    //TOAST METHOD
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}