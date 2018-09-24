package ku.reh.gdu.graduationrehearsal.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import ku.reh.gdu.graduationrehearsal.API.NetworkConnectionManager;
import ku.reh.gdu.graduationrehearsal.API.OncallbackPracticeListener;
import ku.reh.gdu.graduationrehearsal.Model.PracticeModel;
import ku.reh.gdu.graduationrehearsal.R;
import ku.reh.gdu.graduationrehearsal.RecycleView.PracticeAdp;
import okhttp3.ResponseBody;

public class PracticeFragment  extends Fragment{

    private RecyclerView recyclerView;
    private String TAG  = "PracticeFragment";

    private PracticeAdp practiceAdp;
    private Context context;

    private ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_practice,container,false);
            init(v);
        return v;
    }

    private void init(View v) {

        try{

            context = getContext();
            practiceAdp = new PracticeAdp(context);

            recyclerView = v.findViewById(R.id.recyclePractice);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            showProgress();

        new NetworkConnectionManager().callPratice(practiceListener);

        }catch (Exception e){

            e.printStackTrace();

        }

    }


    OncallbackPracticeListener practiceListener = new OncallbackPracticeListener() {
        @Override
        public void onResponse(List<PracticeModel> practice) {
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
//            Toast.makeText(context, ""+new Gson().toJson(practice), Toast.LENGTH_SHORT).show();
                practiceAdp.UpdateData(practice);
                recyclerView.setAdapter(practiceAdp);

        }

        @Override
        public void onBodyError(ResponseBody responseBodyError) {
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }

        @Override
        public void onBodyErrorIsNull() {
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }

        @Override
        public void onFailure(Throwable t) {
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
    };


    private void showProgress() {

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("กำลังโหลด");
        progressDialog.show();


    }
}
