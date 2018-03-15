package helpers;

import com.google.gson.Gson;
import controllers.HomeController;
import helpers.interfaces.EnlightInfoFetcher;
import javafx.application.Platform;
import models.LedInfo;
import okhttp3.*;
import org.hildan.fxgson.FxGson;

import java.io.IOException;

public class EnlightLedInfoFetcher implements EnlightInfoFetcher
{
    private final OkHttpClient httpClient = new OkHttpClient();
    private LedInfo ledInfo;

    public EnlightLedInfoFetcher(LedInfo ledInfo)
    {
        this.ledInfo = ledInfo;
    }

    @Override
    public void fetchInfo(String baseUrl)
    {
        // Expose all exceptions to warn the users if occur
        Request request = new Request.Builder().url(String.format("http://%s/led_info", baseUrl)).build();

        // Start the request and
        httpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                HomeController.showError("OKHttp request failed: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                if(!response.isSuccessful())
                    throw new IOException("Failed to request System Information, code: " + response.code());

                if (response.body() == null)
                    throw new IOException("Empty body received!");

                // Deserialize the JSON to JavaFX model objects (e.g. StringProperty instead of ordinary String etc.)
                Gson gson = FxGson.create();
                LedInfo newInfo = gson.fromJson(response.body().string(), LedInfo.class);

                // Put into UI thread...
                Platform.runLater(() -> ledInfo.clone(newInfo));
            }
        });
    }
}
