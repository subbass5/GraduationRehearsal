package ku.reh.gdu.graduationrehearsal.RecycleView;

import android.content.Context;
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

import ku.reh.gdu.graduationrehearsal.Model.PracticeModel;
import ku.reh.gdu.graduationrehearsal.Model.ScheduleModel;
import ku.reh.gdu.graduationrehearsal.R;
import ku.reh.gdu.graduationrehearsal.Util.Myfer;

public class PracticeAdp extends RecyclerView.Adapter<PracticeAdp.MyHolder> {
    Context context;
    List<PracticeModel> val;

    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;

    public PracticeAdp(Context context){

        this.context = context;
        sharedPreferences = ((AppCompatActivity) context).getSharedPreferences(Myfer.MY_FER,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public void UpdateData(List<PracticeModel> val) {

        this.val = val;

    }

    @NonNull
    @Override
    public PracticeAdp.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_practice,parent,false);
        return new PracticeAdp.MyHolder(v,context);
    }

    @Override
    public void onBindViewHolder(@NonNull PracticeAdp.MyHolder holder, final int position) {

        holder.tv_name.setText(val.get(position).getName());

        holder.btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDetail(val.get(position).getName(),
                           val.get(position).getIdcard(),
                           val.get(position).getScore1(),
                           val.get(position).getScore2(),
                           val.get(position).getScore3(),
                           val.get(position).getScore4(),
                           val.get(position).getScore5(),
                           val.get(position).getScore6()
                        );

            }
        });


        holder.setOnClickRecycleView(new RecycleViewOnClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick, MotionEvent motionEvent) {
//                Toast.makeText(context, ""+val.get(position).getDetails(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLongClick(View view, int position, boolean isLongClick, MotionEvent motionEvent) {
            }
        });

    }

    private void showDetail(String name,String idcard, String score1, String score2 , String score3 ,String score4,String score5,String score6){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View v = inflater.inflate(R.layout.layout_show_practice, null);


        AlertDialog.Builder  builder = new AlertDialog.Builder(context);
//        builder.setTitle("คะแนน ของ "+name);
        builder.setView(v);
        TextView tv_cardid,tv_score1,tv_score2,tv_score3,tv_score4,tv_score5,tv_score6;
        final AlertDialog dialog =  builder.show();
        tv_cardid = v.findViewById(R.id.tv_cardid);

        tv_score1 = v.findViewById(R.id.tv_score1);
        tv_score2 = v.findViewById(R.id.tv_score2);
        tv_score3 = v.findViewById(R.id.tv_score3);
        tv_score4 = v.findViewById(R.id.tv_score4);
        tv_score5 = v.findViewById(R.id.tv_score5);
        tv_score6 = v.findViewById(R.id.tv_score6);

        tv_cardid.setText("ID CARD : "+idcard);
        tv_score1.setText(score1);
        tv_score2.setText(score2);
        tv_score3.setText(score3);
        tv_score4.setText(score4);
        tv_score5.setText(score5);
        tv_score6.setText(score6);


        Button btnClose = v.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        TextView tv_name;
        Button btn_view;
        RecycleViewOnClickListener listener;

        public MyHolder(View v,Context context) {

            super(v);
            this.context = context;

            tv_name  = itemView.findViewById(R.id.tv_name);
            btn_view = itemView.findViewById(R.id.btn_view);

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
