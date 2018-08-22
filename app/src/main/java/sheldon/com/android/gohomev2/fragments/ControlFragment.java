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
import sheldon.com.android.gohomev2.adapters.DigitalOutputAdapter;
import sheldon.com.android.gohomev2.content.WidgetControl;

public class ControlFragment extends Fragment {

    private static DigitalOutputAdapter digitalOutputAdapter;
    private static RecyclerView mRecyclerViewDO;
    private static ImageButton mExpandButtonDO;
    private static int positionDO = 0;

    private static ArrayList<WidgetControl> widgetsDO;

    public ControlFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        widgetsDO = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_control, container, false);


        mRecyclerViewDO = (RecyclerView) rootView.findViewById(R.id.rv_control);
        mRecyclerViewDO.setHasFixedSize(true);

        //empty widgetsDO
        initiateEmptyWidgets();

        digitalOutputAdapter = new DigitalOutputAdapter(widgetsDO);
        mRecyclerViewDO.setAdapter(digitalOutputAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerViewDO.setLayoutManager(llm);

        mExpandButtonDO = (ImageButton) rootView.findViewById(R.id.expand_button_do);
        mExpandButtonDO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRecyclerViewDO.getVisibility() == View.VISIBLE) {
                    mExpandButtonDO.setImageResource(R.drawable.ic_expand_more_black_36dp);
                    mRecyclerViewDO.setVisibility(View.GONE);
                } else {
                    mExpandButtonDO.setImageResource(R.drawable.ic_expand_less_black_36dp);
                    mRecyclerViewDO.setVisibility(View.VISIBLE);
                }
            }
        });
        return rootView;
    }

    private static void initiateEmptyWidgets() {
        widgetsDO.add(new WidgetControl("Control", "GRAY", "NA"));
    }

    public static void updateDataDO(JSONObject jsonObject) {
        Log.d("POSITION_CONTROL", "updateDataAI: " + positionDO);
        Log.d("WIDGET_SIZE", "updateDataAI: " + widgetsDO.size());

        if (!(positionDO >= widgetsDO.size())) {
            Log.d("CONTROL_FRAGMENT", "UPDATE");
            try {
                widgetsDO.get(positionDO).setLabel(jsonObject.getString("label"));
                widgetsDO.get(positionDO).setColor(jsonObject.getString("color"));
                widgetsDO.get(positionDO).setValue(jsonObject.getString("value"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Log.d("CONTROL_FRAGMENT", "ADD");
                widgetsDO.add(positionDO, new WidgetControl(jsonObject.getString("label"),
                        jsonObject.getString("color"),
                        jsonObject.getString("value")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        digitalOutputAdapter.notifyDataSetChanged();

        positionDO++;
    }

    public static void resetPosition() {
        positionDO = 0;
    }

    public static void removeUnusedD0Widgets() {
        for (int i = MainActivity.countUpdateDO; i < widgetsDO.size(); i++) {
            widgetsDO.remove(i);
        }

        digitalOutputAdapter.notifyItemRemoved(positionDO);
        digitalOutputAdapter.notifyItemRangeRemoved(positionDO, digitalOutputAdapter.getItemCount());
    }
}