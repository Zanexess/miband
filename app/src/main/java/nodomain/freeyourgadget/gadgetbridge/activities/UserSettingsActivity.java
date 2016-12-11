package nodomain.freeyourgadget.gadgetbridge.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nodomain.freeyourgadget.gadgetbridge.GBApplication;
import nodomain.freeyourgadget.gadgetbridge.R;

public class UserSettingsActivity extends AppCompatActivity {

    @BindView(R.id.userId)
    EditText userId;

    @BindView(R.id.username)
    EditText username;

    @OnClick(R.id.ok)
    void submit() {
        GBApplication.getSharedPrefs().edit().putString("api_user_id", userId.getText().toString()).apply();
        GBApplication.getSharedPrefs().edit().putString("api_user_name", username.getText().toString()).apply();

        Toast.makeText(getApplicationContext(), "Updated user", Toast.LENGTH_SHORT).show();

        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        String id = GBApplication.getSharedPrefs().getString("api_user_id", "0");
        userId.setText(id);
        String name = GBApplication.getSharedPrefs().getString("api_user_name", "0");
        username.setText(name);
    }
}
