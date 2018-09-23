package ku.reh.gdu.graduationrehearsal.API;

import java.util.List;

import ku.reh.gdu.graduationrehearsal.Model.CheckStdModel;
import ku.reh.gdu.graduationrehearsal.Model.LoginModel;
import ku.reh.gdu.graduationrehearsal.Model.NewsModel;
import ku.reh.gdu.graduationrehearsal.Model.ScheduleModel;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("app/login")
    Call<LoginModel> login(@Field("username") String usr,@Field("password") String pwd);


    @GET("app/news")
    Call<List<NewsModel>> news();


    @GET("app/schedule")
    Call<List<ScheduleModel>> schedule();

//    @FormUrlEncoded
    @POST("app/student/find/{id}")
    Call<CheckStdModel> checkSTD(@Path("id") String id, @Body RequestBody body);

//    Call<CheckStdModel> checkSTD(@Path("id") String id, @Field("permission") String permission, @Field("schedules_id") String schedules_id);

}
