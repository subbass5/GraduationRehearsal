package ku.reh.gdu.graduationrehearsal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import ku.reh.gdu.graduationrehearsal.Util.Myfer;
import okhttp3.ResponseBody;

import static ku.reh.gdu.graduationrehearsal.Util.Myfer.CLASS_ROOM;
import static ku.reh.gdu.graduationrehearsal.Util.Myfer.CREATED_AT;
import static ku.reh.gdu.graduationrehearsal.Util.Myfer.GENDER;
import static ku.reh.gdu.graduationrehearsal.Util.Myfer.IDENTIFY_ID;
import static ku.reh.gdu.graduationrehearsal.Util.Myfer.ID_CARD;
import static ku.reh.gdu.graduationrehearsal.Util.Myfer.IMAGE;
import static ku.reh.gdu.graduationrehearsal.Util.Myfer.MY_FER;
import static ku.reh.gdu.graduationrehearsal.Util.Myfer.NAME;
import static ku.reh.gdu.graduationrehearsal.Util.Myfer.NUMBER;
import static ku.reh.gdu.graduationrehearsal.Util.Myfer.PERFIX;
import static ku.reh.gdu.graduationrehearsal.Util.Myfer.PERMISSION;
import static ku.reh.gdu.graduationrehearsal.Util.Myfer.QR_CODE;
import static ku.reh.gdu.graduationrehearsal.Util.Myfer.QUALIFICATION;
import static ku.reh.gdu.graduationrehearsal.Util.Myfer.STUDENT;
import static ku.reh.gdu.graduationrehearsal.Util.Myfer.TEACHER;
import static ku.reh.gdu.graduationrehearsal.Util.Myfer.TEL;
import static ku.reh.gdu.graduationrehearsal.Util.Myfer.UID;
import static ku.reh.gdu.graduationrehearsal.Util.Myfer.UPDATED_AT;

public class AuthenActivity  extends AppCompatActivity implements View.OnClickListener{

    private EditText et_usr,et_pwd;
    private Context context;
    private String usr,pwd;
    private String TAG = "AuthenActivity";
    private SharedPreferences sh;
    private SharedPreferences.Editor editor;
    private ProgressDialog progressDialog;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authen_activity);
        getSupportActionBar().hide();

        context = AuthenActivity.this;

        et_usr = findViewById(R.id.et_usr);
        et_pwd = findViewById(R.id.et_pwd);

        findViewById(R.id.btn_login).setOnClickListener(this);

        sh = getSharedPreferences(MY_FER,MODE_PRIVATE);
        editor = sh.edit();


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
                showProgress();
                new NetworkConnectionManager().callLogin(listener,usr,pwd);

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void showProgress(){

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("กำลังโหลดข้อมูล");
        progressDialog.show();
    }

    private void saveUserData(LoginModel loginModel){

        try {

            editor.putString(UID,""+loginModel.getId());
            editor.putString(PERFIX,loginModel.getPrefix());
            editor.putString(NAME,loginModel.getName());
            editor.putString(IDENTIFY_ID,""+loginModel.getIdentifyId());
            editor.putString(ID_CARD,""+loginModel.getIdcard());
            editor.putString(TEL,""+loginModel.getTel());
            editor.putString(GENDER,loginModel.getGender());
            editor.putString(NUMBER,""+loginModel.getNumber());
            editor.putString(CLASS_ROOM,""+loginModel.getClassRoom());
            editor.putString(QUALIFICATION,""+loginModel.getQualification());
            editor.putString(PERMISSION,loginModel.getPermission());
            editor.putString(QR_CODE,""+loginModel.getQrCode());
            editor.putString(CREATED_AT,loginModel.getCreatedAt());
            editor.putString(UPDATED_AT,loginModel.getUpdatedAt());
            editor.putString(IMAGE,loginModel.getImage());
            editor.commit();

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
            if (progressDialog.isShowing())
                progressDialog.dismiss();

//            String result = new Gson().toJson(loginModel);

            saveUserData(loginModel);  //save im session

            if(loginModel.getPermission().equals(TEACHER)){

                Intent intent = new Intent(AuthenActivity.this,TeacherActivity.class);
                startActivity(intent);
                finish();

            }else if(loginModel.getPermission().equals(STUDENT)){
                Intent intent = new Intent(AuthenActivity.this,StudentActivity.class);
                startActivity(intent);
                finish();
            }else{
                et_usr.setText("");
                et_pwd.setText("");
            }

        }

        @Override
        public void onBodyError(ResponseBody responseBodyError) {
            Log.d(TAG,responseBodyError.source().toString());
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }

        @Override
        public void onBodyErrorIsNull() {
            Log.d(TAG,"null");
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }

        @Override
        public void onFailure(Throwable t) {

            if (progressDialog.isShowing())
                progressDialog.dismiss();
            t.printStackTrace();
        }
    };
}


