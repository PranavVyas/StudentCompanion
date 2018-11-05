package com.vyas.pranav.studentcompanion.timetable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.timetable.viewModels.Cell;
import com.vyas.pranav.studentcompanion.timetable.viewModels.ColumnHeader;
import com.vyas.pranav.studentcompanion.timetable.viewModels.RowHeader;

public class TimetableAdapter extends AbstractTableAdapter<ColumnHeader, RowHeader, Cell> {

    Context mContext;

    public TimetableAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public int getColumnHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getRowHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getCellItemViewType(int position) {
        return 0;
    }

    @Override
    public AbstractViewHolder onCreateCellViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_holder_timetable_cell, parent, false);
        return new MyCellViewHolder(view);
    }

    @Override
    public void onBindCellViewHolder(AbstractViewHolder holder, Object cellItemModel, int columnPosition, int rowPosition) {
        Cell cellDetail = (Cell) cellItemModel;
        MyCellViewHolder viewHolder = (MyCellViewHolder) holder;
        viewHolder.tvCell.setText(cellDetail.getmData());
//        viewHolder.itemView.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
//        viewHolder.tvCell.requestLayout();
        //TODO TRY TO fi width problem here
    }

    @Override
    public AbstractViewHolder onCreateColumnHeaderViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_holder_timetable_column, parent, false);
        return new MyColumnViewHolder(view);
    }

    @Override
    public void onBindColumnHeaderViewHolder(AbstractViewHolder holder, Object columnHeaderItemModel, int columnPosition) {
        ColumnHeader columnDetail = (ColumnHeader) columnHeaderItemModel;
        MyColumnViewHolder viewHolderColumn = (MyColumnViewHolder) holder;
        viewHolderColumn.tvColumn.setText(columnDetail.getmData());
//        viewHolderColumn.itemView.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
//        viewHolderColumn.tvColumn.requestLayout();
    }

    @Override
    public AbstractViewHolder onCreateRowHeaderViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_holder_timetable_row, parent, false);
        return new MyRowViewHolder(view);
    }

    @Override
    public void onBindRowHeaderViewHolder(AbstractViewHolder holder, Object rowHeaderItemModel, int rowPosition) {
        RowHeader rowDetail = (RowHeader) rowHeaderItemModel;
        MyRowViewHolder viewHolderRow = (MyRowViewHolder) holder;
        viewHolderRow.tvRow.setText(rowDetail.getmData());
//        viewHolderRow.itemView.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
//        viewHolderRow.tvRow.requestLayout();
    }

    @Override
    public View onCreateCornerView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_holder_timetale_corner, null);
        return view;
    }

    class MyCellViewHolder extends AbstractViewHolder {
        TextView tvCell;

        //@BindView(R.id.tv_timetable_cell)TextView tvCell;
        public MyCellViewHolder(View itemView) {
            super(itemView);
            tvCell = itemView.findViewById(R.id.tv_timetable_cell);
            //ButterKnife.bind(this,itemView);
        }
    }

    class MyColumnViewHolder extends AbstractViewHolder {
        TextView tvColumn;

        //@BindView(R.id.tv_timetable_column)TextView tvColumn;
        public MyColumnViewHolder(View itemView) {
            super(itemView);
            tvColumn = itemView.findViewById(R.id.tv_timetable_column);
            //ButterKnife.bind(this,itemView);
        }
    }

    class MyRowViewHolder extends AbstractViewHolder {
        TextView tvRow;

        //@BindView(R.id.tv_timetable_row)TextView tvRow;
        public MyRowViewHolder(View itemView) {
            super(itemView);
            tvRow = itemView.findViewById(R.id.tv_timetable_row);
            //ButterKnife.bind(this,itemView);
        }
    }
}
