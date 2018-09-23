package ku.reh.gdu.graduationrehearsal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import ku.reh.gdu.graduationrehearsal.API.NetworkConnectionManager;
import ku.reh.gdu.graduationrehearsal.API.OncallbackCheckStdListener;
import ku.reh.gdu.graduationrehearsal.Fragment.ScheduleFragment;
import ku.reh.gdu.graduationrehearsal.Fragment.ShowQRFragment;
import ku.reh.gdu.graduationrehearsal.Model.CheckStdModel;
import ku.reh.gdu.graduationrehearsal.Util.ApiUtil;
import ku.reh.gdu.graduationrehearsal.Util.Myfer;
import okhttp3.ResponseBody;

import static ku.reh.gdu.graduationrehearsal.Util.ApiUtil.BASE_URL;
import static ku.reh.gdu.graduationrehearsal.Util.Myfer.IMAGE;
import static ku.reh.gdu.graduationrehearsal.Util.Myfer.MY_FER;
import static ku.reh.gdu.graduationrehearsal.Util.Myfer.NAME;
import static ku.reh.gdu.graduationrehearsal.Util.Myfer.PERMISSION;

public class StudentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Context context;
    private  Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private AlertDialog alertDialog;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String TAG = "StudentActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        toolbar =  findViewById(R.id.toolbar);
        toolbar.setTitle("นักศึกษา");
        setSupportActionBar(toolbar);

        context = StudentActivity.this;
        drawer = findViewById(R.id.drawer_layout_std);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        sharedPreferences = getSharedPreferences(MY_FER,MODE_PRIVATE);
        editor = sharedPreferences.edit();

        navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView tv_name = headerView.findViewById(R.id.tv_name_user);
        TextView tv_permission = headerView.findViewById(R.id.tv_permission);
        ImageView img_user = headerView.findViewById(R.id.img_user);


        tv_name.setText(sharedPreferences.getString(NAME,""));
        tv_permission.setText(sharedPreferences.getString(PERMISSION,""));

        try {
            String urlImage = sharedPreferences.getString(IMAGE,"");

            if(!urlImage.isEmpty()){
                Picasso.get().load(BASE_URL+urlImage).into(img_user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        fragmentTran(new ScheduleFragment(),null);
    }

    public void fragmentTran(Fragment fragment, Bundle bundle){

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction frgTran = fragmentManager.beginTransaction();
        frgTran.replace(R.id.contentStudent, fragment).addToBackStack(null).commit();   //content teacher

    }

    private void do_logout(){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("คำเตือน");
        builder.setMessage("คุณต้องการที่จะออกจากระบบ ?");

        builder.setNegativeButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent =new Intent(getBaseContext(),AuthenActivity.class);
                startActivity(intent);
                finish();

            }
        });
        builder.setPositiveButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

               dialog.dismiss();

            }
        });
        alertDialog = builder.show();
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try{
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            String getID = result.getContents();
            String[] res = getID.split("/");
            String permission = sharedPreferences.getString(Myfer.PERMISSION,"");
            String schedule_id = sharedPreferences.getString(Myfer.SCHEDULES_ID,"");
            String checker = sharedPreferences.getString(Myfer.UID,"");


//            Toast.makeText(context, "Result = "+sharedPreferences.getString(Myfer.PERMISSION,""), Toast.LENGTH_SHORT).show();
            new NetworkConnectionManager().callCheckSTD(checkStdListener,res[6],permission,schedule_id,checker);
            Log.e(TAG,""+res[6]);


        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "QR Code ไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
        }

    }

    OncallbackCheckStdListener checkStdListener = new OncallbackCheckStdListener() {
        @Override
        public void onResponse(CheckStdModel checkStdModel) {
//            String result = new Gson().toJson(checkStdModel);
//            Log.e("Response",result);
            showUser(ApiUtil.BASE_URL+checkStdModel.getImage(),
                    checkStdModel.getName(),
                    checkStdModel.getIdentifyId(),
                    checkStdModel.getIdcard(),
                    checkStdModel.getClassRoom(),
                    checkStdModel.getTel(),
                    checkStdModel.getEmail());
        }

        @Override
        public void onBodyError(ResponseBody responseBodyError) {
            Log.e(TAG,""+responseBodyError.source());
        }

        @Override
        public void onBodyErrorIsNull() {
            Log.e(TAG,"null");
        }

        @Override
        public void onFailure(Throwable t) {
            t.printStackTrace();
        }
    };

    private void showUser( String pathUser,String fullname,String stdId,String cardId,String classroom,String tel,String email) {


        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.layout_show_std, null);
            ImageView img = v.findViewById(R.id.img_profile);
            TextView tv_fullname,tv_stdid,tv_cardid,tv_classroom,tv_tel,tv_email;
            tv_fullname = v.findViewById(R.id.tv_name);
            tv_stdid = v.findViewById(R.id.tv_std_id);
            tv_cardid = v.findViewById(R.id.tv_cardid);
            tv_classroom = v.findViewById(R.id.tv_classroom);
            tv_email = v.findViewById(R.id.tv_email);
            tv_tel = v.findViewById(R.id.tv_tel);

            tv_fullname.setText(fullname);
            tv_stdid.setText(stdId);
            tv_cardid.setText(cardId);
            tv_classroom.setText(classroom);
            tv_email.setText(email);
            tv_tel.setText(tel);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(v);
            Picasso.get().load(pathUser).into(img);

            final AlertDialog dialog   = builder.show();
            Button btnCLose = v.findViewById(R.id.btn_close);
            btnCLose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

        }catch (Exception e) {

        }

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_place) {
            fragmentTran(new ScheduleFragment(),null);
        } else if (id == R.id.nav_qr) {
            fragmentTran(new ShowQRFragment(),null);
        } else if (id == R.id.nav_logout) {
            do_logout();
        }


        drawer =  findViewById(R.id.drawer_layout_std);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
