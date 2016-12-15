package app.rinno.com.devicewall.utils;

import android.content.Context;
import android.util.Log;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Nearable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dev21 on 09-11-16.
 */

public class ShowroomManager {

    private Listener listener;

    private BeaconManager beaconManager;
    private String scanId;

    private Map<NearableID, Boolean> nearablesMotionStatus = new HashMap<>();

    public ShowroomManager(Context context, final Map<NearableID, Product> products) {
        beaconManager = new BeaconManager(context);
        beaconManager.setNearableListener(new BeaconManager.NearableListener() {

            @Override
            public void onNearablesDiscovered(List<Nearable> list) {
                for (Nearable nearable : list) {
                    NearableID nearableID = new NearableID(nearable.identifier);
                    if (!products.keySet().contains(nearableID)) { continue; }

                    boolean previousStatus = nearablesMotionStatus.containsKey(nearableID) && nearablesMotionStatus.get(nearableID);
                    if (previousStatus != nearable.isMoving) {
                        Product product = products.get(nearableID);
                        if (nearable.isMoving) {
                            listener.onProductPickup(product);
                        } else {
                            Log.d("Stop","stop");
                        }
                        nearablesMotionStatus.put(nearableID, nearable.isMoving);
                    }
                }
            }
        });
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onProductPickup(Product product);
        void onProductPutdown(Product product);
    }

    public void startUpdates() {
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                scanId = beaconManager.startNearableDiscovery();
            }
        });
    }

    public void stopUpdates() {
        beaconManager.stopNearableDiscovery(scanId);
    }

    public void destroy() {
        beaconManager.disconnect();
    }
}
