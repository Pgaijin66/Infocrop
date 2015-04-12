package crop.parser.com.crop_final;

/**
 * Created by rohit on 4/11/15.
 */

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewProductActivity extends Activity {

    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    EditText longitude;
    EditText latitude;
    EditText inputpest;
    EditText infectedDate;
    EditText currentdate;
    EditText areaaffected;
    EditText info;

    // url to create new product
    private static String url_create_product = "http://10.0.2.2/android_connect/create_product.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        // Edit Text
        longitude = (EditText) findViewById(R.id.longitude);
        latitude = (EditText) findViewById(R.id.latitude);
        inputpest = (EditText) findViewById(R.id.inputpest);
        infectedDate = (EditText) findViewById(R.id.infecteddate);
        currentdate = (EditText) findViewById(R.id.currentdate);
        areaaffected = (EditText) findViewById(R.id.areaaffected);
        info = (EditText) findViewById(R.id.info);

        // Create button
        Button btnCreateProduct = (Button) findViewById(R.id.btnCreateProduct);

        // button click event
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread
                new CreateNewProduct().execute();

                

            }
        });
    }

    /**
     * Background Async Task to Create new product
     * */
    class CreateNewProduct extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewProductActivity.this);
            pDialog.setMessage("Creating Product..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String longitude1 = longitude.getText().toString();
            String latitude1 = latitude.getText().toString();
            String inputpest1 = inputpest.getText().toString();
            String infectedDate1 = infectedDate.getText().toString();
            String currentdate1 = currentdate.getText().toString();
            String areaaffected1 = areaaffected.getText().toString();
            String info1 = info.getText().toString();
            Log.w("longitude1",longitude1);

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("longitude", longitude1));
            params.add(new BasicNameValuePair("latitude", latitude1));
            params.add(new BasicNameValuePair("inputpest", inputpest1));
            params.add(new BasicNameValuePair("infecteddate", infectedDate1));
            params.add(new BasicNameValuePair("currentdate", currentdate1));
            params.add(new BasicNameValuePair("areaaffected", areaaffected1));
            params.add(new BasicNameValuePair("info", info1));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
                    startActivity(i);

                    // closing this screen
                    finish();
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }
}