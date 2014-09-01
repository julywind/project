package com.special.BuidingSite;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.special.BuidingSite.base.BaseFragment;
import com.special.BuidingSite.lib.PullToRefreshBase;
import com.special.BuidingSite.net.HttpStatusException;
import com.special.BuidingSite.net.HttpUtil;
import com.special.BuidingSite.po.Alarm;
import com.special.BuidingSite.util.DateUtil;
import com.special.BuidingSite.util.JSONUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * User: special
 * Date: 13-12-22
 * Time: 下午3:26
 * Mail: specialcyci@gmail.com
 */
public class AlarmListFragment extends BaseFragment {

    private View parentView;
    private ListView mPullToRefreshListView;

    private Handler listViewRemoveHandler = new Handler() {
        public void handleMessage(Message message) {
            int position = message.what;
            // msg.what means index to delete

            if(position==-2)
            {
                startActivity(new Intent(getActivity(),Login.class));
                getActivity().finish();
                return;
            }
            if (position >= 0) {
                calendarList.remove(position);
            }
            mAdapter.notifyDataSetChanged();
            mPullToRefreshListView.invalidate();

            // Call onRefreshComplete when the list has been refreshed.
            //mPullToRefreshListView.onRefreshComplete();
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //super.onCreateView(inflater)
        instance = inflater.getContext();
        parentView = inflater.inflate(R.layout.calendar, container, false);
        mPullToRefreshListView = (ListView) parentView.findViewById(R.id.listView);
        initView();
        LoadData();
        return parentView;
    }

    private void LoadData()
    {
        showWaitingDialog(instance);
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                String url1 = "/alarm/list.json";
                try {
                    String response = HttpUtil.submitPost(getActivity(), url1, new HashMap<String, String>()).toString();
                    System.out.println("response:"+response);
                    onResult(response, PullToRefreshBase.MODE_PULL_UP_TO_REFRESH);
                } catch (HttpStatusException e) {
                    sendMessage(e.getMessage() + " status:" + e.getStatus());
                } catch (IOException e) {
                    if(e instanceof ConnectException)
                    {
                        sendMessage("连接失败,请检查网络是否通畅");
                    }else {
                        e.printStackTrace();
                    }
                } catch (net.sf.json.JSONException e)
                {
                    sendMessage("返回数据格式异常，非Json格式");
                } finally{
                    closeWaitingDialog();
                }
                /*TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        sendBroadcast(new Intent(Login.PROCESS_OVER));
                    }
                };
                new Timer().schedule(task, 50);*/
            }
        }).start();
    }
    private void onResult(String response, int load_type) {

        JSONObject obj = null;
        Boolean addToHead = (PullToRefreshBase.MODE_PULL_DOWN_TO_REFRESH == load_type);
        if (response.trim().length() > 0) {
            try {
                obj = JSONObject.fromObject(response);
                if (obj.containsKey("data")) {
                    JSONArray list = obj.getJSONArray("data");
                    if (list == null || list.isEmpty()) {
                        sendMessage("已经没有数据啦！");
                    } else {
                        if (addToHead == true) {
                            for (int idx = list.size() - 1; idx >= 0; idx--) {
                                JSONObject temp = list.getJSONObject(idx);
                                //data.addFirst(temp);
                                calendarList.addFirst((Alarm)JSONUtil.toBean(temp,Alarm.class));
                            }
                        } else {
                            for (int idx = 0; idx < list.size(); idx++) {
                                JSONObject temp = list.getJSONObject(idx);
                                //data.add(temp);
                                calendarList.add((Alarm)JSONUtil.toBean(temp,Alarm.class));
                            }
                        }
                    }
                } else if (obj.containsKey("errorMessage")
                        && obj.containsKey("code")) {
                    sendMessage(obj.getString("errorMessage"));
                    if(obj.getInt("code")==HttpUtil.ERROR_UNLOGIN)
                    {
                        listViewRemoveHandler.sendEmptyMessage(-2);
                    }
                }else if(obj.containsKey("data")&&obj.getString("data")!=null)
                {
                    sendMessage(obj.getString("errorMessage"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            sendMessage("已经没有数据啦！");
        }

        listViewRemoveHandler.sendEmptyMessage(-1);
    }
    AlarmListAdapter mAdapter = null;
    private void initView(){
        /*SimpleAdapter mSimpleAdapter = new SimpleAdapter(this,listItem,//需要绑定的数据
                R.layout.item,//每一行的布局//动态数组中的数据源的键对应到定义布局的View中new String[] {"ItemImage"
                ,"ItemTitle", "ItemText"},
    newint[] {R.id.ItemImage,R.id.ItemTitle,R.id.ItemText}
    );*/



        mAdapter=new AlarmListAdapter(
                getActivity().getApplicationContext(),
                R.layout.alarm_list_item,
                getCalendarData());
        mPullToRefreshListView.setAdapter(mAdapter);
        mPullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String addr = HttpUtil.finalUrl(getActivity(), "../" + mAdapter.getItem(i).getFileName());
                Intent intent = new Intent();
                intent.setData(Uri.parse(addr));
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent); //启动浏览器
                //Toast.makeText(getActivity(), "Clicked item!", Toast.LENGTH_LONG).show();
            }
        });
    }

    LinkedList<Alarm> calendarList = new LinkedList<Alarm>();
    private LinkedList<Alarm> getCalendarData(){
        return calendarList;
    }

    public class AlarmListAdapter extends ArrayAdapter<Alarm> {
        private int resourceId;
        public AlarmListAdapter(Context context, int textViewResourceId,List<Alarm> objects) {
            super(context, textViewResourceId, objects);
            this.resourceId = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            Alarm alarm = getItem(position);
            LinearLayout alarmListItem = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(resourceId, alarmListItem, true);
            TextView camera = (TextView)alarmListItem.findViewById(R.id.camera);
            TextView filePath = (TextView)alarmListItem.findViewById(R.id.file_path);
            TextView genDate = (TextView)alarmListItem.findViewById(R.id.gen_date);
            camera.setText(alarm.getUserName());
            filePath.setText(alarm.getFileName());
            genDate.setText(DateUtil.getLongToString(alarm.getGenDate()));
            return alarmListItem;
        }
    }
}
