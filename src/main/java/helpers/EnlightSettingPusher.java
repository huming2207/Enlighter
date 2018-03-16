package helpers;

import okhttp3.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class EnlightSettingPusher
{
    private final OkHttpClient httpClient = new OkHttpClient();
    private EnlightPushResult result = new EnlightPushResult();
    private String deviceIpAddr;

    public EnlightSettingPusher(String deviceIpAddr)
    {
        this.deviceIpAddr = deviceIpAddr;
    }

    public void pushSettingsAsync(Map<String, String> settingPairs, String endPoint)
    {
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
                result.setCode(-1);
                result.setMessage("Request failed: " + e.getMessage());
                result.setSuccessful(false);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                if(!response.isSuccessful())
                    throw new IOException("Unsuccessful response received.");

                if(response.body() == null)
                    throw new IOException("Response body is empty.");

                result.setCode(response.code());
                result.setSuccessful(response.code() == 200);
                result.setMessage(response.body().string());
            }
        });

    }

    public EnlightPushResult getResult()
    {
        return this.result;
    }
}
