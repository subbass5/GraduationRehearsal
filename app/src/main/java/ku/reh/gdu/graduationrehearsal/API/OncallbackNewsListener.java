package ku.reh.gdu.graduationrehearsal.API;

import java.util.List;

import ku.reh.gdu.graduationrehearsal.Model.LoginModel;
import ku.reh.gdu.graduationrehearsal.Model.NewsModel;
import okhttp3.ResponseBody;

public interface OncallbackNewsListener {
    public void onResponse(List<NewsModel> news);
    public void onBodyError(ResponseBody responseBodyError);
    public void onBodyErrorIsNull();
    public void onFailure(Throwable t);
}
