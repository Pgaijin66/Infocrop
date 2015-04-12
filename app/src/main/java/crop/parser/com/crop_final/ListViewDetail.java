package crop.parser.com.crop_final;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;


public class ListViewDetail extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_detail);
        Intent intent = getIntent();
        String strJson = intent.getStringExtra("json");
        int index = intent.getIntExtra("index", 0);
        JSONArray obj;
        try {

            ((TextView) findViewById(R.id.textView)).setText("adasdasd1");
            obj = new JSONObject("json").getJSONArray("products");

            ((TextView) findViewById(R.id.textView)).setText("adasdasd2");

            JSONObject jsonObj = obj.getJSONObject(index);
//            String pid = jsonObj.getString("pid");

            ((TextView) findViewById(R.id.textView)).setText("adasdasd");
            String longitude = jsonObj.getString("longitude");
            String latitude = jsonObj.getString("latitude");
            String  pest =jsonObj.getString("Pest");
            String infecteddate=jsonObj.getString("Infecteddate");
            String currentdate=jsonObj.getString("Currentdate");
            String perarea= jsonObj.getString("perarea");
            String info=jsonObj.getString("info");

            ((TextView) findViewById(R.id.textView)).setText(longitude);
            ((TextView) findViewById(R.id.textView2)).setText(latitude);
            ((TextView) findViewById(R.id.textView3)).setText(pest);
            ((TextView) findViewById(R.id.textView4)).setText(infecteddate);
            ((TextView) findViewById(R.id.textView5)).setText(currentdate);
            ((TextView) findViewById(R.id.textView6)).setText(perarea);
            ((TextView) findViewById(R.id.textView7)).setText(info);





        } catch (JSONException e) {

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_view_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
