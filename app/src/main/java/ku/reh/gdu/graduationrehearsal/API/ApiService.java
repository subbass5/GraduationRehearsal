package ku.reh.gdu.graduationrehearsal.API;

import ku.reh.gdu.graduationrehearsal.Model.LoginModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("app/login")
    Call<LoginModel> login(@Field("username") String usr,@Field("password") String pwd);

}
