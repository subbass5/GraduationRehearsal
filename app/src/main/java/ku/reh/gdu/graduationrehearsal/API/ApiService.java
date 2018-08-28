package ku.reh.gdu.graduationrehearsal.API;

import java.util.List;

import ku.reh.gdu.graduationrehearsal.Model.LoginModel;
import ku.reh.gdu.graduationrehearsal.Model.NewsModel;
import ku.reh.gdu.graduationrehearsal.Model.ScheduleModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("app/login")
    Call<LoginModel> login(@Field("username") String usr,@Field("password") String pwd);


    @GET("app/news")
    Call<List<NewsModel>> news();


    @GET("app/schedule")
    Call<List<ScheduleModel>> schedule();

}
