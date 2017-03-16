package hieuxinhe.notificationappication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import connectWebservice.CheckServer;
import connectWebservice.TVSFunction;
import login.LoginActivity;
import object.BuilderManager;
import object.Message;

public class MessageActivity extends AppCompatActivity  {
   // BOOM MENU
    private BoomMenuButton boomMenuButton;
    private MessageAdapter adapter;
    private List<Message> albumList;
    private String TAG = MessageActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    //  private static String url = "http://api.androidhive.info/contacts/";
    @Override
    protected void onCreate(Bundle savedInstanceState)                                         {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Toast.makeText(getApplicationContext(),"HỘP TIN NHẮN ",Toast.LENGTH_LONG).show();
        // boom menu
        boomMenuButton = (BoomMenuButton)findViewById(R.id.bmb) ;
        assert boomMenuButton != null;
        boomMenuButton.setButtonEnum(ButtonEnum.Ham);
        boomMenuButton.setPiecePlaceEnum(PiecePlaceEnum.HAM_2);
        boomMenuButton.setButtonPlaceEnum(ButtonPlaceEnum.HAM_2);
          addBuilder1();
          addBuilder2();

        initCollapsingToolbar();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        albumList = new ArrayList<>();
        adapter = new MessageAdapter(this, albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        prepareAlbums();   // set data
        try {
            Glide.with(this).load(R.drawable.banner).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void initCollapsingToolbar()                                                       {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Xin chào :  " + getUsers() );
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);
        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setBackgroundColor(Color.GREEN);
                    collapsingToolbar.setCollapsedTitleTextColor(Color.BLACK);
                    collapsingToolbar.setTitle(getString(R.string.home_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle("");
                    isShow = false;
                }
            }
        });
    }
    private void addBuilder1()                                                                 {
        boomMenuButton.addBuilder(new  HamButton.Builder()
                .normalImageRes(BuilderManager.getImageResource())
                .normalTextRes(R.string.text_ham_button_text_normal)
                .subNormalTextRes(R.string.text_ham_button_sub_text_normal).listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        LogOut();
                        startActivity(new Intent(MessageActivity.this,LoginActivity.class));
                    }
                }));
    }
    private void addBuilder2()                                                                 {
        final TVSFunction func = new TVSFunction();
        boomMenuButton.addBuilder(new  HamButton.Builder()
                .normalImageRes(BuilderManager.getImageResource2())
                .normalTextRes(R.string.text_ham_button_text_normal_link_to_web)
                .subNormalTextRes(R.string.text_ham_button_sub_text_normal_link_to_web).listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                       String  url = "http://gianoibo.com/TinNhan.aspx?version="+
                               "android"+
                               "&username="+getUsers() +
                               "&password="+ func.MD5Cytography(getPasswords());
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                }));
    }
    /**
     */
    private void prepareAlbums()                                                               {
        new GetJsonMessage().execute();
        adapter.notifyDataSetChanged();
    }
    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration                 {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;
        GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp)                                                                 {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    /**
     * Async task class to get json by making HTTP call
     */
    private class GetJsonMessage extends AsyncTask<Void, Void, Void>                           {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MessageActivity.this);
            pDialog.setMessage("Đang tải dữ liệu ...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            CheckServer server = new CheckServer();
            String jsonStr = server.getJsonString(getUsers(),getPasswords());
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("Message");   //messages
                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String id = c.getString("Id");
                        String name = c.getString("Title");
                        String content = c.getString("SMS");   // content
                        String date = c.getString("DayCreate");    // date

                        Message m = new Message(id,name,content,date);
                        albumList.add(m);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Dữ liệu tải xuống không đúng định dạng : " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Không thể tải xuống dữ liệu !",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            adapter.notifyDataSetChanged();
        }

    }
    public  void LogOut()                                                                       {
        SharedPreferences sp = getSharedPreferences("LogIn", 0);
        SharedPreferences.Editor ed = sp.edit();
        ed.clear().apply();
    }
    public String getUsers()                                                                   {
        SharedPreferences introPref = getSharedPreferences("LogIn", 0);

        if (introPref.getString("username", null)== null)
        {
            Bundle b = getIntent().getExtras();
            if (b!= null)
            {
                Log.e("BUNDLE", b.getString("username"));
                return  b.getString("username");
            }
        }
         return introPref.getString("username", null);
    }
    public String getPasswords()                                                               {
        SharedPreferences introPref = getSharedPreferences("LogIn", 0);
        return introPref.getString("password", null);
    }
}
