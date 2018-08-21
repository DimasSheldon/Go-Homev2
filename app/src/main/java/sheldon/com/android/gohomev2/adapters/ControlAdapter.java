package sheldon.com.android.gohomev2.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import sheldon.com.android.gohomev2.R;
import sheldon.com.android.gohomev2.content.WidgetControl;
import sheldon.com.android.gohomev2.fragments.ControlFragment;

public class ControlAdapter extends RecyclerView.Adapter<ControlAdapter.WidgetViewHolder> {

    public static class WidgetViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView widgetLabel;
        TextView widgetValue;
        Switch widgetToggle;
        View view;

        public WidgetViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv_control);
            widgetLabel = (TextView) itemView.findViewById(R.id.widget_label_control);
            widgetValue = (TextView) itemView.findViewById(R.id.widget_value_control);
            widgetToggle = (Switch) itemView.findViewById(R.id.widget_toggle_control);

            view = itemView;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // item clicked
                    Toast.makeText(v.getContext(), "Position:" +
                            Integer.toString(getPosition()), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private ArrayList<WidgetControl> widgets;

    public ControlAdapter(ArrayList<WidgetControl> widgets) {
        this.widgets = widgets;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public WidgetViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_control, viewGroup, false);
        WidgetViewHolder wvh = new WidgetViewHolder(v);
        return wvh;
    }

    @Override
    public void onBindViewHolder(WidgetViewHolder widgetViewHolder, int position) {
        Log.d("OnBindControl", String.valueOf(position));


        // Label
        widgetViewHolder.widgetLabel.setText(widgets.get(position).getLabel());
        // Color
        widgetViewHolder.cv.setBackgroundColor(widgets.get(position).cvColor());
        widgetViewHolder.widgetToggle.setBackgroundColor(widgets.get(position).iconColor());
        // Value
        widgetViewHolder.widgetValue.setText(widgets.get(position).getValue());

        widgetViewHolder.widgetToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Switched", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return widgets.size();
    }
}