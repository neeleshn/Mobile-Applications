package com.AtsNeuEdu.AndroidproximityDh4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.AtsNeuEdu.AndroidproximityDh4.estimote.BeaconID;
import com.AtsNeuEdu.AndroidproximityDh4.estimote.EstimoteCloudBeaconDetails;
import com.AtsNeuEdu.AndroidproximityDh4.estimote.EstimoteCloudBeaconDetailsFactory;
import com.AtsNeuEdu.AndroidproximityDh4.estimote.ProximityContentManager;
import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.cloud.model.Color;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final Map<Color, Integer> BACKGROUND_COLORS = new HashMap<>();

    static {
        BACKGROUND_COLORS.put(Color.ICY_MARSHMALLOW, android.graphics.Color.rgb(109, 170, 199));
        BACKGROUND_COLORS.put(Color.BLUEBERRY_PIE, android.graphics.Color.rgb(98, 84, 158));
        BACKGROUND_COLORS.put(Color.MINT_COCKTAIL, android.graphics.Color.rgb(155, 186, 160));
    }

    private static final int BACKGROUND_COLOR_NEUTRAL = android.graphics.Color.rgb(160, 169, 172);

    private ProximityContentManager proximityContentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        proximityContentManager = new ProximityContentManager(this,
                Arrays.asList(new BeaconID("XXX", 1, 0)),
                new EstimoteCloudBeaconDetailsFactory());
        proximityContentManager.setListener(new ProximityContentManager.Listener() {
            @Override
            public void onContentChanged(Object content) {
                String text;
                Integer backgroundColor;
                if (content != null) {
                    EstimoteCloudBeaconDetails beaconDetails = (EstimoteCloudBeaconDetails) content;
                    if(beaconDetails.getBeaconName().equalsIgnoreCase("JesseMint")){
                        text = "You're in Kick Off Room.\nFollowing are the sessions here:\n - 10:00 am - Commencement\n - 04:00 pm Closing Session";
                        backgroundColor = BACKGROUND_COLORS.get(beaconDetails.getBeaconColor());
                    } else if (beaconDetails.getBeaconName().equalsIgnoreCase("AnaIce")){
                        text = "You're in Room 201.\nFollowing are the sessions here:\n - 12:30 pm - Tech Talk1\n - 2:00pm Tech Talk2";
                        backgroundColor = BACKGROUND_COLORS.get(beaconDetails.getBeaconColor());
                    } else {
                        text = "You're in Room 202.\nFollowing are the sessions here:\n - 12:30 pm - Tech Talk1\n - 2:00pm Tech Talk2";
                        backgroundColor = BACKGROUND_COLORS.get(beaconDetails.getBeaconColor());
                    }
                } else {
                    text = "No beacons in range.";
                    backgroundColor = null;
                }
                ((TextView) findViewById(R.id.textView)).setText(text);
                findViewById(R.id.relativeLayout).setBackgroundColor(
                        backgroundColor != null ? backgroundColor : BACKGROUND_COLOR_NEUTRAL);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            Log.e(TAG, "Can't scan for beacons, some pre-conditions were not met");
            Log.e(TAG, "Read more about what's required at: http://estimote.github.io/Android-SDK/JavaDocs/com/estimote/sdk/SystemRequirementsChecker.html");
            Log.e(TAG, "If this is fixable, you should see a popup on the app's screen right now, asking to enable what's necessary");
        } else {
            Log.d(TAG, "Starting ProximityContentManager content updates");
            proximityContentManager.startContentUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Stopping ProximityContentManager content updates");
        proximityContentManager.stopContentUpdates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        proximityContentManager.destroy();
    }
}
