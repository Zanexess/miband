package nodomain.freeyourgadget.gadgetbridge.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nodomain.freeyourgadget.gadgetbridge.GBApplication;
import nodomain.freeyourgadget.gadgetbridge.R;

public class UserSettingsActivity extends AppCompatActivity {

    @BindView(R.id.userId)
    EditText edUserId;

    @OnClick(R.id.ok)
    void submit() {
        GBApplication.getSharedPrefs().edit().putString("api_user_id", edUserId.getText().toString()).apply();

        String id = GBApplication.getSharedPrefs().getString("api_user_id", "0");

        Log.d("TAG", id);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
