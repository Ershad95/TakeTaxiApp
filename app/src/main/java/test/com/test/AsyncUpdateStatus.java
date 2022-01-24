package test.com.test;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Ershad on 02/02/2017.
 */

public interface AsyncUpdateStatus {
    void UpdateStatus_Response(Response response) throws IOException, JSONException;
}
