package ku.reh.gdu.graduationrehearsal.API;

import java.util.List;

import ku.reh.gdu.graduationrehearsal.Model.ScheduleModel;
import okhttp3.ResponseBody;

public interface OncallbackScheduleListener {
    public void onResponse(List<ScheduleModel> schedule);
    public void onBodyError(ResponseBody responseBodyError);
    public void onBodyErrorIsNull();
    public void onFailure(Throwable t);
}
