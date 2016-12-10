package nodomain.freeyourgadget.gadgetbridge;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by 4xes on 11/12/2016.
 */

public interface RateService {

    @POST("/pulses/")
    Call<ResponseBody> rate(@Body JSONObject json);
}
