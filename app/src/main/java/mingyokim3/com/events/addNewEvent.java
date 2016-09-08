package mingyokim3.com.events;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class addNewEvent extends AppCompatActivity {
    int year,month,day;
    private Calendar calendar;

    int final_year;
    int final_month;
    int final_day;

    ArrayList<String> titleList = new ArrayList<String>();
    ArrayList<String> descList = new ArrayList<String>();
    ArrayList<Integer> dayList = new ArrayList<Integer>();
    ArrayList<Integer> monthList = new ArrayList<Integer> ();
    ArrayList<Integer> yearList = new ArrayList<Integer> ();

    //EditText enterDate = (EditText) findViewById(R.id.eventDate);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event);

        calendar= Calendar.getInstance ();

        year= calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);

    }

    //Date
    @SuppressWarnings("deprecation")
    public void setDate (View view) {
        showDialog(999);
    }

    @SuppressWarnings("deprecation")
    protected Dialog onCreateDialog (int id) {
        DatePickerDialog dialog = new DatePickerDialog(this, myDateListener, year, month, day );
        return dialog;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet (DatePicker arg0, int fyear, int fmonth, int fday) {
            showDate(fyear, fmonth, fday);

        }
    };

    public void showDate (int fyear, int fmonth, int fday) {
        EditText enterDate = (EditText) findViewById(R.id.eventDate);
        enterDate.setText(new StringBuilder().append(fday).append("/").append(fmonth+1)
        .append("/").append(fyear));

        final_year=fyear;
        final_month=fmonth;
        final_day=fday;
    }

    //Done
    public void Done (View view) {
        EditText Title = (EditText) findViewById(R.id.eventTitle);
        EditText Desc = (EditText) findViewById(R.id.eventDesc);

        String sTitle = Title.getText().toString();
        String sDesc = Desc.getText().toString();

        //Arrays
        titleList.add(sTitle);
        descList.add(sDesc);

        dayList.add(final_day);
        monthList.add(final_month);
        yearList.add(final_year);


        Intent output= new Intent(this, MainActivity.class);


        output.putExtra("title",sTitle);
        output.putExtra("desc",sDesc);
        output.putExtra("year",final_year);
        output.putExtra("month",final_month);
        output.putExtra("day",final_day);

        //output
        /*
        output.putStringArrayListExtra("TitleList",titleList);
        output.putStringArrayListExtra("DescList", descList);

        output.putIntegerArrayListExtra("DayList",dayList);
        output.putIntegerArrayListExtra("MonthList",monthList);
        output.putIntegerArrayListExtra("YearList",yearList);
        */

        setResult(RESULT_OK,output);
        finish();
    }

}
