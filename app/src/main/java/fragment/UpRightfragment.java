package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hmidemo.R;
import com.example.hmidemo.RadarView;
import com.example.hmidemo.Var;
import com.example.hmidemo.Var2radar;
import com.example.hmidemo.util.Connection;

import static android.content.ContentValues.TAG;


/**
 * 雷达图
 * Created by Administrator on 2017/11/24 0024.
 */

public class UpRightfragment extends Fragment{

    private RadarView mRadarView;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        final View view=inflater.inflate(R.layout.upr_fragment,container,false);

       mRadarView = view.findViewById(R.id.radar_view);
//       mRadarView.setSearching(true);

        Var2radar.getInstance().setBroadListener(new Var2radar.BroadListener() {
            @Override
            public View onSuccess() {

                if (Connection.intarr!=null){
                    int count=Connection.intarr[9];

                    Log.i(TAG,count+"radar data");

                    mRadarView.addPoint(count);
                    mRadarView.setSearching(true);

                } Log.i(TAG,"radar ui updata");
                return view;

            }
        });
        return view;
    }

}

