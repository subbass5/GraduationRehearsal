package ku.reh.gdu.graduationrehearsal.RecycleView;

import android.view.MotionEvent;
import android.view.View;

public interface RecycleViewOnClickListener {

    void onClick(View view, int position, boolean isLongClick, MotionEvent motionEvent);
    void onLongClick(View view, int position, boolean isLongClick, MotionEvent motionEvent);

}
