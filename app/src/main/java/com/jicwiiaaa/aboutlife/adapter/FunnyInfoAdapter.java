package com.jicwiiaaa.aboutlife.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jicwiiaaa.aboutlife.R;
import com.jicwiiaaa.aboutlife.model.FunnyInfo;

import org.byteam.superadapter.IMulItemViewType;
import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by liuliu on 2017/6/13.
 */

public class FunnyInfoAdapter extends SuperAdapter<FunnyInfo> {
    public FunnyInfoAdapter(Context context, List<FunnyInfo> items, IMulItemViewType<FunnyInfo> mulItemViewType) {
        super(context, items, mulItemViewType);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, FunnyInfo item) {
        switch (viewType) {
            case 10:
                holder.setText(R.id.tv_name, item.getName());
                holder.setText(R.id.tv_time, item.getCreate_time());
                holder.setText(R.id.tv_desc, item.getText());
                Glide.with(getContext()).load(item.getProfile_image()).into((ImageView) holder.findViewById(R.id.img_header));
                if (item.getCdn_img().endsWith(".gif")) {
                    Glide.with(getContext()).load(item.getCdn_img()).asGif().override(200, 200).into((ImageView) holder.findViewById(R.id.img_content));
                }else {
                    Glide.with(getContext()).load(item.getCdn_img()).override(200, 200).into((ImageView) holder.findViewById(R.id.img_content));
                }
                break;
            case 29:
                break;
            case 31:
                break;
            case 41:
                break;
        }
    }

    @Override
    protected IMulItemViewType<FunnyInfo> offerMultiItemViewType() {
        return new IMulItemViewType<FunnyInfo>() {
            @Override
            public int getViewTypeCount() {
                return 4;
            }

            @Override
            public int getItemViewType(int position, FunnyInfo funnyInfo) {
                return Integer.parseInt(funnyInfo.getType());
            }

            @Override
            public int getLayoutId(int viewType) {
                switch (viewType) {
                    case 10:
                        return R.layout.item_funny_info_pic;
                    case 29:
                        return R.layout.item_funny_info_text;
                    case 31:
                        return R.layout.item_funny_info_voice;
                    case 41:
                        return R.layout.item_funny_info_video;
                }
                return R.layout.item_funny_info_pic;
            }
        };
    }
}
