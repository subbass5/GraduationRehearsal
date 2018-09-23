package ku.reh.gdu.graduationrehearsal.RecycleView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;

import java.util.List;

import ku.reh.gdu.graduationrehearsal.Model.NewsModel;
import ku.reh.gdu.graduationrehearsal.Model.ScheduleModel;
import ku.reh.gdu.graduationrehearsal.R;
import ku.reh.gdu.graduationrehearsal.TeacherActivity;
import ku.reh.gdu.graduationrehearsal.Util.Myfer;

public class ScAdapter extends RecyclerView.Adapter<ScAdapter.MyHolder> {

    Context context;
    List<ScheduleModel> val;

    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;

    public ScAdapter(Context context){

        this.context = context;
        sharedPreferences = ((AppCompatActivity) context).getSharedPreferences(Myfer.MY_FER,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }


    public void UpdateData(List<ScheduleModel> val) {

        this.val = val;

    }

    @NonNull
    @Override
    public ScAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_schedule,parent,false);
        return new MyHolder(v,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ScAdapter.MyHolder holder, final int position) {

        holder.tv_place.setText(""+val.get(position).getPlaces());
        holder.tv_datetime.setText(""+val.get(position).getDates());
        holder.tv_update_at.setText("ปรับปรุงเมื่อ : "+val.get(position).getUpdatedAt());

        holder.setOnClickRecycleView(new RecycleViewOnClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick, MotionEvent motionEvent) {
//                Toast.makeText(context, ""+val.get(position).getDetails(), Toast.LENGTH_SHORT).show();
                showDetail(val.get(position).getPlaces(),
                        val.get(position).getDates(),
                        val.get(position).getUpdatedAt(),
                        ""+val.get(position).getId());
            }

            @Override
            public void onLongClick(View view, int position, boolean isLongClick, MotionEvent motionEvent) {
        }
        });

    }

    private void showDetail(String places, String dates, String update_at , final String schedules_id){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View v = inflater.inflate(R.layout.layout_show_schedule, null);



        TextView place_tv ,dates_tv,date_tv;
        place_tv = v.findViewById(R.id.tv_place);
        dates_tv  = v.findViewById(R.id.tv_date_at);
        date_tv = v.findViewById(R.id.tv_date_at);


        place_tv.setText(places);
        dates_tv.setText("ปรับปรุงเมื่อ : "+dates);
        date_tv.setText(update_at);
        AlertDialog.Builder  builder = new AlertDialog.Builder(context);
        builder.setTitle("ดูตารางซ้อม");
        builder.setView(v);

        final AlertDialog dialog =  builder.show();

        String Per = sharedPreferences.getString(Myfer.PERMISSION,"");


        Button btnClose = v.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button btnScan = v.findViewById(R.id.btn_scan);

        if(Per.equals("STUDENT")){
            btnScan.setText("แสกน QR Code");
        }

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString(Myfer.SCHEDULES_ID,schedules_id);
                editor.commit();

                IntentIntegrator integrator = new IntentIntegrator((AppCompatActivity) context);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("กรุณานำกล้องแสกน");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
                dialog.dismiss();
            }
        });





    }

    @Override
    public int getItemCount() {
        
        return val.size();

    }


    public class MyHolder extends RecyclerView.ViewHolder  implements View.OnClickListener
            , View.OnLongClickListener, View.OnTouchListener{
        Context context;
        TextView tv_place, tv_datetime,tv_update_at;
//        Button btn_close ,btn_scan;
        RecycleViewOnClickListener listener;

        public MyHolder(View v,Context context) {
            super(v);

            this.context = context;
            tv_place  = itemView.findViewById(R.id.tv_place);
            tv_datetime = itemView.findViewById(R.id.tv_date_at);
            tv_update_at = itemView.findViewById(R.id.tv_date_update);
//            btn_scan = itemView.findViewById(R.id.btn_scan);
//            btn_close = itemView.findViewById(R.id.btn_close);

//            btn_scan.setVisibility(View.GONE);
//            btn_close.setVisibility(View.GONE);
//
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }
        public void setOnClickRecycleView(RecycleViewOnClickListener listener){
            this.listener =  listener;

        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition(), false, null);

        }

        @Override
        public boolean onLongClick(View v) {
            listener.onLongClick(v, getAdapterPosition(), false, null);

            return false;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            listener.onClick(v, getAdapterPosition(), false, event);

            return false;
        }
    }


}
