package test.com.test;
import org.json.JSONException;

import java.io.IOException;
import okhttp3.Response;

//used This InterFace For Accessing Response Data From Host
interface AsyncResponse {
    //Return Data
    void ReturnResponse(Response response) throws IOException, JSONException;
}
