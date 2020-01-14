package chris.example.com.switchwithme;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chris on 2/28/2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>
{
    private List<AdvancedUser> users;
    private Context context;
    
    public UserAdapter(List<AdvancedUser> users)
    {
        this.users = users;
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, null);
        context = parent.getContext();
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position)
    {
        AdvancedUser user = users.get(position);
        if(user != null)
        {
            holder.tvHandle.setText(user.getInfo().get("handle"));
            holder.tvGender.setText(user.getInfo().get("gender"));
            holder.tvFriendCode.setText(user.getInfo().get("friendCode"));
        }
    }
    
    @Override
    public int getItemCount()
    {
        return users.size();
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView tvHandle;
        private final TextView tvGender;
        private final TextView tvFriendCode;
        
        public ViewHolder(final View itemView)
        {
            super(itemView);
            tvHandle = itemView.findViewById(R.id.tvHandle);
            tvGender = itemView.findViewById(R.id.tvGender);
            tvFriendCode = itemView.findViewById(R.id.tvFriendCode);
        }
    }
}
