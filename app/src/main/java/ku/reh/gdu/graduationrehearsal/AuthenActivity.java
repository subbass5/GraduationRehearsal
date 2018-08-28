package ku.reh.gdu.graduationrehearsal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import ku.reh.gdu.graduationrehearsal.API.NetworkConnectionManager;
import ku.reh.gdu.graduationrehearsal.API.OncallbackLoginListener;
import ku.reh.gdu.graduationrehearsal.Fragment.NewsFragment;
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

public class AuthenActivity  extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authen_activity);
        context = AuthenActivity.this;
        fragmentManager = getSupportFragmentManager();
        fragmentTran(new NewsFragment(),null);

    }

    public void fragmentTran(Fragment fragment, Bundle bundle){

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction frgTran = fragmentManager.beginTransaction();
        frgTran.replace(R.id.contentApp, fragment).addToBackStack(null).commit();

    }

    private void do_exit(){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("คำเตือน");
        builder.setMessage("คุณต้องการออกจากแอพหรือไม่");
        builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

    @Override
    public void onBackPressed() {

        do_exit();
    }
}


