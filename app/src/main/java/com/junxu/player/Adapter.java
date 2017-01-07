package com.junxu.player;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/6.
 */
public class Adapter extends BaseAdapter {

    private Context mContext;
    private List<Map<String,String>> mVideoInfo;
    private LayoutInflater mInflater;

    public Adapter(Context mContext,List<Map<String,String>> mVideoInfo ){
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.mVideoInfo = mVideoInfo;
    }

    @Override
    public int getCount() {
        return mVideoInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return mVideoInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder mViewHolder=null;

        if(convertView==null){
            mViewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.lv_item,null);
            convertView.setTag(mViewHolder);
        }else{
           mViewHolder = (ViewHolder) convertView.getTag();
        }
        if(mViewHolder!=null){
            mViewHolder.mVideoFileName = (TextView) convertView.findViewById(R.id.videoFileName);
            mViewHolder.mVideoFileTime = (TextView) convertView.findViewById(R.id.videoFileTime);
        }

        mViewHolder.mVideoFileName.setText(mVideoInfo.get(position).get("videoFileName"));
        mViewHolder.mVideoFileTime.setText(mVideoInfo.get(position).get("videoFileTime"));

        return convertView;
    }

    static class ViewHolder{
        TextView mVideoFileName;
        TextView mVideoFileTime;
    }
}
