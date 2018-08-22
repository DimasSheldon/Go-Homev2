package sheldon.com.android.gohomev2.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sheldon.com.android.gohomev2.R;
import sheldon.com.android.gohomev2.activities.MainActivity;
import sheldon.com.android.gohomev2.adapters.DigitalInputAdapter;
import sheldon.com.android.gohomev2.content.WidgetMonitor;
import sheldon.com.android.gohomev2.adapters.AnalogInputAdapter;

public class MonitorFragment extends Fragment {

    private static AnalogInputAdapter analogInputAdapter;
    private static DigitalInputAdapter digitalInputAdapter;
    private static RecyclerView mRecyclerViewAI, mRecyclerViewDI;
    private static ImageButton mExpandButtonAI, mExpandButtonDI;
    private static int positionAI = 0;
    private static int positionDI = 0;
    private static ArrayList<WidgetMonitor> widgetsAI;
    private static ArrayList<WidgetMonitor> widgetsDI;

    public MonitorFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        widgetsAI = new ArrayList<>();
        widgetsDI = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_monitor, container, false);

        mRecyclerViewAI = (RecyclerView) rootView.findViewById(R.id.rv_analog_input);
        mRecyclerViewAI.setHasFixedSize(true);

        mRecyclerViewDI = (RecyclerView) rootView.findViewById(R.id.rv_digital_input);
        mRecyclerViewDI.setHasFixedSize(true);

        //empty widgets
        initiateEmptyWidgets();

        analogInputAdapter = new AnalogInputAdapter(widgetsAI);
        digitalInputAdapter = new DigitalInputAdapter(widgetsDI);
        mRecyclerViewAI.setAdapter(analogInputAdapter);
        mRecyclerViewDI.setAdapter(digitalInputAdapter);

        LinearLayoutManager llmAI = new LinearLayoutManager(getActivity());
        LinearLayoutManager llmDI = new LinearLayoutManager(getActivity());
        mRecyclerViewAI.setLayoutManager(llmAI);
        mRecyclerViewDI.setLayoutManager(llmDI);

        mExpandButtonAI = (ImageButton) rootView.findViewById(R.id.expand_button_ai);
        mExpandButtonDI = (ImageButton) rootView.findViewById(R.id.expand_button_di);

        mExpandButtonAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRecyclerViewAI.getVisibility() == View.VISIBLE) {
                    mExpandButtonAI.setImageResource(R.drawable.ic_expand_more_black_36dp);
                    mRecyclerViewAI.setVisibility(View.GONE);
                } else {
                    mExpandButtonAI.setImageResource(R.drawable.ic_expand_less_black_36dp);
                    mRecyclerViewAI.setVisibility(View.VISIBLE);
                }
            }
        });
        mExpandButtonDI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRecyclerViewDI.getVisibility() == View.VISIBLE) {
                    mExpandButtonDI.setImageResource(R.drawable.ic_expand_more_black_36dp);
                    mRecyclerViewDI.setVisibility(View.GONE);
                } else {
                    mExpandButtonDI.setImageResource(R.drawable.ic_expand_less_black_36dp);
                    mRecyclerViewDI.setVisibility(View.VISIBLE);
                }
            }
        });

        return rootView;
    }

    private static void initiateEmptyWidgets() {
        widgetsAI.add(new WidgetMonitor("PLAIN_LOGO", "Monitoring", "GRAY", "NA"));
        widgetsDI.add(new WidgetMonitor("PLAIN_LOGO", "Monitoring", "GRAY", "NA"));
    }

    public static void updateDataAI(JSONObject jsonObject) {
        Log.d("POSITION_MONITOR", "updateDataAI: " + positionAI);
        Log.d("WIDGET_SIZE", "updateDataAI: " + widgetsAI.size());

        if (!(positionAI >= widgetsAI.size())) {
            Log.d("MONITOR_FRAGMENT", "UPDATE");
            try {
                widgetsAI.get(positionAI).setIcon(jsonObject.getString("icon"));
                widgetsAI.get(positionAI).setLabel(jsonObject.getString("label"));
                widgetsAI.get(positionAI).setColor(jsonObject.getString("color"));
                widgetsAI.get(positionAI).setValue(jsonObject.getString("value"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Log.d("MONITOR_FRAGMENT", "ADD");
                widgetsAI.add(positionAI, new WidgetMonitor(jsonObject.getString("icon"),
                        jsonObject.getString("label"),
                        jsonObject.getString("color"),
                        jsonObject.getString("value")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        analogInputAdapter.notifyDataSetChanged();

        positionAI++;
    }

    public static void updateDataDI(JSONObject jsonObject) {
        Log.d("POSITION_MONITOR", "updateDataDI: " + positionDI);
        Log.d("WIDGET_SIZE", "updateDataDI: " + widgetsDI.size());

        if (!(positionDI >= widgetsDI.size())) {
            Log.d("MONITOR_FRAGMENT", "UPDATE");
            try {
                widgetsDI.get(positionDI).setIcon(jsonObject.getString("icon"));
                widgetsDI.get(positionDI).setLabel(jsonObject.getString("label"));
                widgetsDI.get(positionDI).setColor(jsonObject.getString("color"));
                widgetsDI.get(positionDI).setValue(jsonObject.getString("value"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Log.d("MONITOR_FRAGMENT", "ADD");
                widgetsDI.add(positionDI, new WidgetMonitor(jsonObject.getString("icon"),
                        jsonObject.getString("label"),
                        jsonObject.getString("color"),
                        jsonObject.getString("value")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        digitalInputAdapter.notifyDataSetChanged();

        positionDI++;
    }

    public static void resetPosition() {
        positionAI = 0;
        positionDI = 0;
    }

    public static void removeUnusedAIWidgets() {
        for (int i = MainActivity.countUpdateAI; i < widgetsAI.size(); i++) {
            widgetsAI.remove(i);
        }

        analogInputAdapter.notifyItemRemoved(positionAI);
        analogInputAdapter.notifyItemRangeRemoved(positionAI, analogInputAdapter.getItemCount());
    }

    public static void removeUnusedDIWidgets() {
        for (int i = MainActivity.countUpdateDI; i < widgetsDI.size(); i++) {
            widgetsDI.remove(i);
        }

        digitalInputAdapter.notifyItemRemoved(positionDI);
        digitalInputAdapter.notifyItemRangeRemoved(positionDI, digitalInputAdapter.getItemCount());
    }
}