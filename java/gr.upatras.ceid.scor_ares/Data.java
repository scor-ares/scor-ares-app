package gr.upatras.ceid.scor_ares;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

public class Data {
    protected Activity context;
    JSONArray json;

    public Data(Activity context, String filename){
        this.context = context;
        this.json = getJson(filename);
    }

    private JSONArray getJson(String filename){
        /* Άνοιγμα του αρχείου json με το όνομα που δόθηκε μεσω της μεταβλητής filename
         * και μετατροπή του περιεχομένου του σε String και έπειτα σε JSONArray
         */
        String jsonString;
        JSONArray jsonArray = new JSONArray();

        try {
            //Άνοιγμα του αρχείου json, μετατροπή του περιεχομένου του σε input stream
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
            jsonArray = new JSONArray(jsonString);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

}
