package helpers;

import com.google.gson.Gson;
import models.LedInfo;
import models.SysInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.hildan.fxgson.FxGson;

import java.io.IOException;

public class EnlightInfoFetcher
{
    private final OkHttpClient httpClient = new OkHttpClient();

    public SysInfo getSystemInfo(String baseUrl) throws Exception
    {
        // Deserialize the JSON to JavaFX model objects (e.g. StringProperty instead of ordinary String etc.)
        Gson gson = FxGson.create();
        return gson.fromJson(getJsonString(baseUrl, "/sys_info"), SysInfo.class);
    }

    public LedInfo getLedInfo(String baseUrl) throws Exception
    {
        Gson gson = FxGson.create();
        return gson.fromJson(getJsonString(baseUrl, "/led_info"), LedInfo.class);
    }

    private String getJsonString(String baseUrl, String endPoint) throws Exception
    {
        // Expose all exceptions to warn the users if occur
        Request request = new Request.Builder().url(String.format("http://%s%s", baseUrl, endPoint)).build();

        // Start the request and
        Response response = httpClient.newCall(request).execute();

        if(!response.isSuccessful())
            throw new IOException("Failed to request System Information, code: " + response.code());

        if (response.body() == null)
            throw new IOException("Empty body received!");

        return response.body().string();
    }
}
