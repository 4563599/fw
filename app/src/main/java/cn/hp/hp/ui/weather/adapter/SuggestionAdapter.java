package cn.hp.hp.ui.weather.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hp.hp.R;
import cn.hp.hp.model.FakeWeather;
import cn.hp.hp.widgets.CircleImageView;

import java.util.List;

/**
 * Created by liyu on 2017/4/1.
 */

public class SuggestionAdapter extends BaseQuickAdapter<FakeWeather.FakeSuggestion, BaseViewHolder> {

    public SuggestionAdapter(int layoutResId, List<FakeWeather.FakeSuggestion> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, FakeWeather.FakeSuggestion item) {
        CircleImageView circleImageView = holder.getView(R.id.civ_suggesstion);
        holder.setText(R.id.tvName, item.getTitle());
        holder.setText(R.id.tvMsg, item.getMsg());
        circleImageView.setFillColor(item.getIconBackgroudColor());
        circleImageView.setImageResource(item.getIcon());
    }
}
