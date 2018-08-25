package ku.reh.gdu.graduationrehearsal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import ku.reh.gdu.graduationrehearsal.API.NetworkConnectionManager;
import ku.reh.gdu.graduationrehearsal.API.OncallbackLoginListener;
import ku.reh.gdu.graduationrehearsal.Model.LoginModel;
import okhttp3.ResponseBody;

public class AuthenActivity  extends AppCompatActivity implements View.OnClickListener{

    private EditText et_usr,et_pwd;
    private Context context;
    private String usr,pwd;
    private String TAG = "AuthenActivity";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authen_activity);
        getSupportActionBar().hide();

        context = getBaseContext();
        et_usr = findViewById(R.id.et_usr);
        et_pwd = findViewById(R.id.et_pwd);

        findViewById(R.id.btn_login).setOnClickListener(this);

    }

    private void login(){
        try {
            usr = et_usr.getText().toString().trim();
            pwd = et_pwd.getText().toString().trim();

            if(usr.isEmpty()){
                Toast.makeText(context, "กรุณาป้อน Username", Toast.LENGTH_SHORT).show();
            }
            else if(pwd.isEmpty()){
                Toast.makeText(context, "กรุณาป้อน Password", Toast.LENGTH_SHORT).show();
            }else{
                new NetworkConnectionManager().callLogin(listener,usr,pwd);

            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                login();
//                Toast.makeText(context, "do", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    OncallbackLoginListener listener = new OncallbackLoginListener() {
        @Override
        public void onResponse(LoginModel loginModel) {
            String result = new Gson().toJson(loginModel);
            Log.d(TAG,result);
        }

        @Override
        public void onBodyError(ResponseBody responseBodyError) {
            Log.d(TAG,responseBodyError.source().toString());
        }

        @Override
        public void onBodyErrorIsNull() {
            Log.d(TAG,"null");
        }

        @Override
        public void onFailure(Throwable t) {
            t.printStackTrace();
        }
    };
}


