package ku.reh.gdu.graduationrehearsal.API;

import ku.reh.gdu.graduationrehearsal.Model.LoginModel;
import okhttp3.ResponseBody;

public interface OncallbackLoginListener {

    public void onResponse(LoginModel loginModel);
    public void onBodyError(ResponseBody responseBodyError);
    public void onBodyErrorIsNull();
    public void onFailure(Throwable t);

}

