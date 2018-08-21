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
import sheldon.com.android.gohomev2.adapters.ControlAdapter;
import sheldon.com.android.gohomev2.content.WidgetControl;
import sheldon.com.android.gohomev2.content.WidgetMonitor;

public class ControlFragment extends Fragment {

    private static ControlAdapter controlAdapter;
    private static RecyclerView rv;
    private static int position = 0;

    private static ArrayList<WidgetControl> widgets;

    public ControlFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_control, container, false);

        rv = (RecyclerView) rootView.findViewById(R.id.rv_control);
        rv.setHasFixedSize(true);

        //empty widgets
        initiateEmptyWidgets();

        controlAdapter = new ControlAdapter(widgets);
        rv.setAdapter(controlAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }

    private static void initiateEmptyWidgets() {
        widgets.add(new WidgetControl("Control", "GRAY", "NA"));
    }

    public static void updateData(JSONObject jsonObject) {
        Log.d("POSITION_CONTROL", "updateData: " + position);
        Log.d("WIDGET_SIZE", "updateData: " + widgets.size());

        if (!(position >= widgets.size())) {
            Log.d("CONTROL_FRAGMENT", "UPDATE");
            try {
                widgets.get(position).setLabel(jsonObject.getString("label"));
                widgets.get(position).setColor(jsonObject.getString("color"));
                widgets.get(position).setValue(jsonObject.getString("value"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Log.d("CONTROL_FRAGMENT", "ADD");
                widgets.add(position, new WidgetControl(jsonObject.getString("label"),
                        jsonObject.getString("color"),
                        jsonObject.getString("value")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        controlAdapter.notifyDataSetChanged();

        position++;
    }

    public static void resetPosition() {
        position = 0;
    }

    public static void removeUnusedWidgets() {
        for (int i = MainActivity.countUpdateCtrl; i < widgets.size(); i++) {
            widgets.remove(i);
        }

        controlAdapter.notifyItemRemoved(position);
        controlAdapter.notifyItemRangeRemoved(position, controlAdapter.getItemCount());
    }
}