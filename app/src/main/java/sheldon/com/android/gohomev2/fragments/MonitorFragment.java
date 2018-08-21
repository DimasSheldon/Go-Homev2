package sheldon.com.android.gohomev2.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sheldon.com.android.gohomev2.R;
import sheldon.com.android.gohomev2.activities.MainActivity;
import sheldon.com.android.gohomev2.content.WidgetMonitor;
import sheldon.com.android.gohomev2.adapters.MonitorAdapter;

public class MonitorFragment extends Fragment {

    private static MonitorAdapter monitorAdapter;
    private static RecyclerView rv;
    private static int position = 0;

    private static ArrayList<WidgetMonitor> widgets;

    public MonitorFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        widgets = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_monitor, container, false);

        rv = (RecyclerView) rootView.findViewById(R.id.rv_monitor);
        rv.setHasFixedSize(true);

        //empty widgets
        initiateEmptyWidgets();

        monitorAdapter = new MonitorAdapter(widgets);
        rv.setAdapter(monitorAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }

    private static void initiateEmptyWidgets() {
//        for (int i = 0; i < 10; i++)
//            widgets.add(new WidgetMonitor("PLAIN_LOGO", "Monitoring" + i, "GRAY", "NA"));
        widgets.add(new WidgetMonitor("PLAIN_LOGO", "Monitoring", "GRAY", "NA"));
    }

    public static void updateData(JSONObject jsonObject) {
        Log.d("POSITION_MONITOR", "updateData: " + position);
        Log.d("WIDGET_SIZE", "updateData: " + widgets.size());

        if (!(position >= widgets.size())) {
            Log.d("MONITOR_FRAGMENT", "UPDATE");
            try {
                widgets.get(position).setIcon(jsonObject.getString("icon"));
                widgets.get(position).setLabel(jsonObject.getString("label"));
                widgets.get(position).setColor(jsonObject.getString("color"));
                widgets.get(position).setValue(jsonObject.getString("value"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Log.d("MONITOR_FRAGMENT", "ADD");
                widgets.add(position, new WidgetMonitor(jsonObject.getString("icon"),
                        jsonObject.getString("label"),
                        jsonObject.getString("color"),
                        jsonObject.getString("value")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        monitorAdapter.notifyDataSetChanged();

        position++;
    }

    public static void resetPosition() {
        position = 0;
    }

    public static void removeUnusedWidgets() {
        for (int i = MainActivity.countUpdateMntr; i < widgets.size(); i++) {
            widgets.remove(i);
        }

        monitorAdapter.notifyItemRemoved(position);
        monitorAdapter.notifyItemRangeRemoved(position, monitorAdapter.getItemCount());
    }
}