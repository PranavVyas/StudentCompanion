package com.vyas.pranav.studentcompanion.holidays;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.data.holidayDatabase.HolidayEntry;
import com.vyas.pranav.studentcompanion.extraUtils.Converters;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HolidaysAdapter extends RecyclerView.Adapter<HolidaysAdapter.HolidayHolder> {
    private Context mContext;
    private List<HolidayEntry> mHolidays;

    HolidaysAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public HolidayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_holder_holiday,parent,false);
        return new HolidayHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolidayHolder holder, int position) {
        holder.mHolidayNo.setText(String.format(Locale.US, "%d.", position + 1));
        holder.mHolidayDate.setText(Converters.convertDateToString(mHolidays.get(position).getHolidayDate()));
        holder.mHolidayName.setText(mHolidays.get(position).getHolidayName());
        holder.mHolidayDay.setText(mHolidays.get(position).getHolidayDay());
    }

    @Override
    public int getItemCount() {
        return (mHolidays == null) ? 0 : (mHolidays.size());
    }

    public void setmHolidays(List<HolidayEntry> mHolidays) {
        this.mHolidays = mHolidays;
        notifyDataSetChanged();
    }

    class HolidayHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_recycler_holiday_no) TextView mHolidayNo;
        @BindView(R.id.tv_recycler_holiday_date) TextView mHolidayDate;
        @BindView(R.id.tv_recycler_holiday_name) TextView mHolidayName;
        @BindView(R.id.tv_recycler_holiday_day) TextView mHolidayDay;
        public HolidayHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
