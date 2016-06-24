package annikatsai.nytimessearch.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.Calendar;

import annikatsai.nytimessearch.DatePickerFragment;
import annikatsai.nytimessearch.R;
import annikatsai.nytimessearch.SearchFilters;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    private Calendar c = Calendar.getInstance();
    String queryArts = "";
    String queryFashion = "";
    String querySports = "";
    String sort = "";
    int day, month, getYear;

    @BindView(R.id.spinner) Spinner spinner;
    @BindView(R.id.tvDate) TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        //Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sortOrder_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        sort = (String) parent.getItemAtPosition(pos);
        Log.d("Sort Type", sort);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

     //attach to an onclick handler to show the date picker
    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // handle the date selected
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // store the values selected into a Calendar instance
        // final Calendar c = Calendar.getInstance();

        //TextView tvDate = (TextView) findViewById(R.id.tvDate);
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        day = view.getDayOfMonth();
        month = view.getMonth() + 1;
        getYear = view.getYear();

        String date = "" + String.valueOf(month) + "/" + String.valueOf(day)
                + "/" + String.valueOf(getYear);
        tvDate.setText(date);
    }

//    @OnClick({R.id.cbArts, R.id.cbSports, R.id.cbFashion})
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
//        String queryArts;
//        String queryFashion;
//        String querySports;

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.cbArts:
                if (checked)
                    queryArts = "Arts";
                else
                    queryArts = "";
                break;
            case R.id.cbFashion:
                if (checked)
                    queryFashion = "Fashion & Style";
                else
                    queryFashion = "";
                break;
            case R.id.cbSports:
                if (checked)
                    querySports = "Sports";
                else
                    querySports = "";
                break;
        }
    }

    public void onSubmit(View view) {
        String returnArts, returnFashion, returnSports;
        String newMonth, newDay;

        String returnSort = sort;

        if (month < 10)
            newMonth = "0" + String.valueOf(month);
        else
            newMonth = String.valueOf(month);
        if (day < 10)
            newDay = "0" + String.valueOf(day);
        else
            newDay = String.valueOf(day);
        String returnDate = String.valueOf(getYear) + newMonth + newDay;

        returnArts = queryArts;
        returnFashion = queryFashion;
        returnSports = querySports;

        String query = "";
        if (!returnArts.isEmpty()) {
            query += returnArts;
            if (!returnFashion.isEmpty()) {
                query += (", " + returnFashion);
                if (!returnSports.isEmpty()) {
                    query += ", " + returnSports;
                }
            }
            else {
                if (!returnSports.isEmpty()) {
                    query += returnSports;
                }
            }

        } else {
            if (!returnFashion.isEmpty()) {
                query += returnFashion;
                if (!returnSports.isEmpty()) {
                    query += ", " + returnSports;
                }
            }
            else {
                if (!returnSports.isEmpty()) {
                    query += returnSports;
                }
            }
        }

        SearchFilters searchFilters = new SearchFilters(returnDate, returnSort, query);

        Intent intent = new Intent();
        intent.putExtra("filter", Parcels.wrap(searchFilters));
        setResult(RESULT_OK, intent);
        finish();
    }
}
