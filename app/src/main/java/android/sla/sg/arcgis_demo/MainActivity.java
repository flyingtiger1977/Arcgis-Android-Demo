package android.sla.sg.arcgis_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.esri.android.map.FeatureLayer;
import com.esri.android.map.Layer;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnLongPressListener;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.android.map.ogc.WMTSLayer;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Geometry;
import com.esri.core.map.CallbackListener;

public class MainActivity extends AppCompatActivity {

    MapView mMapView;
    WMTSLayer mWMTSLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMapView = (MapView) findViewById(R.id.map);
        mWMTSLayer = new WMTSLayer("http://repo.sgmap.xyz/tileserver/wmts");
        mWMTSLayer.setOpacity((float) 0.5);
        mMapView.addLayer(mWMTSLayer);
        mMapView.setExtent(new Envelope(11532933.483206868,135149.29269599967,11582617.551592167,165227.26332617598));

        mWMTSLayer.setOnStatusChangedListener(new OnStatusChangedListener() {
            @Override
            public void onStatusChanged(Object source, STATUS status) {
                if ((source == mWMTSLayer) && (status == STATUS.INITIALIZED)) {
                    Log.e("Layeradded", "LayerAdded");
                }
            }
        });
    }

    @Override
    protected void onPause(){
        mMapView.pause();
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

}
