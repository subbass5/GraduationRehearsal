package ku.reh.gdu.graduationrehearsal.RecycleView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import ku.reh.gdu.graduationrehearsal.Fragment.NewsFragment;
import ku.reh.gdu.graduationrehearsal.Model.NewsModel;
import ku.reh.gdu.graduationrehearsal.R;
import ku.reh.gdu.graduationrehearsal.Util.Myfer;

public class ReAdapter extends RecyclerView.Adapter<ReAdapter.MyHolder> {

    Context context;
    List<NewsModel> val;

    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;

    public ReAdapter(Context context){

        this.context = context;
        sharedPreferences = ((AppCompatActivity) context).getSharedPreferences(Myfer.MY_FER,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }


    public void UpdateData(List<NewsModel> val) {

        this.val = val;

    }

    @NonNull
    @Override
    public ReAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_news,parent,false);
        return new MyHolder(v,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ReAdapter.MyHolder holder, final int position) {

        holder.tv_topic.setText(val.get(position).getTopic());
        holder.tv_detail.setText("\t"+val.get(position).getDetails());
        holder.tv_update_at.setText(val.get(position).getUpdatedAt());

        holder.setOnClickRecycleView(new RecycleViewOnClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick, MotionEvent motionEvent) {
//                Toast.makeText(context, ""+val.get(position).getDetails(), Toast.LENGTH_SHORT).show();
                showDetail(val.get(position).getDetails(),val.get(position).getTopic(),val.get(position).getUpdatedAt());
            }

            @Override
            public void onLongClick(View view, int position, boolean isLongClick, MotionEvent motionEvent) {
        }
        });

    }

    private void showDetail(String detail,String topic,String update_at){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View v = inflater.inflate(R.layout.row_news, null);

        TextView default_tv ,topic_tv,date_tv;
        default_tv = v.findViewById(R.id.tv_detail);
        topic_tv  = v.findViewById(R.id.tv_topic);
        date_tv = v.findViewById(R.id.tv_date_at);


        default_tv.setText(detail);
        topic_tv.setText(topic);
        date_tv.setText(update_at);
        AlertDialog.Builder  builder = new AlertDialog.Builder(context);
        builder.setTitle("ดูข่าวสาร");
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
        TextView tv_topic, tv_detail,tv_update_at;
        RecycleViewOnClickListener listener;

        public MyHolder(View v,Context context) {
            super(v);

            this.context = context;
            tv_topic  = itemView.findViewById(R.id.tv_topic);
            tv_detail = itemView.findViewById(R.id.tv_detail);
            tv_update_at = itemView.findViewById(R.id.tv_date_at);

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
