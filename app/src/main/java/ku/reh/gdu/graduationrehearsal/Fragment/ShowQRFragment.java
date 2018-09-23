package ku.reh.gdu.graduationrehearsal.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ku.reh.gdu.graduationrehearsal.R;
import ku.reh.gdu.graduationrehearsal.Util.Myfer;

import static ku.reh.gdu.graduationrehearsal.Util.ApiUtil.BASE_URL;
import static ku.reh.gdu.graduationrehearsal.Util.Myfer.MY_FER;
import static ku.reh.gdu.graduationrehearsal.Util.Myfer.QR_CODE;

public class ShowQRFragment  extends Fragment{

    SharedPreferences sharedPreferences;
    ImageView imageView;
    Context context;
    String urlQR = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_showqr,container,false);
        init(v);
        return v;
    }

    private void init(View v) {
            imageView = v.findViewById(R.id.img_qr);
            sharedPreferences = getActivity().getSharedPreferences(MY_FER,Context.MODE_PRIVATE);
            urlQR = sharedPreferences.getString(QR_CODE,"");
            Log.e("URLQR",BASE_URL+urlQR);
            try {
                if(!urlQR.isEmpty()){

                    Picasso.get().load(BASE_URL+urlQR).into(imageView);
                }

            }catch (Exception e){
                e.printStackTrace();
            }

    }
}
