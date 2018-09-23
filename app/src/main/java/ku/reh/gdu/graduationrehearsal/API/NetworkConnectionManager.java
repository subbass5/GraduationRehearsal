package ku.reh.gdu.graduationrehearsal.API;

import java.util.List;

import ku.reh.gdu.graduationrehearsal.Model.CheckStdModel;
import ku.reh.gdu.graduationrehearsal.Model.LoginModel;
import ku.reh.gdu.graduationrehearsal.Model.NewsModel;
import ku.reh.gdu.graduationrehearsal.Model.ScheduleModel;
import ku.reh.gdu.graduationrehearsal.Util.ApiUtil;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkConnectionManager {

    public NetworkConnectionManager() {

    }

    public void callNews(final OncallbackNewsListener listener){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService serv = retrofit.create(ApiService.class);
        Call call = serv.news();
        call.enqueue(new Callback<List<NewsModel>>() {

            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                List<NewsModel> loginModel = response.body();

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
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {
                listener.onFailure(t);
            }


        });

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

    public void callSchedule(final OncallbackScheduleListener listener){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService serv = retrofit.create(ApiService.class);
        Call call = serv.schedule();
        call.enqueue(new Callback<List<ScheduleModel>>() {

            @Override
            public void onResponse(Call<List<ScheduleModel>> call, Response<List<ScheduleModel>> response) {
                List<ScheduleModel> schedule = response.body();

                if (schedule == null) {
                    //404 or the response cannot be converted to User.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(schedule);
                }
            }

            @Override
            public void onFailure(Call<List<ScheduleModel>> call, Throwable t) {
                listener.onFailure(t);
            }


        });

    }


    public void callCheckSTD(final OncallbackCheckStdListener listener,String path,String permission,String schedule_id,String checker){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("permission", permission)
                .addFormDataPart("schedule_id", schedule_id)
                .addFormDataPart("checker", checker)
                .build();

        ApiService serv = retrofit.create(ApiService.class);
        Call call = serv.checkSTD(path,requestBody);
        call.enqueue(new Callback<CheckStdModel>() {

            @Override
            public void onResponse(Call<CheckStdModel> call, Response<CheckStdModel> response) {
                CheckStdModel loginModel = response.body();

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
            public void onFailure(Call<CheckStdModel> call, Throwable t) {
                listener.onFailure(t);
            }


        });

    }

}
