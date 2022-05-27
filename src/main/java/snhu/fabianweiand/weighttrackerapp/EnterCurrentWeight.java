package snhu.fabianweiand.weighttrackerapp;


//HEADER INCLUSIONS
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EnterCurrentWeight extends AppCompatActivity {

    //SET LOCAL VARIABLES
    DatabaseWeight mDatabaseWeight;
    private TextView cDateText;
    private String myDate_Str;
    private EditText newCurrentWeight;
    private Button submitCurrentWeight;
    private ImageView homeLogo;
    private String tWeightText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_current_weight);

        //TIE ACTIVITY ID'S TO LOCAL VARIABLES
        myDate_Str = new SimpleDateFormat("MMM.dd.yyyy ' / ' HH:mm:ss z", Locale.getDefault()).format(new Date());
        cDateText = findViewById(R.id.textCurrentDate);
        cDateText.setText(myDate_Str);
        homeLogo = findViewById(R.id.imageLogo);
        newCurrentWeight = findViewById(R.id.editCurrentWeight);
        submitCurrentWeight = findViewById(R.id.buttonCurrentSubmit);
        mDatabaseWeight = new DatabaseWeight(this);

        //GET TARGET FROM SP FOR INPUT IN SQLITE DATABASE
        SharedPreferences settWeight = getSharedPreferences("myPref",0);
        if(settWeight.contains("Key_2")){
            tWeightText = settWeight.getString("Key_2",null);
        } else {
            tWeightText = "Boo";
        }

        //INPUT TARGET AND CURRENT WEIGHT INTO SP AND SQLITE DATABASE
        submitCurrentWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newcWeight = newCurrentWeight.getText().toString();
                if (newCurrentWeight.length() != 0){
                    SharedPreferences saveCWeight = getSharedPreferences("myPref", 0);
                    SharedPreferences.Editor editor = saveCWeight.edit();
                    editor.putString("Key_1", newcWeight);
                    editor.apply();
                    AddWeight(tWeightText, newcWeight, myDate_Str);
                    toMainAct();
                }
                else{
                    toastMessage("You Must Enter Weight to Submit");
                }
            }
        });

        //CLICK LOGO TO GO TO HOME PAGE
        homeLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMainAct();
            }
        });
    }

    //METHOD FOR ADDING WEIGHT INTO DATABASE
    public void AddWeight(String ntWeight, String ncWeight, String nDate){
        boolean insertWeight = mDatabaseWeight.addData(ntWeight, ncWeight, nDate);
        if (insertWeight) {
            toastMessage("New Weight Entered Successfully!");
        }
        else {
            toastMessage("Something Went Wrong");
        }
    }

    //METHOD FOR RETURNING TO HOME PAGE
    public void toMainAct(){
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }

    //TOAST METHOD
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
