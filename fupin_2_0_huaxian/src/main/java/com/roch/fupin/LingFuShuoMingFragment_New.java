package com.roch.fupin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.roch.fupin.adapter.LingFuShuoMingAdapter;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin.dialog.NormalDailog;
import com.roch.fupin.entity.LingFuShuoMing;
import com.roch.fupin.entity.LingFuShuoMing_ResultList;
import com.roch.fupin.utils.Common;
import com.roch.fupin.utils.MyConstans;
import com.roch.fupin.utils.MyRequestCallBack;
import com.roch.fupin.utils.StringUtil;
import com.roch.fupin.utils.URLs;
import com.roch.fupin_2_0.R;

import java.util.List;

/**
 * 贫困村和贫困户的另附说明fragment
 */
public class LingFuShuoMingFragment_New extends BaseFragment {

    /**
     * 标志位，标志初始化已经完成
     */
    private boolean isPrepared;

    /**
     * 标识当前fragment是否可见
     */
    private boolean isVisible;

    @ViewInject(R.id.ll)
    private LinearLayout ll;

    @ViewInject(R.id.lv_poor)
    ListView lv_poor;
    @ViewInject(R.id.rl)
    RelativeLayout rl;

    Context mContext;

    /**
     * 贫困户householderid或贫困村id
     */
    String houseid;
    /**
     * 标志位---如果为"pinkunhu"则为贫困户情况说明，如果为"pinkuncun"则为贫困村情况说明
     */
    String type_lingfushuoming;
    /**
     * 标志位---是否是第一次显示“暂无情况说明”，默认为true
     */
    boolean isFirstComeIn = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_poorhouse_familypeople, container, false);
        ViewUtils.inject(this, view);
        isPrepared = true;
        mContext = getActivity();
        lazyLoad();
        return view;
    }

    /**
     * 判断当前的fragment是否可见，如果可见就请求网络加载数据；否则，不请求网络加载数据
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
        }
    }

    /**
     * 如果没有初始化完成或当前fragment不可见，就不去请求网络加载数据；否则开始请求网络加载数据
     */
    private void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
