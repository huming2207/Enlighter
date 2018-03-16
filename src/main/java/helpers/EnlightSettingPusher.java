package helpers;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import okhttp3.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class EnlightSettingPusher
{
    private String deviceIpAddr;

    public EnlightSettingPusher(String deviceIpAddr)
    {
        this.deviceIpAddr = deviceIpAddr;
    }

    public void commitSetting(Map<String, String> settingPairs, String endPoint)
    {
        // Sometimes ESP32 devices may need more time to process the request, so here are some stupid tweaks...
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        // Manipulate URL
        HttpUrl.Builder urlBuilder = new HttpUrl.Builder()
                .scheme("http")
                .host(deviceIpAddr)
                .addPathSegment(endPoint);

        for(Map.Entry<String, String> entry : settingPairs.entrySet()) {
            urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }

        Request request = new Request.Builder().url(urlBuilder.build()).build();
        httpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                // Create an Alert GUI dialog to show the error to user
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Settings failed to push");
                    alert.setContentText(String.format("Request failed, reason: %s", e.getMessage()));
                    alert.show();
                });

                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                if(!response.isSuccessful())
                    throw new IOException("Unsuccessful response received, code: " + response.code());

                if(response.body() == null)
                    throw new IOException("Response body is empty.");

                // This is necessary as ESP32 cannot hold more than 4 connections, as a result, when request
                // successfully performed, the client must close the connection immediately.
                // Otherwise the upcoming requests will fail...
                response.close();
            }
        });

    }

    public void commitSetting(String param, String value, String endPoint)
    {
        HashMap<String, String> map = new HashMap<>();
        map.put(param, value);

        commitSetting(map, endPoint);
    }
}
