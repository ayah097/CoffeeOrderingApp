package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    int quantity = 2;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whippedCreamCheckBox);
        boolean haswhippedCream = whippedCreamCheckBox.isChecked();
        // Figure out if the user wants chocolate
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolateCheckBox);
        boolean hasChocolate = chocolateCheckbox.isChecked();
        // Calculate the price
        int price = calculatePrice(haswhippedCream, hasChocolate);
        // Gets the name order summary on the screen
        EditText customerName = (EditText) findViewById(R.id.name_field);
        String cName = customerName.getText().toString();
        // Gets the order summary on the screen
        String priceMessage = createOrderSummary(price, haswhippedCream, hasChocolate, cName);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + cName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        // Display the order summary on the screen
        //displayMessage(priceMessage);

    }

    /**
     * Calculates the price of the order.
     *@param addWhippedCream is whether or not user wants whipped cream topping
     *@param addChocolate is whether or not user wants chocolate topping
     *@return is the total number of cups of coffee ordered
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        //price per cup of coffee
        int basePrice = 5;
        //add $1 if the user wants whipped cream
        if(addWhippedCream == true){
            basePrice =+ 1;
        //add $1 if the user wants chocolate
        }
        if (addChocolate == true){
            basePrice =+ 2;
        //calculate total order price multiplied by quantity
        }
        return quantity * basePrice;
    }

    /**
     *@param cName of the customer
     *@param price of the order
     *@param addWhippedCream is whether or not user wants whipped cream topping
     *@param addChocolate is whether or not user wants chocolate topping
     *@return text summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String cName) {
        String priceMessage = getString(R.string.order_summary_name, cName);
        priceMessage += "\n" + getString(R.string.order_summary_whippedCream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }


    /**
     * This method is called when the + button is clicked.
     */

    public void increment(View view) {
        if (quantity == 100) {
            //Show error Message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            //Exit method early
            return;
        }
        quantity = quantity + 1;
        display(quantity);

    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            //Show error Message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            //Exit method early
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);

    }

    /**
     * This method displays the given price on the screen.
     *
     * @param number
     */
    private void displayPrice(int number) {
        TextView order_summary_text_view = (TextView) findViewById(R.id.order_summary_text_view);
        order_summary_text_view.setText(NumberFormat.getCurrencyInstance().format(number));
    }

}

