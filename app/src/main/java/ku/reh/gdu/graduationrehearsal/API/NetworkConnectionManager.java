package ku.reh.gdu.graduationrehearsal.API;

import ku.reh.gdu.graduationrehearsal.Model.LoginModel;
import ku.reh.gdu.graduationrehearsal.Util.ApiUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ku.reh.gdu.graduationrehearsal.Util.ApiUtil.BASE_URL;

public class NetworkConnectionManager {

    public NetworkConnectionManager() {

    }

    public void callLogin(final OncallbackLoginListener listener,String usr,String pwd){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService serv = retrofit.create(ApiService.class);
        Call call = serv.login(usr,pwd);
        call.enqueue(new Callback<LoginModel>() {

            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                LoginModel loginModel = response.body();

                if (loginModel == null) {
                    //404 or the response cannot be converted to User.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(loginModel);
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                listener.onFailure(t);
            }


        });

    }

}
