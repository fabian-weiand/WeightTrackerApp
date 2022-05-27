package snhu.fabianweiand.weighttrackerapp;

//HEADER INCLUSIONS
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HistoricalData extends AppCompatActivity {

    //SET LOCAL VARIABLES
    DatabaseWeight Weightdb;
    private StringBuilder weightHistory;
    private Cursor data;
    private ImageView homeLogo;
    private Button bHome;
    private Button clWeight;
    private ListView lvWeight;
    private TextView txWeight;
    private TextView txTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_data);

        //TIE ACTIVITY ID'S TO LOCAL VARIABLES
        homeLogo = findViewById(R.id.imageLogo);
        bHome = findViewById(R.id.buttonHome);
        txWeight = findViewById(R.id.hisWeight);
        txTest = findViewById(R.id.textViewTest);
        clWeight = findViewById(R.id.buttonCLHistory);
        Weightdb = new DatabaseWeight(this);
        data = Weightdb.getListContents();
        weightHistory = new StringBuilder();

        //RETRIEVE DATABASE INFORMATION (HISTORY)
        if(data.getCount() == 0){
            txTest.setText("You have no Weight History");
        }
        else{
            while (data.moveToNext()){
                weightHistory.append("\n" + data.getString(3) + " - " + data.getString(1) + " lbs - " + data.getString(2) + " lbs");
                txTest.setText(weightHistory);
            }
        }

        //CLEAR HISTORY
        clWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(data.getCount() == 0){
                    toastMessage("There is No History to Clear");
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(HistoricalData.this);
                    builder.setMessage("Are You Sure You want to Clear All Data?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Weightdb.deleteAll();
                                    toMainAct();
                                    toastMessage("All History Has been Cleared");
                                }
                            })
                            .setNegativeButton("Cancel",null);
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        //BUTTON AND LOGO TO GO TO HOMEPAGE
        bHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMainAct();
            }
        });
        homeLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMainAct();
            }
        });
    }

    //METHOD TO RETURN TO HOME PAGE
    public void toMainAct(){
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }

    //TOAST METHOD
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}