package nz.co.brunton.stocktransfer;

import android.content.DialogInterface;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    String SLocation, DLocation;
    Spinner spin_sLocation, spin_dLocation;

    EditText edt_Barcode;
    TextView tv_Productcode, tv_Description, tv_Barcode;

    ListView lv_Lines;
    List<HashMap<String,Object>> transferLines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spin_sLocation = (Spinner)findViewById(R.id.spin_sLocation);
        spin_dLocation = (Spinner)findViewById(R.id.spin_dLocation);

        edt_Barcode = (EditText)findViewById(R.id.edt_Barcode);
        edt_Barcode.addTextChangedListener(barcodeWatcher);

        tv_Productcode = (TextView)findViewById(R.id.tv_ProductCode);
        tv_Description = (TextView)findViewById(R.id.tv_Description);
        tv_Barcode = (TextView)findViewById(R.id.tv_Barcode);

        lv_Lines = (ListView)findViewById(R.id.lv_Lines);

        GetData();
    }

    private final class LVItemClickListener implements AdapterView.OnItemClickListener {

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //ListView listView = (ListView) parent;
            //HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
            //String personid = data.get("id").toString();
            //Toast.makeText(getApplicationContext(), personid, 1).show();
        }
    }

    private final TextWatcher barcodeWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 0) return;

            if (((byte)s.charAt(s.length() - 1) == 13) || ((byte)s.charAt(s.length() - 1) == 10)) {
                String BarcodeStr = s.toString().substring(0,s.length()-1);
                scan(BarcodeStr);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0) return;
        }
    };

    public void scan(String Barcode) {
        tv_Barcode.setText(Barcode);
        //Toast.makeText(getApplicationContext(), String.valueOf(DataHelp.ICProductList.size()), Toast.LENGTH_LONG).show();

        ICProduct icProduct = DataHelp.GetProductByBarcode(Barcode);
        if (icProduct == null) {

        }
        else {
            tv_Productcode.setText(icProduct.getProductCode());
            tv_Description.setText(icProduct.getDescription());

            edt_Barcode.setText("");

            HashMap<String, Object> hm = new HashMap<String, Object>();
            hm.put("Productcode", icProduct.getProductCode());
            hm.put("Description", icProduct.getDescription());
            hm.put("Quantity", "1");

            transferLines.add(hm);
            SimpleAdapter simpleAdapter = (SimpleAdapter)lv_Lines.getAdapter();
            simpleAdapter.notifyDataSetChanged();
        }

    }

    private void setLocation() {
        final List<String> LocationList = DataHelp.GetLocationList();
        final String LocationArr[] = LocationList.toArray(new String[LocationList.size()]);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, LocationArr);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin_sLocation.setAdapter(arrayAdapter);
        spin_dLocation.setAdapter(arrayAdapter);

        spin_sLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SLocation = LocationList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin_dLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DLocation = LocationList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setLines() {
        transferLines = new ArrayList<HashMap<String, Object>>();
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, transferLines, R.layout.item, new String[]{"Productcode", "Quantity", "Description"},
                new int[]{R.id.item_tv_Productcode, R.id.item_tv_Quantity, R.id.item_tv_Description});
        lv_Lines.setAdapter(simpleAdapter);
        lv_Lines.setOnItemClickListener(new LVItemClickListener());
    }

    public android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    setLocation();
                    setLines();
                    break;
                case 1:
                    Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_LONG).show();
                    break;
                case 10:
                    Toast.makeText(getApplicationContext(), "10", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    public void CreateTransfer_OnClick(View view) {

        ICTransferLine icTransferLine = new ICTransferLine();
        icTransferLine.setProductCode("1.8MWARDROBE");
        icTransferLine.setQuantityDispatched(1);
        icTransferLine.setQuantityReceipted(1);

        ICTransfer icTransfer = new ICTransfer();
        icTransfer.setSourceLocationCode("AKL");
        icTransfer.setDestinationLocationCode("CHCH");
        icTransfer.setTransitLocationCode("");
        icTransfer.setDocumentDate("7 August 2017");
        icTransfer.setReceiptDate("7 August 2017");
        icTransfer.setReference("AndroidTest");
        icTransfer.setComment("AndroidTest");

        List<ICTransferLine> Line = new ArrayList<ICTransferLine>();
        Line.add(icTransferLine);
        icTransfer.setLine(Line);

        Gson gjson = new Gson();
        String json = gjson.toJson(icTransfer);

        //tv_result.setText(json);

        //CreateNewTransfer(json);
        //GetValue();
        //GetICLocations();

        //GetData();
    }

    private void CreateNewTransfer(String jsonstr) {
        final String url = "http://192.168.69.21/OAuth2ClientTest/api/IC/CreateNewICTransfer";
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        final String json = jsonstr;

        //Toast.makeText(getApplicationContext(),json, Toast.LENGTH_LONG).show();

        /*
        final String json = "{'SourceLocationCode':'AKL', 'DestinationLocationCode':'CHCH', 'TransitLocationCode':'', 'DocumentDate':'5 August 2017', 'ReceiptDate':'5 August 2017'," +
                "'Reference':'TestAPI', 'Comment': 'APITest'," +
                "'Line':[{'ProductCode':'1.8MWARDROBE','QuantityDispatched':1, 'QuantityReceipted': 1}]}";
        */

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String token = DataHelp.GetAccessToken();

                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = RequestBody.create(JSON, json);
                    Request request = new Request.Builder()
                            .url(url)
                            .addHeader("Authorization", " bearer " + token)
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute();

                    Message msg = handler.obtainMessage();
                    msg.arg1 = 0;
                    msg.what = 1;
                    msg.obj = response.body().string();
                    handler.sendMessage(msg);

                } catch (Exception e) {
                    Message msg = handler.obtainMessage();
                    msg.arg1 = 0;
                    msg.what = 10;
                    msg.obj = e.getMessage().toString();
                    handler.sendMessage(msg);
                }
            }
        });
        thread.start();

    }

    private void GetData() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    DataHelp.GetDataFromWeb();

                    Message msg = handler.obtainMessage();
                    msg.arg1 = 0;
                    msg.what = 0;
                    msg.obj = "Got All";
                    handler.sendMessage(msg);

                } catch (Exception e) {
                    Message msg = handler.obtainMessage();
                    msg.arg1 = 0;
                    msg.what = 10;
                    msg.obj = e.getMessage().toString();
                    handler.sendMessage(msg);
                }
            }
        });
        thread.start();
    }

    public void save_onClick_Event(View view) {

        List<ICTransferLine> Lines = new ArrayList<ICTransferLine>();
        for (HashMap<String, Object> hm: transferLines) {
            ICTransferLine line = new ICTransferLine();
            line.setProductCode(hm.get("Productcode").toString());
            line.setQuantityDispatched(Double.parseDouble(hm.get("Quantity").toString()));
            line.setQuantityReceipted(Double.parseDouble(hm.get("Quantity").toString()));
            Lines.add(line);
        }

        ICTransfer icTransfer = new ICTransfer();
        icTransfer.setSourceLocationCode(SLocation);
        icTransfer.setDestinationLocationCode(DLocation);
        icTransfer.setTransitLocationCode("");

        Date DocumentDate = new Date();
        icTransfer.setDocumentDate(Common.DateConvert(DocumentDate));
        Date ReceiptDate = new Date();
        icTransfer.setReceiptDate(Common.DateConvert(ReceiptDate));

        icTransfer.setReference("AndroidTest");
        icTransfer.setComment("TestAndroid");

        icTransfer.setLine(Lines);

        Gson gjson = new Gson();
        String json = gjson.toJson(icTransfer);

        CreateNewTransfer(json);
    }

    public void cancel_onClick_Event(View view) {
        Date date = new Date();
        Toast.makeText(getApplicationContext(), Common.DateConvert(date), Toast.LENGTH_LONG).show();
    }
}

