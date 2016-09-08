package mingyokim3.com.events;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public String Title;
    String Desc;

    public ArrayList<String> titleList = new ArrayList<String>();
    ArrayList<String> descList = new ArrayList<String>();
    ArrayList<Integer> dayList = new ArrayList<Integer>();
    ArrayList<Integer> monthList = new ArrayList<Integer> ();
    ArrayList<Integer> yearList = new ArrayList<Integer> ();

    int Bold=1;
    int notBold=-1;
    public int count=0;
    public int id=0;
    public int loc;
    public int textID=0;

    public int titleID=0;
    public int dateID=1;
    public int noteID=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void newEvent(View view) {
        Intent c= new Intent(this, addNewEvent.class );
        startActivityForResult(c,1);
    }


    public TextView createNewTextView (String text, int bold) {
        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView (this);

        //TextView Params
        textView.setLayoutParams(lparams);

        textView.setText(text);
        textView.setTextSize(25);

        Typeface face = Typeface.SANS_SERIF;
        textView.setTypeface(face);

        if(bold==Bold)
            textView.setTypeface(null, Typeface.BOLD);
        else{
            //TextView ID
            textView.setId(textID);
            textID++;
        }

        return textView;
    }

    public View createNewDivider () {
        final View divider = new View (this);
        divider.setLayoutParams( new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,8));
        divider.setBackgroundColor(Color.parseColor("#ffdbdbdb"));
        divider.setId(count);
        return divider;
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data!= null) {
            LinearLayout mLayout = (LinearLayout) findViewById(R.id.linearLayout);

            Title=data.getStringExtra("title");
            Desc=data.getStringExtra("desc");
            int year = data.getIntExtra("year",0);
            int month = data.getIntExtra("month",0);
            int day = data.getIntExtra("day",0);

            //Add input to Arrays
            titleList.add(Title);
            descList.add(Desc);
            dayList.add(day);
            monthList.add(month);
            yearList.add(year);

            createRowString("Event", Title, mLayout);
            createRowDate("Date", year, month, day, mLayout);
            createRowString("Note", Desc, mLayout);

            createButtons(mLayout);

            mLayout.addView(createNewDivider());

            count++;
        }
        else if (requestCode == 2 && resultCode == RESULT_OK && data!= null) {
            LinearLayout mLayout = (LinearLayout) findViewById(R.id.linearLayout);


            Title=data.getStringExtra("title");
            Desc=data.getStringExtra("desc");
            int year = data.getIntExtra("year",0);
            int month = data.getIntExtra("month",0);
            int day = data.getIntExtra("day",0);

            editTextView(loc,Title,titleID);
            editTextView(loc,Desc,noteID);

            //Empty Date Entry
            if(day==0)
                editDateTextView(loc,dayList.get(loc),monthList.get(loc)+1,yearList.get(loc),dateID);
            else
                editDateTextView(loc,day,month,year, dateID);

        }

    }

    public void editTextView (int location, String content, int ID) {
        TextView newText = (TextView) findViewById(location+location*2+ID);
        newText.setText(content);
    }

    public void editDateTextView (int location, int newDay, int newMonth, int newYear, int ID) {
        TextView newText = (TextView) findViewById(location+location*2+ID);
        newText.setText(newDay + "/" + newMonth + "/" + newYear);

    }

    public void createRowString (String subject, String content, LinearLayout mLayout) {
        LinearLayout hLayout = new LinearLayout(this);
        hLayout.setOrientation(LinearLayout.HORIZONTAL);

        hLayout.addView(createNewTextView(subject + ": ", Bold));
        hLayout.addView(createNewTextView(content,notBold));
        mLayout.addView(hLayout);
    }

    public void createRowDate (String subject, int y, int m, int d, LinearLayout mLayout) {
        LinearLayout hLayout = new LinearLayout(this);
        hLayout.setOrientation(LinearLayout.HORIZONTAL);

        hLayout.addView(createNewTextView(subject + ": ", Bold));
        hLayout.addView(createNewTextView(d + "/" + (m+1) + "/" + y,notBold));
        mLayout.addView(hLayout);
    }

    public void createButtons (LinearLayout mLayout) {
        //Horizontal Layout
        LinearLayout hLayout=new LinearLayout (this);
        hLayout.setOrientation(LinearLayout.HORIZONTAL);

        //Space
        LinearLayout.LayoutParams spaceParams = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, 2l);

        Space space = new Space(this);
        space.setLayoutParams(spaceParams);

        hLayout.addView(space);

        //Buttons
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, 1l);

        Button edit = new Button(this);
        Button delete = new Button(this);

        //Buttons ID
        edit.setId(id);
        id++;
        delete.setId(id);
        id++;

        //ButtonsParams
        edit.setLayoutParams(buttonParams);
        edit.setText(R.string.edit);
        edit.setOnClickListener(editListener);

        delete.setLayoutParams(buttonParams);
        delete.setText(R.string.delete);

        //Horizontal layout
        hLayout.addView(edit);
        hLayout.addView(delete);

        //Merge into root layout
        mLayout.addView(hLayout);


    }

    View.OnClickListener editListener = new View.OnClickListener() {
        public void onClick (View v) {
            loc = v.getId()/2;
            //test();
            startIntent();

        }
    };


    public void startIntent() {
        Intent d = new Intent(this, editEvent.class);

        d.putExtra("Title", titleList.get(loc));
        d.putExtra("Desc", descList.get(loc));
        d.putExtra("Day", dayList.get(loc));
        d.putExtra("Month", monthList.get(loc));
        d.putExtra("Year", yearList.get(loc));
        startActivityForResult(d, 2);
    }


}
