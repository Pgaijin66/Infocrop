package crop.parser.com.crop_final;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.net.URL;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.util.ArrayList;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;



public class NewsActivity extends ActionBarActivity {

    String rssResult = "";
    boolean item = false;
    TextView rss;
    ListView rssList;
    ArrayAdapter<RSSNewsItem> listAdapter;
    RSSHandler rssHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        rss = (TextView) findViewById(R.id.rss);
        rssList = (ListView) findViewById(R.id.rssList);
        listAdapter = new ArrayAdapter<RSSNewsItem>(this, android.R.layout.simple_list_item_1);
        rssList.setAdapter(listAdapter);
        new RssTask().execute("http://www.farms.com/Portals/_default/RSS_Portal/News_Crop.xml");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news, menu);
        return true;
    }

    class RssTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = null;

            try {
                URL rssUrl = new URL(urls[0]);
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();
                XMLReader xmlReader = saxParser.getXMLReader();
                rssHandler = new RSSHandler();
                xmlReader.setContentHandler(rssHandler);
                InputSource inputSource = new InputSource(rssUrl.openStream());
                xmlReader.parse(inputSource);
                //result = rssHandler.getResult();

            } catch (IOException e) {
                rss.setText(e.getMessage());
            } catch (SAXException e) {
                rss.setText(e.getMessage());
            } catch (ParserConfigurationException e) {
                rss.setText(e.getMessage());
            }

            //Log.e("rss-string", result);

            return result;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            listAdapter.addAll(rssHandler.newsItemArrayList);
        }
    }

    private class RSSNewsItem {
        public String title;
        public String description;
        public String link;
        public String guid;
        public String pubDate;

        @Override
        public String toString() {
            return title;
        }
    }

    private class RSSHandler extends DefaultHandler {

        String rssResult;
        String tempVal;
        RSSNewsItem tempItem;
        Boolean item = false;
        ArrayList<RSSNewsItem> newsItemArrayList;

        public RSSHandler() {
            newsItemArrayList = new ArrayList<>();
        }

        public void startElement(String uri, String localName, String qName,
                                 Attributes attrs) throws SAXException {
            // reset
            tempVal = "";
            if (qName.equalsIgnoreCase("item")) {
                // create a new instance of employee
                tempItem = new RSSNewsItem();
                item = true;
            }

        }

        public void endElement(String namespaceURI, String localName,
                               String qName) throws SAXException {
            if(!item) {
                return;
            }
            if (qName.equalsIgnoreCase("item")) {
                // add  it to the list
                newsItemArrayList.add(tempItem);
            } else if (qName.equalsIgnoreCase("title")) {
                tempItem.title = tempVal;
            } else if (qName.equalsIgnoreCase("description")) {
                tempItem.description = tempVal;
            } else if (qName.equalsIgnoreCase("link")) {
                tempItem.link = tempVal;
            } else if (qName.equalsIgnoreCase("guid")) {
                tempItem.guid = tempVal;
            } else if (qName.equalsIgnoreCase("pubDate")) {
                tempItem.pubDate = tempVal;
            }

        }

        public void characters(char[] ch, int start, int length)
                throws SAXException {
            tempVal = new String(ch, start, length);
        }

        public String getResult() {
            return rssResult;
        }
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
