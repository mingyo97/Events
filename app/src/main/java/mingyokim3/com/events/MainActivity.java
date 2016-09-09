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

    //flags
    int Bold=1;
    int notBold=-1;

    //ID for a specific row of events
    public int row_ID=0;
    //ID for a speciic button
    public int Btn_id=0;
    //number used to identify things by their id
    public int loc;
    //ID for all texts in a "row of events"
    public int textID=0;
    //ID for individual text fields of event, date, and description
    public int titleID=0;
    public int dateID=1;
    public int noteID=2;

    public static int request_newEvent=1;
    public static int request_editEvent=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Activated by pressing the button "Add Event" to start a new activity "addNewEvent"
    public void newEvent(View view) {
        Intent c= new Intent(this, addNewEvent.class );

        //Set a request code to identify which activity we are obtaining data from at "onActivityResult"
        startActivityForResult(c,request_newEvent);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (requestCode == request_newEvent && resultCode == RESULT_OK && data!= null) {
            //Identify which layout we are going to add the new rows of text fields and buttons
            //In this case it is the main layout (mLayout)
            LinearLayout mLayout = (LinearLayout) findViewById(R.id.linearLayout);

            //Obtain user input from the activity "addNewEvent"
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

            //Display the content of the new event onto the main activity in a specified format
            createRowString("Event", Title, mLayout);
            createRowDate("Date", year, month, day, mLayout);
            createRowString("Note", Desc, mLayout);

            //Add on the "Edit" and "Delete" buttons
            createButtons(mLayout);

            //Add a divider (a horizontal line) to separate rows of events
            mLayout.addView(createNewDivider());

            row_ID++;
        }
        else if (requestCode == request_editEvent && resultCode == RESULT_OK && data!= null) {
            LinearLayout mLayout = (LinearLayout) findViewById(R.id.linearLayout);

            //Obtain the input edited by the user
            Title=data.getStringExtra("title");
            Desc=data.getStringExtra("desc");
            int year = data.getIntExtra("year",0);
            int month = data.getIntExtra("month",0);
            int day = data.getIntExtra("day",0);

            //edit the text view in the main layout according to user input
            editTextView(loc,Title,titleID);
            editTextView(loc,Desc,noteID);

            //Empty Date Entry
            if(day==0)
                editDateTextView(loc,dayList.get(loc),monthList.get(loc)+1,yearList.get(loc),dateID);
            else
                editDateTextView(loc,day,month,year, dateID);

        }

    }

    //Called after the user returns from "editEvent" in the function "onActivityResult"
    //Creates string based on what type of data (Title, date, or description), the content, and layout format
    public void createRowString (String subject, String content, LinearLayout mLayout) {
        //Create a (sub)layout that will be added on to the main layout
        LinearLayout hLayout = new LinearLayout(this);
        hLayout.setOrientation(LinearLayout.HORIZONTAL);

        //Add the info about the type of data (ex: "Event: ...."); it is bold
        hLayout.addView(createNewTextView(subject + ": ", Bold));
        //Add the content; it is not bold
        hLayout.addView(createNewTextView(content,notBold));

        //Add the (sub)layout to the main layout
        mLayout.addView(hLayout);
    }

    //Called after the user returns from "editEvent" in the function "onActivityResult"
    //Creates a row of date based on the type of data, the date, and the layout to add data to
    public void createRowDate (String subject, int y, int m, int d, LinearLayout mLayout) {
        //new sublayout to be added to the main layout
        LinearLayout hLayout = new LinearLayout(this);
        hLayout.setOrientation(LinearLayout.HORIZONTAL);

        //Add info about the data (ex: "Date: ...")
        hLayout.addView(createNewTextView(subject + ": ", Bold));
        hLayout.addView(createNewTextView(d + "/" + (m+1) + "/" + y,notBold));
        //Add the sublayout to the main layout
        mLayout.addView(hLayout);
    }

    //Creates buttons. Parameter is the layout we want to add the buttons to
    public void createButtons (LinearLayout mLayout) {
        //Create a new sublayout
        LinearLayout hLayout=new LinearLayout (this);
        hLayout.setOrientation(LinearLayout.HORIZONTAL);

        //Add space before the buttons so that they are aligned to the right of the screen
        LinearLayout.LayoutParams spaceParams = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, 2l);

        Space space = new Space(this);
        space.setLayoutParams(spaceParams);

        hLayout.addView(space);

        //initialize buttons
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, 1l);
        Button edit = new Button(this);
        Button delete = new Button(this);

        //Buttons ID
        edit.setId(Btn_id);
        Btn_id++;
        delete.setId(Btn_id);
        Btn_id++;

        //Set layout parameters to the new buttons
        edit.setLayoutParams(buttonParams);
        delete.setLayoutParams(buttonParams);

        //Set the text to the new buttons and add a listener
        edit.setText(R.string.edit);
        edit.setOnClickListener(editListener);

        delete.setText(R.string.delete);

        //add the buttons to the sublayout
        hLayout.addView(edit);
        hLayout.addView(delete);

        //Merge into main layout
        mLayout.addView(hLayout);
    }

    //Input is the text that we want to display and either 1 or 0 whether we want the text bold or not
    public TextView createNewTextView (String text, int bold) {
        //Set the layout parameters for the text field to be created
        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView (this);

        //Set the parameters to the textView
        textView.setLayoutParams(lparams);

        //Set the text and font size to the textView
        textView.setText(text);
        textView.setTextSize(25);

        //Set the font to the textView
        Typeface face = Typeface.SANS_SERIF;
        textView.setTypeface(face);

        //Make the text bold depending on user input
        //The text containing user input is not bold, so we need a separate ID from row_ID in case the user presses the "Edit" button
        if(bold==Bold) {
            textView.setTypeface(null, Typeface.BOLD);
            textView.setId(row_ID);
        }
        else{
            //TextView ID
            textView.setId(textID);
            textID++;
        }

        return textView;
    }

    //Creates a new divider after user adds an event
    public View createNewDivider () {
        final View divider = new View (this);
        divider.setLayoutParams( new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,8));
        divider.setBackgroundColor(Color.parseColor("#ffdbdbdb"));
        divider.setId(row_ID);
        return divider;
    }

    //Editing the "Title" and "Description" text view after user edits an event
    public void editTextView (int location, String content, int ID) {
        TextView newText = (TextView) findViewById(location*3+ID);
        newText.setText(content);
    }

    //Editing the "Date" text view after user edits an event
    public void editDateTextView (int location, int newDay, int newMonth, int newYear, int ID) {
        TextView newText = (TextView) findViewById(location*3+ID);
        newText.setText(newDay + "/" + newMonth + "/" + newYear);
    }

    //Listen for user to press the "Edit" button
    View.OnClickListener editListener = new View.OnClickListener() {
        public void onClick (View v) {
            //Find the index of the content that the user wishes to edit in their corresponding arrays
            loc = v.getId()/2;
            //start a new activity "editEvent"
            startIntent();

        }
    };

    //Start activity for "editEvent" when the user presses the "Edit" button
    public void startIntent() {
        Intent d = new Intent(this, editEvent.class);

        //Pass on the content that the user wants to edit so that they are displayed before they start editing
        d.putExtra("Title", titleList.get(loc));
        d.putExtra("Desc", descList.get(loc));
        d.putExtra("Day", dayList.get(loc));
        d.putExtra("Month", monthList.get(loc));
        d.putExtra("Year", yearList.get(loc));

        //Set a request code for "onActivityResult"
        startActivityForResult(d, request_editEvent);
    }


}
