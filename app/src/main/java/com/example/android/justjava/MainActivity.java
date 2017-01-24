    package com.example.android.justjava;

    import android.content.Intent;
    import android.net.Uri;
    import android.os.Bundle;
    import android.support.v7.app.AppCompatActivity;
    import android.util.Log;
    import android.view.View;
    import android.widget.CheckBox;
    import android.widget.EditText;
    import android.widget.TextView;
    import android.widget.Toast;

    /**
     * This app displays an order form to order coffee.
     */
    public class MainActivity extends AppCompatActivity {
        int quantity = 2;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        }

        /**
         * This method displays the given plus is clicked.
         */
        public void increment(View view) {
        if (quantity==100){
            Toast.makeText(this,"You cannot have more than 100 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
            quantity = quantity + 1;
            displayQuantity(quantity);
        }

        /**
         * This method displays the given minus is clicked.
         */
        public void decrement(View view) {
            if (quantity == 1) {
                Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
                return;
            }
            quantity--;
            displayQuantity(quantity);
        }

        /**
         * This method is called when the order button is clicked.
         */
        public void submitOrder(View view) {
            CheckBox isChecked = (CheckBox) findViewById(R.id.checkBox);
            boolean hasWippedCream = isChecked.isChecked();
            CheckBox withChoco = (CheckBox) findViewById(R.id.checkBoxChoco);
            boolean hasChoco = withChoco.isChecked();
            int price = calculatePrice(hasWippedCream,hasChoco);
            EditText et = (EditText) findViewById(R.id.editText);
            String name = et.getText().toString();
            if (name.isEmpty())
                name = "Without name";
            String priceMessage = createOrderSummary(price, hasWippedCream, hasChoco, name);

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for "+name);
            intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

//            Intent intent=new Intent(Intent.ACTION_SEND);
//            intent.setType("text/plain");
//            intent.putExtra(Intent.EXTRA_SUBJECT,"Just Java order for "+name);
//            intent.putExtra(Intent.EXTRA_EMAIL,priceMessage);
//            try {
//                startActivity(Intent.createChooser(intent, "Send mail..."));
//            } catch (android.content.ActivityNotFoundException ex) {
//                Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
//            }


          //  displayMessage(priceMessage);
        }

        /**
         * Calculates the price of the order.
         *
         * @return total price
         */
        private int calculatePrice(boolean hasWippedCream, boolean hasChoco) {
            int price = 5;
            if (hasWippedCream){
                price=price+1;
            }
            if (hasChoco){
                price=price+2;
            }
            return price*quantity;
        }

        private String createOrderSummary(int price, boolean wasAdeedWippedCream, boolean hasChoco, String name) {
            String priceMessage = "Name: " + name;
            priceMessage = priceMessage + "\nAdd Wipped Cream? " + wasAdeedWippedCream;
            priceMessage = priceMessage + "\nAdd Chocolate? " + hasChoco;
            priceMessage = priceMessage + "\nQuantity: " + quantity;
            priceMessage = priceMessage + "\nTotal $: " + price;
            priceMessage = priceMessage + "\nThank You";
            return priceMessage;
        }

        /**
         * This method displays the given quantity value on the screen.
         */
        private void displayQuantity(int number) {

            TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
            quantityTextView.setText("" + number);

        }


        /**
         * This method displays the given text on the screen.
         */
        private void displayMessage(String message) {
            TextView orderSummaryTextVew = (TextView) findViewById(R.id.order_summary_text_view);
            orderSummaryTextVew.setText(message);
        }
    }