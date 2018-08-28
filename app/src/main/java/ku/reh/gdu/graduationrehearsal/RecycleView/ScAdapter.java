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
import android.widget.TextView;

import java.util.List;

import ku.reh.gdu.graduationrehearsal.Model.NewsModel;
import ku.reh.gdu.graduationrehearsal.Model.ScheduleModel;
import ku.reh.gdu.graduationrehearsal.R;
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
        holder.tv_update_at.setText(""+val.get(position).getUpdatedAt());

        holder.setOnClickRecycleView(new RecycleViewOnClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick, MotionEvent motionEvent) {
//                Toast.makeText(context, ""+val.get(position).getDetails(), Toast.LENGTH_SHORT).show();
                showDetail(val.get(position).getPlaces(),val.get(position).getDates(),val.get(position).getUpdatedAt());
            }

            @Override
            public void onLongClick(View view, int position, boolean isLongClick, MotionEvent motionEvent) {
        }
        });

    }

    private void showDetail(String places,String dates,String update_at){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View v = inflater.inflate(R.layout.row_schedule, null);

        TextView place_tv ,dates_tv,date_tv;
        place_tv = v.findViewById(R.id.tv_place);
        dates_tv  = v.findViewById(R.id.tv_date_at);
        date_tv = v.findViewById(R.id.tv_date_at);


        place_tv.setText(places);
        dates_tv.setText(dates);
        date_tv.setText(update_at);
        AlertDialog.Builder  builder = new AlertDialog.Builder(context);
        builder.setTitle("ดูตารางซ้อม");
        builder.setView(v);

        builder.setNegativeButton("ปิด", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

    @Override
    public int getItemCount() {
        
        return val.size();

    }


    public class MyHolder extends RecyclerView.ViewHolder  implements View.OnClickListener
            , View.OnLongClickListener, View.OnTouchListener{
        Context context;
        TextView tv_place, tv_datetime,tv_update_at;
        RecycleViewOnClickListener listener;

        public MyHolder(View v,Context context) {
            super(v);

            this.context = context;
            tv_place  = itemView.findViewById(R.id.tv_place);
            tv_datetime = itemView.findViewById(R.id.tv_date_at);
            tv_update_at = itemView.findViewById(R.id.tv_date_update);

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
