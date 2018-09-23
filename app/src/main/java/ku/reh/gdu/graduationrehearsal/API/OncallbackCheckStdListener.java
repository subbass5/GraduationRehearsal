package ku.reh.gdu.graduationrehearsal.API;

import ku.reh.gdu.graduationrehearsal.Model.CheckStdModel;
import ku.reh.gdu.graduationrehearsal.Model.LoginModel;
import okhttp3.ResponseBody;

public interface OncallbackCheckStdListener {

    public void onResponse(CheckStdModel checkStdModel);
    public void onBodyError(ResponseBody responseBodyError);
    public void onBodyErrorIsNull();
    public void onFailure(Throwable t);

}
