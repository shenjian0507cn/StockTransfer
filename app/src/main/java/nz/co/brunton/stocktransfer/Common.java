package nz.co.brunton.stocktransfer;

import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;

/**
 * Created by james.shen on 10/08/2017.
 */

public class Common {
    static String loginUrl = "http://192.168.69.21/OAuth2ClientTest/token";

    static String baseUrl = "http://192.168.69.21/OAuth2ClientTest/api/";
    static String ICUrl = baseUrl + "IC/";

    static String client_id = "1234";
    static String client_secret = "5678";
    static String grant_type = "client_credentials";

    final static MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    //Format like "01 August 2017"
    public static String DateConvert(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMMM yyyy");
        return simpleDateFormat.format(date);
    }
}
