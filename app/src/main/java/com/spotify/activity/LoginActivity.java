package com.spotify.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.PixelCopy;
import android.view.View;

import com.spotify.R;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    static final String CLIENT_ID = "9b29ba638ee846a2b3d5784dabc922f5";
    private static final String REDIRECT_URI = "http://localhost:8080";
    private static final int REQUEST_CODE = 1337;

    OkHttpClient client = new OkHttpClient();

    String mAccessToken;
    private SpotifyAppRemote appRemote;
    static ConnectionParams connectionParams = new ConnectionParams.Builder(CLIENT_ID)
            .setRedirectUri(REDIRECT_URI)
            .showAuthView(true)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    protected void onStart() {
        super.onStart();
    }

    public void start(View view) {
        AuthorizationRequest.Builder builder =
                new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"streaming"});
        AuthorizationRequest request = builder.build();

        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(appRemote);
    }

    private void connected() {
        Intent i = new Intent(this, PlayerActivity.class);
        startActivity(i);
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                case TOKEN:
                    Log.d("LoginActivity", response.getAccessToken());
                    Log.e("LoginActivity", "Namoral passei aqui");
                    mAccessToken = response.getAccessToken();
                    Log.e("LoginActivity", mAccessToken);
                    final Request request = new Request.Builder()
                            .url("https://api.spotify.com/v1/me")
                            .addHeader("Authorization", "Bearer " + mAccessToken)
                            .build();
                    Log.e("LoginActivity", String.valueOf(request));
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            // Lidar com falha na chamada
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (!response.isSuccessful()) {
                                // Lidar com a resposta não bem-sucedida
                                throw new IOException("Unexpected code " + response);
                            } else {
                                // Lidar com a resposta bem-sucedida
                                String responseData = response.body().string();
                                // Faça o que quiser com os dados da resposta
                                Log.e("LoginActivity", responseData);
                            }
                        }
                    });

                    connected();
                    break;

                case ERROR:
                    break;

                default:
            }
        }
    }

}