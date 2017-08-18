package com.roch.fupin.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.roch.fupin.dialog.CheckPhoneDialog;
import com.roch.fupin.entity.PoorHousePhoto;
import com.roch.fupin.utils.ResourceUtil;
import com.roch.fupin_2_0.R;

import java.util.List;

public class fragmentAdapter3 extends BaseAdapter {

    /**
     * 按日期排序的ListView的数据源---对象类型的集合
     */
    private List<PoorHousePhoto> list;

    protected LayoutInflater mInflater;
    private newchildAdapter1 adapter;
    private Context context;

    public fragmentAdapter3(Context context, List<PoorHousePhoto> list) {
        this.list = list;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listviewgrid, null);
            holder = new ViewHolder();
            holder.mTextViewTitle = (TextView) convertView.findViewById(R.id.date);
            holder.phone = (TextView) convertView.findViewById(R.id.phone);
            holder.mGrid = (GridView) convertView.findViewById(R.id.mGrid);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final PoorHousePhoto mImageBean = list.get(position);
        //照片组的时间
        holder.mTextViewTitle.setText(mImageBean.getShijian());
        System.out.println("照片组的时间为===" + mImageBean.getShijian());
        //照片组的电话
        holder.phone.setText(mImageBean.getUserphone());
        holder.phone.setTextColor(ResourceUtil.getInstance().getColorById(R.color.color_00aff0));
        holder.phone.setClickable(true);
        if(!"未知".equals(mImageBean.getUserphone())){ //如果照片组的电话不是“未知”，就能打电话
            holder.phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPhoneDialog(mImageBean.getUserphone());
                }
            });
        }
        System.out.println("照片组的电话为===" + mImageBean.getUserphone());

        //给照片组的GridView设置适配器
        adapter = new newchildAdapter1(context, list.get(position).getLiam(), holder.mGrid);
        holder.mGrid.setAdapter(adapter);

        //给照片组的GridView设置条目点击事件
        holder.mGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                if(null!=mOnClickListner){
                    mOnClickListner.onClick(position,pos);
                }
            }
        });
        //给照片组的GridView设置条目长按事件
        holder.mGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {
                if(null!=mOnLongClickListner){
                    mOnLongClickListner.onLongClick(position,pos);
                }
                return true;
            }
        });
        return convertView;
    }

    public static class ViewHolder {
        public TextView mTextViewTitle;
        public TextView phone;
        public GridView mGrid;
    }

    private OnClickListner mOnClickListner;
    private OnLongClickListner mOnLongClickListner;

    public void setOnClickListner(OnClickListner onClickListner){
        mOnClickListner=onClickListner;
    }

    public void setOnLongClickListner(OnLongClickListner onLongClickListner){
        mOnLongClickListner=onLongClickListner;
    }

    /**
     * 返回TextView所显示的内容
     * @param tv
     * @return
     */
    public String getTextView(TextView tv) {
        return tv.getText().toString().trim();
    }

    /**
     * 显示自定义的对电话号码进行拨打、保存操作的dialog对话框 <br/>
     * 2016年7月15日  <br/>
     * ZhaoDongShao <br/>
     * @param phone 电话号码
     */
    public void showPhoneDialog(final String phone) {
        final CheckPhoneDialog dialog = new CheckPhoneDialog(context, R.style.popup_dialog_style);
        Window window = dialog.getWindow();

        window.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        WindowManager windowManager = (WindowManager) this.context.getSystemService(Context.WINDOW_SERVICE);
        window.setWindowManager(windowManager, null, null);
        dialog.setCanceledOnTouchOutside(true);
        window.setWindowAnimations(R.style.ContactAnimationPreview);
        dialog.show();
        dialog.setLable(phone);
        dialog.setOnClickListener(new View.OnClickListener() {
            Intent intent = null;
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.check_phone_dialog_cancel:
                        dialog.dismiss();
                        break;

                    case R.id.check_save_phone_dialog:
                        intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
                        intent.setType("vnd.android.cursor.item/person");
                        intent.setType("vnd.android.cursor.item/contact");
                        intent.setType("vnd.android.cursor.item/raw_contact");
                        intent.putExtra(android.provider.ContactsContract.Intents.Insert.PHONE, phone);
                        intent.putExtra(android.provider.ContactsContract.Intents.Insert.PHONE_TYPE, 3);
                        context.startActivity(intent);
                        break;

                    case R.id.check_call_phone_dialog:
                        intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                        context.startActivity(intent);
                        break;

                    default:
                        break;
                }
            }
        });
    }

    public interface OnClickListner{
        void onClick(int position, int pos);
    }

    public interface OnLongClickListner{
        void onLongClick(int position, int pos);
    }

}