//        if (isFirstComeIn) {
//            isFirstComeIn = false;
//        }
//        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    /**
     * 初始化数据，即通过Bundle获取传输过来的hourseid,然后给情况说明赋值
     */
    private void initData() {
        Bundle bundle = getArguments();
        if (!StringUtil.isEmpty(bundle)) {
//			@SuppressWarnings("unchecked")
            houseid = bundle.getString(Common.TITLE_KEY);
            type_lingfushuoming = bundle.getString("type_lingfushuoming");
            if ("pinkunhu".equals(type_lingfushuoming)) {
                RequestParams rp = new RequestParams();
                rp.addBodyParameter("householderid", houseid);
                MyApplication.getInstance().getHttpUtilsInstance().send(HttpRequest.HttpMethod.POST,
                        URLs.PoorHouse_LingFuShuoMing_List, rp,
                        new MyRequestCallBack(this, MyConstans.FIRST));
                System.out.println("贫困户请求服务器中另附说明的网址为：===" + URLs.PoorHouse_LingFuShuoMing_List + "&?householderid=" + houseid);
            } else if ("pinkuncun".equals(type_lingfushuoming)) {
                RequestParams rp = new RequestParams();
                rp.addBodyParameter("id", houseid);
                MyApplication.getInstance().getHttpUtilsInstance().send(HttpRequest.HttpMethod.POST,
                        URLs.PoorVillage_LingFuShuoMing_List, rp,
                        new MyRequestCallBack(this, MyConstans.FIRST));
                System.out.println("贫困村请求服务器中另附说明的网址为：===" + URLs.PoorVillage_LingFuShuoMing_List + "&?id=" + houseid);
            }
        }
    }

    /**
     * 另附说明的记录的集合
     */
    List<LingFuShuoMing> rows;
    LingFuShuoMingAdapter lingFuShuoMingAdapter;
    @Override
    public void onSuccessResult(String str, int flag) {
        super.onSuccessResult(str, flag);
        switch (flag) {
            case MyConstans.FIRST:
                System.out.println("贫困户或贫困村请求服务器中另附说明成功：===" + str);
                LingFuShuoMing_ResultList lingFuShuoMing_resultList = LingFuShuoMing_ResultList.parseToT(str, LingFuShuoMing_ResultList.class);
                if (null != lingFuShuoMing_resultList) {
                    rows = lingFuShuoMing_resultList.getRows();
                    if (null != rows && rows.size() > 0) {
                        lv_poor.setVisibility(View.VISIBLE);
                        rl.setVisibility(View.GONE);
                        lingFuShuoMingAdapter = new LingFuShuoMingAdapter(mContext, rows);
                        lv_poor.setAdapter(lingFuShuoMingAdapter);
                        lv_poor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                LingFuShuoMing lingFuShuoMing = (LingFuShuoMing) parent.getItemAtPosition(position);
                                Intent intent = new Intent(mContext, LingFuShuoMingDetailActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable(Common.BUNDEL_KEY, lingFuShuoMing);
                                intent.putExtra(Common.INTENT_KEY, bundle);
                                intent.putExtra("type_lingfushuoming", type_lingfushuoming);
                                intent.putExtra("houseid", houseid);
                                startActivity(intent);
                            }
                        });
                        lv_poor.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                SharedPreferences sp = mContext.getSharedPreferences("loginactivty", Context.MODE_APPEND);
                                String loginUserId=sp.getString("loginUserId", "");
                                if(loginUserId.equals(rows.get(position).getUserid())){
                                    //显示是否删除另附说明的对话框
                                    showDeleteDialog(position);
                                }else {
                                    ShowToast("无权限删除");
                                }
                                return true;
                            }
                        });
                    } else {
                        lv_poor.setVisibility(View.GONE);
                        rl.setVisibility(View.VISIBLE);
//                        ShowToast("暂无情况说明");
                    }
                }
                break;

            case MyConstans.SECOND:
                ShowToast("删除成功");
                //删除另附说明记录
                deletePhoto(deletePosition);
                break;
        }
    }

    /**
     * 删除另附说明记录
     * @param position
     */
    private void deletePhoto(int position) {
        rows.remove(position);
        lingFuShuoMingAdapter.notifyDataSetChanged();
        if(rows.size()<=0){
            lv_poor.setVisibility(View.GONE);
            rl.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFaileResult(String str, int flag) {
        super.onFaileResult(str, flag);
        switch (flag) {
            case MyConstans.FIRST:
                ShowToast(str);
                System.out.println("贫困户或贫困村请求服务器中另附说明成功：===" + str);
                break;

            case MyConstans.SECOND:
                ShowToast("删除失败");
                System.out.println("贫困户或贫困村删除另附说明失败：===" + str);
                break;
        }
    }

    int deletePosition;
    /**
     * 显示是否删除照片的对话框
     */
    private void showDeleteDialog(final int position) {
        final NormalDailog normalDailog=new NormalDailog(getActivity(),R.style.popup_dialog_style);
        normalDailog.show();
        normalDailog.setTitleText("删除提示");
        normalDailog.setContentText("确定要删除这条记录吗？");
        normalDailog.setOnClickLinener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.normal_dialog_done: //确定
                        //请求服务器删除另附说明记录
                        requestNetDeletePhoto(position);
                        deletePosition = position;
                        normalDailog.dismiss();
                        break;

                    case R.id.normal_dialog_cancel: //取消
                        normalDailog.dismiss();
                        break;
                }
            }
        });
    }

    /**
     * 请求服务器删除另附说明记录
     */
    private void requestNetDeletePhoto(int position) {
        if("pinkunhu".equals(type_lingfushuoming)){
            RequestParams rp = new RequestParams();
            rp.addBodyParameter("familyinfoid", rows.get(position).getFamilyinfoid());
            MyApplication.getInstance().getHttpUtilsInstance().send(HttpRequest.HttpMethod.POST,
                    URLs.POOR_HOUSE_QingKuangShuoMing_Delete, rp,
                    new MyRequestCallBack(this, MyConstans.SECOND));
            System.out.println("贫困户删除情况说明的网址为==="+URLs.POOR_HOUSE_QingKuangShuoMing_Delete+"&?familyinfoid="+rows.get(position).getFamilyinfoid());
        }else {
            RequestParams rp = new RequestParams();
            rp.addBodyParameter("villageinfoid", rows.get(position).getVillageinfoid());
            MyApplication.getInstance().getHttpUtilsInstance().send(HttpRequest.HttpMethod.POST,
                    URLs.POOR_Village_QingKuangShuoMing_Delete, rp,
                    new MyRequestCallBack(this, MyConstans.SECOND));
            System.out.println("贫困村删除情况说明的网址为==="+URLs.POOR_Village_QingKuangShuoMing_Delete+"&?villageinfoid="+rows.get(position).getFamilyinfoid());

        }
    }
}
