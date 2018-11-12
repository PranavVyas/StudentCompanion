package com.vyas.pranav.studentcompanion.holidays;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.data.holidayDatabase.HolidayEntry;
import com.vyas.pranav.studentcompanion.extrautils.Converters;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type Holidays adapter.
 */
public class HolidaysAdapter extends RecyclerView.Adapter<HolidaysAdapter.HolidayHolder> {
    private Context mContext;
    private List<HolidayEntry> mHolidays;

    /**
     * Instantiates a new Holidays adapter.
     *
     * @param mContext the context
     */
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

    /**
     * Sets holidays.
     *
     * @param mHolidays the m holidays
     */
    public void setmHolidays(List<HolidayEntry> mHolidays) {
        this.mHolidays = mHolidays;
        notifyDataSetChanged();
    }

    /**
     * The type Holiday holder.
     */
    class HolidayHolder extends RecyclerView.ViewHolder {
        /**
         * The holiday no.
         */
        @BindView(R.id.tv_recycler_holiday_no) TextView mHolidayNo;
        /**
         * The holiday date.
         */
        @BindView(R.id.tv_recycler_holiday_date) TextView mHolidayDate;
        /**
         * The holiday name.
         */
        @BindView(R.id.tv_recycler_holiday_name) TextView mHolidayName;
        /**
         * The holiday day.
         */
        @BindView(R.id.tv_recycler_holiday_day) TextView mHolidayDay;

        /**
         * Instantiates a new Holiday holder.
         *
         * @param itemView the item view
         */
        public HolidayHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
