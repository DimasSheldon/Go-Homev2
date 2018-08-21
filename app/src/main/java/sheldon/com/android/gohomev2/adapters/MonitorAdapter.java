package sheldon.com.android.gohomev2.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import sheldon.com.android.gohomev2.R;
import sheldon.com.android.gohomev2.content.WidgetMonitor;

public class MonitorAdapter extends RecyclerView.Adapter<MonitorAdapter.WidgetViewHolder> {

    private ArrayList<WidgetMonitor> widgets;

    public static class WidgetViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView widgetLabel;
        TextView widgetValue;
        ImageView widgetIcon;
        View view;

        public WidgetViewHolder(View itemView) {
            super(itemView);

            cv = (CardView) itemView.findViewById(R.id.cv_monitor);
            widgetIcon = (ImageView) itemView.findViewById(R.id.widget_icon_monitor);
            widgetLabel = (TextView) itemView.findViewById(R.id.widget_label_monitor);
            widgetValue = (TextView) itemView.findViewById(R.id.widget_value_monitor);

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

    public MonitorAdapter(ArrayList<WidgetMonitor> widgets) {
        this.widgets = widgets;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public WidgetViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_monitor, viewGroup, false);
        WidgetViewHolder wvh = new WidgetViewHolder(v);
        return wvh;
    }

    @Override
    public void onBindViewHolder(WidgetViewHolder widgetViewHolder, int position) {
        Log.d("OnBindMonitor", String.valueOf(position));

        // Icon
        widgetViewHolder.widgetIcon.setImageResource(widgets.get(position).getIcon());
        // Label
        widgetViewHolder.widgetLabel.setText(widgets.get(position).getLabel());
        // Color
        widgetViewHolder.cv.setCardBackgroundColor(widgets.get(position).cvColor());
        widgetViewHolder.widgetIcon.setBackgroundColor(widgets.get(position).iconColor());
        // Value
        widgetViewHolder.widgetValue.setText(widgets.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return widgets.size();
    }

}