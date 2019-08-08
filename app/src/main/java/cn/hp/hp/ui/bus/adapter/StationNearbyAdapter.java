package cn.hp.hp.ui.bus.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hp.hp.R;
import cn.hp.hp.model.BusLineNearby;
import cn.hp.hp.ui.bus.StationDetailActivity;

import java.util.List;

/**
 * Created by liyu on 2016/11/1.
 */

public class StationNearbyAdapter extends BaseQuickAdapter<BusLineNearby.StationBean, BaseViewHolder> {

    public StationNearbyAdapter(int layoutResId, List<BusLineNearby.StationBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final BusLineNearby.StationBean item) {
        holder.setText(R.id.tv_line_name, item.getSName() + "  " + item.getSname_info());
        holder.setText(R.id.tv_line_desc, item.getLines_info());
        holder.setText(R.id.tv_line_direction, "");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StationDetailActivity.start(mContext, item.getSName(), item.getSCode());
            }
        });
    }
}
