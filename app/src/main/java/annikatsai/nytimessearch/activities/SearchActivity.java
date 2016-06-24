package annikatsai.nytimessearch.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import annikatsai.nytimessearch.Article;
import annikatsai.nytimessearch.ArticleAdapter;
import annikatsai.nytimessearch.EndlessRecyclerViewScrollListener;
import annikatsai.nytimessearch.ItemClickSupport;
import annikatsai.nytimessearch.R;
import annikatsai.nytimessearch.SearchFilters;
import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 50;
    ArrayList<Article> articles;
    ArticleAdapter rvAdapter;
    String searchQuery;
    String beginDate = "", sortFilter = "", newsDesk = "";
    SearchFilters searchFilters;
    Boolean filterUsed = false;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.gvResults) GridView gvResults;
    @BindView(R.id.custom_font) TextView toolbarTitle;
    @BindView(R.id.my_recycler_view) RecyclerView rvArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");


        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Kingthings_Calligraphica_2.ttf");

        toolbarTitle.setTypeface(font);

        setUpViews();

//        // Displaying Current Top Stories Before Search
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/topstories/v2/home.json";

        RequestParams params = new RequestParams();
        params.put("api-key", "3c92f112cd9f4553b556f691624b70af");
        params.put("page", 0);

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Debug", response.toString());
                JSONArray articleJsonResults = null;

                try {
                    articleJsonResults = response.getJSONArray("results");
                    for (int i = 0; i < articles.size(); i++) {
                        articles.remove(i);
                    }
                    rvAdapter.notifyItemRangeRemoved(0, articles.size());

                    articles.addAll(Article.fromJsonArray(articleJsonResults));
                    rvAdapter.notifyItemRangeInserted(0, articles.size());

                    Log.d("Debug", articles.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";

        RequestParams params = new RequestParams();
        params.put("api-key", "3c92f112cd9f4553b556f691624b70af");
        params.put("page", offset);
        params.put("q", searchQuery);

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Debug", response.toString());
                JSONArray articleJsonResults = null;

                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    //adapter.addAll(Article.fromJsonArray(articleJsonResults));

                    int curSize = rvAdapter.getItemCount();
                    articles.addAll(Article.fromJsonArray(articleJsonResults));
                    rvAdapter.notifyDataSetChanged();

                    Log.d("Debug", articles.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setUpViews() {
        gvResults = (GridView) findViewById(R.id.gvResults);
        articles = new ArrayList<>();
        searchFilters = new SearchFilters(beginDate, sortFilter, newsDesk);

        rvAdapter = new ArticleAdapter(this, articles);
        rvArticles.setAdapter(rvAdapter);

        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rvArticles.setLayoutManager(gridLayoutManager);

        rvArticles.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                customLoadMoreDataFromApi(page);
            }
        });

        ItemClickSupport.addTo(rvArticles).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent i = new Intent(getApplicationContext(), ArticleActivity.class);
                // get the article to display
                Article article = articles.get(position);
                // pass in that article into the intent
                i.putExtra("article", article);
                // launch the activity
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                searchQuery = query;
                onArticleSearch(query);
                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    public void onArticleSearch(String query) {

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";

        RequestParams params = new RequestParams();
        params.put("api-key", "3c92f112cd9f4553b556f691624b70af");
        params.put("page", 0);
        if (filterUsed == true) {
            if (!searchFilters.getNews_desk().isEmpty())
                params.put("fq", String.format("news_desk:(%s)", searchFilters.getNews_desk()));
            else
                params.put("fq", query);
            if (!searchFilters.getBegin_date().isEmpty())
                params.put("begin_date", searchFilters.getBegin_date());
            if (!searchFilters.getSort().isEmpty())
                params.put("sort", searchFilters.getSort());
        }
        params.put("q", query);


        client.get(url,params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Debug", response.toString());
                JSONArray articleJsonResults = null;

                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
//                    adapter.clear();
//                    adapter.addAll(Article.fromJsonArray(articleJsonResults));
//                    adapter.notifyDataSetChanged();
                    articles.clear();
                    //rvAdapter.notifyItemRangeRemoved(0, articles.size());
                    articles.addAll(Article.fromJsonArray(articleJsonResults));
                    //rvAdapter.notifyItemRangeInserted(0, articles.size());
                    rvAdapter.notifyDataSetChanged();

                    Log.d("Debug", articles.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        });
    }

//    @OnClick(R.id.action_filter)
    public void launchFilterView(MenuItem item) {
        Intent i = new Intent(SearchActivity.this, FilterActivity.class);
        i.putExtra("filter", Parcels.wrap(searchFilters));
        filterUsed = true;
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            searchFilters = Parcels.unwrap(data.getParcelableExtra("filter"));
            onArticleSearch(searchQuery);
        }
    }
}
