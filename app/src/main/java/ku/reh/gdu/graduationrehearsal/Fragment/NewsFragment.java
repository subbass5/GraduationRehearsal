package ku.reh.gdu.graduationrehearsal.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import ku.reh.gdu.graduationrehearsal.API.NetworkConnectionManager;
import ku.reh.gdu.graduationrehearsal.API.OncallbackNewsListener;
import ku.reh.gdu.graduationrehearsal.Model.NewsModel;
import ku.reh.gdu.graduationrehearsal.R;
import ku.reh.gdu.graduationrehearsal.RecycleView.ReAdapter;
import okhttp3.ResponseBody;

public class NewsFragment extends Fragment{

    private RecyclerView recyclerView;
    private Button btn_go_login;
    private String TAG  = "NewsFragment";

    private Context context;
    private ReAdapter newsAdaper;

    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_news,container,false);
            init(v);
        return v;
    }

    private void init(View v) {

        context = getContext();
        ((AppCompatActivity) context).getSupportActionBar().show();
        ((AppCompatActivity) context).getSupportActionBar().setTitle("ข่าวสาร");


        btn_go_login = v.findViewById(R.id.btn_go_login);

        newsAdaper = new ReAdapter(context);

        recyclerView = v.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));


        try {

            showProgress();
            new NetworkConnectionManager().callNews(newsListener);

            btn_go_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    fragmentTran(new LoginFragment(),null);

                }
            });
        }catch (Exception err){
            err.printStackTrace();
        }

    }

    public void fragmentTran(Fragment fragment, Bundle bundle){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction frgTran = fragmentManager.beginTransaction();
        frgTran.replace(R.id.contentApp, fragment).addToBackStack(null).commit();

    }

    private void showProgress(){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("กำลังโหลดข้อมูลข่าวสาร");
        progressDialog.show();
    }


    OncallbackNewsListener newsListener = new OncallbackNewsListener() {
        @Override
        public void onResponse(List<NewsModel> news) {
            if(progressDialog.isShowing())
                progressDialog.dismiss();

            String result = new Gson().toJson(news);
            Log.e(TAG,result);

            newsAdaper.UpdateData(news);
            recyclerView.setAdapter(newsAdaper);


        }

        @Override
        public void onBodyError(ResponseBody responseBodyError) {
            Log.e(TAG,""+responseBodyError.source());
            if(progressDialog.isShowing())
                progressDialog.dismiss();
        }

        @Override
        public void onBodyErrorIsNull() {
            Log.e(TAG,"news is null");
            if(progressDialog.isShowing())
                progressDialog.dismiss();
        }

        @Override
        public void onFailure(Throwable t) {
            if(progressDialog.isShowing())
                progressDialog.dismiss();
            Toast.makeText(context, "เกิดปัญหาบางอย่าง กรุณาติดต่อผู้พัฒนา", Toast.LENGTH_SHORT).show();
            recyclerView.setBackgroundColor(getResources().getColor(R.color.cardview_light_background));
                t.printStackTrace();
        }
    };


}
