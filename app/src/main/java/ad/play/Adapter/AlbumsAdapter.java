package ad.play.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ad.play.Activity.PlayerActivity;
import ad.play.R;
import ad.play.REST.Models.songs;

/**
 * Created by ADITYA on 16-12-2017.
 */
public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder> implements Filterable {


    private ArrayList<songs> heroList;
    private ArrayList<songs> filteredList;
    private Context context;

    private static int currentPosition = 0;

    public AlbumsAdapter(ArrayList<songs> heroList, Context context) {
        this.heroList = heroList;
        this.filteredList = heroList;
        this.context = context;
    }

    @Override
    public AlbumsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row, parent, false);
        return new AlbumsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AlbumsViewHolder holder, final int position) {
        final songs song = filteredList.get(position);
        holder.textViewName.setText(song.getSong());
        holder.textViewArtist.setText(song.getArtists());

        Picasso.with(context)
                .load(song.getCover_image())
                .into(holder.textViewCover);
        holder.textViewCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPosition = position;
                Intent intent = new Intent(view.getContext(), PlayerActivity.class);
                intent.putExtra("URL", heroList.get(currentPosition).getUrl());
                intent.putExtra("Cover", heroList.get(currentPosition).getCover_image());
                intent.putExtra("SONG", heroList.get(currentPosition).getSong());
                intent.putExtra("ARTIST", heroList.get(currentPosition).getArtists());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected Filter.FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                if (charString.isEmpty()) {

                    filteredList = heroList;
                } else {

                    ArrayList<songs> mfilteredList = new ArrayList<>();
                    for (int i = 0; i < heroList.size(); i++) {

                        if (heroList.get(i).getSong().toLowerCase().startsWith(charString.toLowerCase())) {
                            mfilteredList.add(heroList.get(i));
                        }
                    }
                    filteredList = mfilteredList;
                }
                FilterResults mfilterResults = new FilterResults();
                mfilterResults.values = filteredList;
                return mfilterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults mfilterResults) {
                filteredList = (ArrayList<songs>) mfilterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class AlbumsViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewArtist;
        ImageView textViewCover;

        AlbumsViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.song_title);
            textViewArtist = (TextView) itemView.findViewById(R.id.artist);
            textViewCover = (ImageView) itemView.findViewById(R.id.thumbnail);


        }
    }


    @Override
    public int getItemCount() {
        return filteredList.size();

    }

}