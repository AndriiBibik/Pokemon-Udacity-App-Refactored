package udacity.pokemon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import udacity.pokemon.Pokemon;
import udacity.pokemon.R;

public class PokemonAdapter extends ArrayAdapter<Pokemon> {

    private Context context;
    private ArrayList<Pokemon> pokemonsList;

    public PokemonAdapter(Context context, ArrayList<Pokemon> pokemonsList) {
        super(context, R.layout.list_item, pokemonsList);
        this.context = context;
        this.pokemonsList = pokemonsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }
        ImageView imageView = convertView.findViewById(R.id.image);
        TextView name = convertView.findViewById(R.id.name);
        TextView id = convertView.findViewById(R.id.id);
        TextView candy = convertView.findViewById(R.id.candy);

        Pokemon pokemon = pokemonsList.get(position);

        Picasso.with(context).load(pokemon.getImage()).into(imageView);
        name.setText(pokemon.getName());
        id.setText("" + pokemon.getId());
        candy.setText(pokemon.getCandy());

        return convertView;
    }
}
