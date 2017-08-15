package nz.co.brunton.stocktransfer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by james.shen on 11/08/2017.
 */

public class DataHelp {
    static List<ICLocn> ICLocnList = new ArrayList<ICLocn>();
    static List<ICProduct> ICProductList = new ArrayList<ICProduct>();
    static List<ICQuantity> ICQuantityList = new ArrayList<ICQuantity>();

    public static String ConvertJsonString(String NewtonStr)
    {
        String GsonStr = NewtonStr.trim();
        if (GsonStr.substring(0,1).equals("\"")) {
            GsonStr = GsonStr.substring(1, GsonStr.length() - 1);
        }
        if (GsonStr.charAt(GsonStr.length() - 1) == '"') {
            GsonStr = NewtonStr.substring(0, GsonStr.length() - 1);
        }

        GsonStr = GsonStr.replace("\\","");
        return GsonStr;
    }

    public static void SaveICLocations(String jsonStr) {
        try {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();

            JsonElement element = parser.parse(jsonStr);
            JsonArray jsonArray = element.getAsJsonArray();

            for (JsonElement je : jsonArray) {
                JsonObject jo = je.getAsJsonObject();
                ICLocn icLocn = new ICLocn();
                icLocn = gson.fromJson(jo, icLocn.getClass());

                ICLocnList.add(icLocn);
            }
        }
        catch (Exception e) {

        }
    }

    public static void SaveICProducts(String jsonStr) {
        try {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();

            JsonElement element = parser.parse(jsonStr);
            JsonArray jsonArray = element.getAsJsonArray();

            for (JsonElement je : jsonArray) {
                JsonObject jo = je.getAsJsonObject();
                ICProduct icProduct = new ICProduct();
                icProduct = gson.fromJson(jo, icProduct.getClass());

                ICProductList.add(icProduct);
            }
        }
        catch (Exception e) {

        }
    }

    public static void SaveICQuantities(String jsonStr) {
        try {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();

            JsonElement element = parser.parse(jsonStr);
            JsonArray jsonArray = element.getAsJsonArray();

            for (JsonElement je : jsonArray) {
                JsonObject jo = je.getAsJsonObject();
                ICQuantity icQuantity = new ICQuantity();
                icQuantity = gson.fromJson(jo, icQuantity.getClass());

                ICQuantityList.add(icQuantity);
            }
        }
        catch (Exception e) {

        }
    }

    public static String GetAccessToken() {
        String token;
        try {
            OkHttpClient client = new OkHttpClient();

            RequestBody body = new FormBody.Builder()
                    .add("client_id", Common.client_id)
                    .add("client_secret", Common.client_secret)
                    .add("grant_type", Common.grant_type)
                    .build();

            Request request = new Request.Builder()
                    .url(Common.loginUrl)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            String responsestr = response.body().string();
            token = new JsonParser().parse(responsestr).getAsJsonObject().get("access_token").getAsString();
        }
        catch (Exception e) {
            token = "";
        }
        return token;
    }

    public static void GetICLocnFromWeb(String token) {
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(Common.ICUrl + "GetLocations")
                    .addHeader("Authorization", " bearer " + token)
                    .build();

            Response response = client.newCall(request).execute();

            SaveICLocations(ConvertJsonString(response.body().string()));
        }
        catch (Exception e) {

        }
    }

    public static void GetICProdFromWeb(String token) {
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(Common.ICUrl + "GetProducts")
                    .addHeader("Authorization", " bearer " + token)
                    .build();

            Response response = client.newCall(request).execute();

            SaveICProducts(ConvertJsonString(response.body().string()));
        }
        catch (Exception e) {

        }
    }

    public static void GetICQtyFromWeb(String token) {
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(Common.ICUrl + "GetQuantity")
                    .addHeader("Authorization", " bearer " + token)
                    .build();

            Response response = client.newCall(request).execute();

            SaveICQuantities(ConvertJsonString(response.body().string()));
        }
        catch (Exception e) {

        }
    }

    public static void CreateNewTransfer(String json) {
        String token = GetAccessToken();

        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(Common.JSON, json);

            Request request = new Request.Builder()
                    .url(Common.ICUrl + "CreateNewICTransfer")
                    .addHeader("Authorization", " bearer " + token)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
        }
        catch (Exception e) {

        }
    }

    public static void GetDataFromWeb() {
        String token = GetAccessToken();

        GetICLocnFromWeb(token);
        GetICProdFromWeb(token);
        GetICQtyFromWeb(token);
    }

    public static List<String> GetLocationList() {
        List<String> LocationList = new ArrayList<>();

        for (int i=0; i<ICLocnList.size(); i++) {
            String LocationCode = ICLocnList.get(i).getLocationCode();
            LocationList.add(LocationCode);
        }

        return LocationList;
    }

    public static ICProduct GetProductByBarcode(String Barcode) {
        for (ICProduct icProduct: ICProductList) {
            if (icProduct.getBarCode().equals(Barcode)) {
                return icProduct;
            }
        }

        return  null;
    }

}
