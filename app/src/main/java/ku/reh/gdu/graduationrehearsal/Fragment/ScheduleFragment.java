package ku.reh.gdu.graduationrehearsal.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.List;

import ku.reh.gdu.graduationrehearsal.API.NetworkConnectionManager;
import ku.reh.gdu.graduationrehearsal.API.OncallbackScheduleListener;
import ku.reh.gdu.graduationrehearsal.Model.ScheduleModel;
import ku.reh.gdu.graduationrehearsal.R;
import ku.reh.gdu.graduationrehearsal.RecycleView.ReAdapter;
import ku.reh.gdu.graduationrehearsal.RecycleView.ScAdapter;
import okhttp3.ResponseBody;

public class ScheduleFragment extends Fragment{

    private RecyclerView recyclerView;
    private Button btn_go_login;
    private String TAG  = "NewsFragment";

    private Context context;
    private ScAdapter scAdapter;

    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule,container,false);
        init(v);
        return v;
    }

    private void init(View v) {

        context = getContext();
        scAdapter = new ScAdapter(context);

        recyclerView = v.findViewById(R.id.rv_schedule);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        showProgress();
        new NetworkConnectionManager().callSchedule(scheduleListener);



    }



    private void showProgress(){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("กำลังโหลดข้อมูลข่าวสาร");
        progressDialog.show();
    }


    OncallbackScheduleListener scheduleListener = new OncallbackScheduleListener() {
        @Override
        public void onResponse(List<ScheduleModel> schedule) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();

            scAdapter.UpdateData(schedule);
            recyclerView.setAdapter(scAdapter);
        }

        @Override
        public void onBodyError(ResponseBody responseBodyError) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            Log.e(TAG,""+responseBodyError.source());
        }

        @Override
        public void onBodyErrorIsNull() {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            Log.e(TAG,"response is null");
        }

        @Override
        public void onFailure(Throwable t) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            t.printStackTrace();
        }
    };
}
