package test.com.test;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Ershad on 25/01/2017.
 */

public interface AsyncSendToServer {
    void Response(Response response) throws IOException, JSONException;
}
