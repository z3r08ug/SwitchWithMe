package chris.example.com.switchwithme;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by chris on 2/28/2018.
 */

public class MyGamesAdapter extends RecyclerView.Adapter<MyGamesAdapter.ViewHolder>
{
    private List<Game> games;
    private Context context;
    
    public MyGamesAdapter(List<Game> games)
    {
        this.games = games;
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, null);
        context = parent.getContext();
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(MyGamesAdapter.ViewHolder holder, int position)
    {
        Game game = games.get(position);
        if(game != null)
        {
            holder.tvGameTitle.setText(game.getName());
            Picasso.get().load(game.pic).placeholder(R.drawable.noimage).into(holder.ivGame);
        }
    }
    
    @Override
    public int getItemCount()
    {
        return games.size();
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView tvGameTitle;
        private final ImageView ivGame;
        
        public ViewHolder(final View itemView)
        {
            super(itemView);
            tvGameTitle = itemView.findViewById(R.id.tvGameTitle);
            ivGame = itemView.findViewById(R.id.ivGame);
        }
    }
}
