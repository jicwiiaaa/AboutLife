package com.jicwiiaaa.aboutlife.adapter;

import android.content.Context;

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
