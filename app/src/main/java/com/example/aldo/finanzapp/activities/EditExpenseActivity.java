package com.example.aldo.finanzapp.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aldo.finanzapp.Fragments.DatePickerFragment;
import com.example.aldo.finanzapp.R;
import com.example.aldo.finanzapp.models.Bills;
import com.example.aldo.finanzapp.models.BillsDAO;

/**
 * Created by Mathieu on 29/08/2016.
 */

public class EditExpenseActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMG = 1;
    private BillsDAO billsDAO;
    private Bills bills;
    private Bundle data;
    private Uri selectedImage;
    private String imgDecodableString;
    private int hasImage = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //creation of the view to add an expense
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_add_expense);
        android.support.v7.widget.Toolbar toolbar_expense = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_expense);
        setSupportActionBar(toolbar_expense);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textViewTitleToolbar = (TextView) findViewById(R.id.toolbar_title);
        textViewTitleToolbar.setText("Modificar cuenta");

        data  = getIntent().getExtras();
        EditText editTitle = (EditText) findViewById(R.id.input_text_expense_title);
        editTitle.setText(data.getString("ExpenseTitle"));
        EditText editAmount = (EditText) findViewById(R.id.input_text_amount);
        editAmount.setText(data.getString("ExpenseAmount"));
        EditText editDescription = (EditText) findViewById(R.id.input_text_description);
        editDescription.setText(data.getString("ExpenseDescription"));
        Button buttonDate = (Button) findViewById(R.id.button_calendar2);
        buttonDate.setText(data.getString("ExpenseFinishDate"));

        if (data.getString("ExpenseImage").length() > 1 ){
            hasImage = 1;
            selectedImage = Uri.parse(data.getString("ExpenseImage"));
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            // Get the cursor
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imgDecodableString = cursor.getString(columnIndex);
            cursor.close();
            ImageView imgView = (ImageView) findViewById(R.id.imgFactureView);
            // Set the Image in ImageView after decoding the String
            imgView.setImageBitmap(BitmapFactory
                    .decodeFile(imgDecodableString));
        }
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.activity_add_expense, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        else if (id == R.id.nav_valid){
            EditText editTitle = (EditText) findViewById(R.id.input_text_expense_title);
            String title = editTitle.getText().toString();
            EditText editAmount = (EditText) findViewById(R.id.input_text_amount);
            String amount = editAmount.getText().toString();
            EditText editDescription = (EditText) findViewById(R.id.input_text_description);
            String description = editDescription.getText().toString();
            Button buttonDate = (Button) findViewById(R.id.button_calendar2);
            String date = buttonDate.getText().toString();

            // If the title is empty
            if (title.equals("")){
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, " entrar un titulo ", duration);
                toast.show();
            }
            else if (date.equals("Fecha")){
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, " entrar una fecha ", duration);
                toast.show();
            }
            else if (amount.equals("")){
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, " entrar un monto ", duration);
                toast.show();
            }
            else{
                billsDAO = new BillsDAO(this);
                billsDAO.open();
                Bills bill;
                date = billsDAO.changeDateFormat(date);

                if (hasImage == 0){
                    bill = new Bills(title,amount, date, description,"");
                }
                else {
                    bill = new Bills(title, amount, date, description, selectedImage.toString());
                }
                billsDAO.updateBill(bill,data.getString("ExpenseTitle"),data.getString("ExpenseAmount"),
                        data.getString("ExpenseFinishDate"));
                Intent intent = new Intent(EditExpenseActivity.this,ListarCuentas.class);
                startActivity(intent);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void loadImageGallery(View v){
        //create intent to open image application
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent,RESULT_LOAD_IMG);
    }

    @Override
    // Display the image picked
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                selectedImage = data.getData();
                hasImage = 1;
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.imgFactureView);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void showImage(View v){

    }
}

