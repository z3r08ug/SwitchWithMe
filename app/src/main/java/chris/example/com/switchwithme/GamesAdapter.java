package chris.example.com.switchwithme;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by chris on 2/28/2018.
 */

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder>
{
    private List<Game> games, savedGames;
    private List<Game> selectedGames = new ArrayList<>();
    private List<Integer> hightlight = new ArrayList<>();
    private Context context;
    private int itemCounter;
    
    public GamesAdapter(List<Game> games, List<Game> savedGames)
    {
        this.games = games;
        this.savedGames = savedGames;
        
        itemCounter = 0;
        selectedGames.clear();
        
        for (Game g : savedGames)
        {
            selectedGames.add(g);
            int i = games.indexOf(g);
            hightlight.add(i);
        }
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
    public void onBindViewHolder(GamesAdapter.ViewHolder holder, int position)
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
    
    public List<Game> getSelectedGames()
    {
        return selectedGames;
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView tvGameTitle;
        private final ImageView ivGame;
        private boolean selected = false;
        
        public ViewHolder(final View itemView)
        {
            super(itemView);
            tvGameTitle = itemView.findViewById(R.id.tvGameTitle);
            ivGame = itemView.findViewById(R.id.ivGame);
            
            if (hightlight.size() != 0)
            {
                for (Integer i : hightlight)
                {
                    if (itemCounter == i)
                    {
                        itemView.setBackgroundColor(context.getColor(R.color.colorPrimary));
                        selected = true;
                        break;
                    }
                    else
                    {
                        itemView.setBackgroundColor(context.getColor(R.color.gray));
                        selected = false;
                    }
                }
            }
            else
            {
                itemView.setBackgroundColor(context.getColor(R.color.gray));
            }
            
            itemView.setOnClickListener(v ->
            {
                if (!selected)
                {
                    selected = true;
                    selectedGames.add(games.get(getAdapterPosition()));
                    itemView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                }
                else
                {
                    selected = false;
                    selectedGames.remove(games.get(getAdapterPosition()));
                    itemView.setBackgroundColor(context.getResources().getColor(R.color.gray));
                }
            });
            
            itemCounter++;
        }
    }
}
