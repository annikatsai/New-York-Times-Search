package annikatsai.nytimessearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        @BindView(R.id.ivImage) ImageView imageView;
        @BindView(R.id.tvTitle) TextView tvTitle;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View view) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(view);

            ButterKnife.bind(this, view);
//            nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
//            messageButton = (Button) ite
// mView.findViewById(R.id.message_button);
        }
    }

    // Store a member variable for the contacts
    private List<Article> mArticles;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public ArticleAdapter(Context context, List<Article> articles) {
        mArticles = articles;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View convertView = inflater.inflate(R.layout.item_article_result, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(convertView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ArticleAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Article article = mArticles.get(position);

        // Set item views based on your views and data model
//        TextView textView = viewHolder.nameTextView;
//        textView.setText(article.getName());
//        Button button = viewHolder.messageButton;
//        button.setText("Message");

        viewHolder.imageView.setImageResource(0);
        viewHolder.tvTitle.setText(article.getHeadline());
        String thumbnail = article.getThumbNail();
        if(!TextUtils.isEmpty(thumbnail)) {
            Glide.with(getContext()).load(thumbnail).into(viewHolder.imageView);
        } else {
            Glide.with(getContext()).load(R.drawable.ic_newspaper).into(viewHolder.imageView);

        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mArticles.size();
    }
}
