package helpers;

import controllers.AdvancedPageController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import models.SysInfo;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class EnlightFirmwareUpdater
{
    public static void updateFirmware(String deviceIp, String firmwareImage, AdvancedPageController controller)
    {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        // Manipulate URL
        HttpUrl.Builder urlBuilder = new HttpUrl.Builder()
                .scheme("http")
                .host(deviceIp)
                .addPathSegment("ota");



        // Manipulate a multipart POST request
        // Simulate the behaviour made by Chrome v65 on macOS 10.13 (at least it works...)
        File file = new File(firmwareImage);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("update", file.getName(),
                        RequestBody.create(MediaType.parse("application/macbinary"), file))
                .build();

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .post(requestBody)
                .build();

        // Now fire the request...
        httpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Failed to upgrade firmware, reason: " + e.getMessage());
                    alert.setTitle("Failed to upgrade");
                    alert.show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                if(!response.isSuccessful())
                    throw new IOException("Unsuccessful response received, code: " + response.code());

                if(response.body() == null)
                    throw new IOException("Response body is empty.");

                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Upgrade successful!");
                    alert.setTitle("Congrats!");
                });

                response.close();
                System.exit(0);
            }
        });
    }
}
