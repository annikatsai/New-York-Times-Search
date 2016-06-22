package annikatsai.nytimessearch.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import annikatsai.nytimessearch.DatePickerFragment;
import annikatsai.nytimessearch.R;

public class FilterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
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

        TextView tvDate = (TextView) findViewById(R.id.tvDate);
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        int day = view.getDayOfMonth();
        int month = view.getMonth() + 1;
        int getYear = view.getYear();

        String date = "" + String.valueOf(month) + "/" + String.valueOf(day)
                + "/" + String.valueOf(getYear);
        tvDate.setText(date);
    }

//    public void onCheckboxClicked(View view) {
//        // Is the view now checked?
//        boolean checked = ((CheckBox) view).isChecked();
//
//        // Check which checkbox was clicked
//        switch(view.getId()) {
//            case R.id.cbArts:
//                if (checked)
//                // Put some meat on the sandwich
//                else
//                // Remove the meat
//                break;
//            case R.id.cbFashion:
//                if (checked)
//                // Cheese me
//                else
//                // I'm lactose intolerant
//            case R.id.cbSports:
//                if (checked)
//                // Cheese me
//                else
//                // I'm lactose intolerant
//                break;
//        }
//    }

    public void onSubmit(View view) {
//        Intent intent = new Intent();
//
//        setResult(RESULT_OK, intent);
        finish();
    }
}
