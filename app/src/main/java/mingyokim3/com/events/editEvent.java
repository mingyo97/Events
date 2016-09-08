package mingyokim3.com.events;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class editEvent extends AppCompatActivity {
    int day, month, year;
    int final_day, final_month, final_year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event2);

        EditText preTitle = (EditText) findViewById(R.id.eventTitle);
        EditText preDesc = (EditText) findViewById(R.id.eventDesc);
        EditText preDate = (EditText) findViewById(R.id.eventDate);

        //Intent
        Intent edit = getIntent();
        String oldTitle = edit.getStringExtra("Title");
        String oldDesc = edit.getStringExtra("Desc");

        day=edit.getIntExtra("Day",0);
        month=edit.getIntExtra("Month",0);
        year=edit.getIntExtra("Year",0);

        preTitle.setText(oldTitle);
        preDate.setText(day + "/" + (month+1) + "/" + year);
        preDesc.setText(oldDesc);
        //int num=edit.getIntExtra("num",0);
        //preTitle.setText(num);
    }

    @SuppressWarnings("deprecation")
    public void setDate (View view) {
        showDialog(999);
    }

    @SuppressWarnings("deprecation")
    protected Dialog onCreateDialog (int id) {
        DatePickerDialog dialog = new DatePickerDialog(this, myDateListener, year, month, day);
        return dialog;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet (DatePicker arg0, int fyear, int fmonth, int fday) {
            showDate(fyear,fmonth+1,fday);
        }
    };

    public void showDate (int fyear, int fmonth, int fday) {
        EditText preDate = (EditText) findViewById(R.id.eventDate);
        preDate.setText(fday + "/" + fmonth + "/" + fyear);
        final_day=fday;
        final_month=fmonth;
        final_year=fyear;
    }

    public void Done (View view) {
        EditText Title = (EditText) findViewById(R.id.eventTitle);
        EditText Desc = (EditText) findViewById(R.id.eventDesc);

        String sTitle = Title.getText().toString();
        String sDesc = Desc.getText().toString();


        //Date


        Intent output= new Intent(this, MainActivity.class);


        output.putExtra("title",sTitle);
        output.putExtra("desc",sDesc);

        output.putExtra("year",final_year);
        output.putExtra("month",final_month);
        output.putExtra("day",final_day);


        setResult(RESULT_OK,output);
        finish();
    }
}
