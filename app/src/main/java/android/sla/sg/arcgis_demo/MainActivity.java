package android.sla.sg.arcgis_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import java.util.List;
import java.util.StringTokenizer;

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
import com.esri.core.ogc.wmts.WMTSLayerInfo;
import com.esri.core.ogc.wmts.WMTSServiceInfo;
import com.esri.core.ogc.wmts.WMTSStyle;

public class MainActivity extends AppCompatActivity {

    String defaultMapStyle = "DEFAULT";

    MapView mMapView;

    WMTSLayer currentBaseMapLayer;

    List<WMTSLayerInfo> omLayerInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMapView = (MapView) findViewById(R.id.map);

        mMapView.setExtent(new Envelope(11532933.483206868,135149.29269599967,11582617.551592167,165227.26332617598));

        //Using the redirect link works
        //WMTSLayer wmtsLayer = new WMTSLayer("https://mapproxy.onemap.sg/wmts/1.0.0/WMTSCapabilities.xml");
        WMTSLayer wmtsLayer = new WMTSLayer("https://mapservices.onemap.sg/mapproxy/wmts/1.0.0/WMTSCapabilities.xml"); //Stable xml

        //Cannot work, will https://mapservices-uat.onemap.sg/wmts will redirect to https://mapproxy.onemap.sg/wmts/1.0.0/WMTSCapabilities.xml
//        WMTSLayer wmtsLayer = new WMTSLayer("https://mapservices-uat.onemap.sg/wmts");

        //Production wmts url
//        WMTSLayer wmtsLayer = new WMTSLayer("https://mapservices.onemap.sg/wmts");

        wmtsLayer.setOnStatusChangedListener(new OnStatusChangedListener() {
            @Override
            public void onStatusChanged(Object source, STATUS status)
            {
                if (status == STATUS.INITIALIZED)
                {
                    WMTSLayer layer = (WMTSLayer) source;

                    WMTSServiceInfo serviceInfo = layer.getWmtsServiceInfo();

                    omLayerInfos = serviceInfo.getLayerInfos();

                    for (WMTSLayerInfo layerInfo : omLayerInfos)
                    {
                        if(layerInfo.getTitle().toUpperCase().equals(defaultMapStyle))
                        {
                            currentBaseMapLayer = new WMTSLayer(layerInfo, layer.getSpatialReference());

                            currentBaseMapLayer.setOnStatusChangedListener(new OnStatusChangedListener() {
                                @Override
                                public void onStatusChanged(Object source, STATUS status) {
                                    if (status == STATUS.INITIALIZED) {
                                        mMapView.addLayer(currentBaseMapLayer);
                                    }
                                }
                            });

                            currentBaseMapLayer.layerInitialise();
                        }
                    }

                    //For testing purpose, comment off for loop above to activate
//                    WMTSLayerInfo layerInfo = omLayerInfos.get(1);
//
//                    currentBaseMapLayer = new WMTSLayer(layerInfo, layer.getSpatialReference());
//
//                    currentBaseMapLayer.setOnStatusChangedListener(new OnStatusChangedListener() {
//                        @Override
//                        public void onStatusChanged(Object source, STATUS status) {
//                            if (status == STATUS.INITIALIZED) {
//                                mMapView.addLayer(currentBaseMapLayer);
//                            }
//                        }
//                    });
//                    currentBaseMapLayer.layerInitialise();
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
