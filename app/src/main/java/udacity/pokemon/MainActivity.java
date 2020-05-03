/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package udacity.pokemon;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URL;
import java.util.ArrayList;

import udacity.pokemon.adapter.PokemonAdapter;

public class MainActivity extends AppCompatActivity {

    private String LOG_TAG = MainActivity.class.getSimpleName();
    private ListView list_view;

    public static final String JSON_ARRAY_POKEMONS = "pokemon";
    public static final String JSON_PRIMITIVE_NAME = "name";
    public static final String JSON_PRIMITIVE_ID = "id";
    public static final String JSON_PRIMITIVE_CANDY = "candy";
    public static final String JSON_PRIMITIVE_IMAGE = "img";

    // list of pokemons
    ArrayList<Pokemon> pokemonsList = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list_view = findViewById(R.id.list);

        new GetPokemon().execute();
    }

    private class GetPokemon extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {

            String jsonString = "";
            URL url = HttpHandler.getUrl();

            jsonString = HttpHandler.makeHttpRequest(url);

            if (jsonString != null && !jsonString.isEmpty()) {
                try {
                    JSONObject root = new JSONObject(jsonString);

                    JSONArray pokemons = root.getJSONArray(JSON_ARRAY_POKEMONS);


                    // looping through all Pokemons
                    for (int i = 0; i < pokemons.length(); i++) {

                        JSONObject pokemonObject = pokemons.getJSONObject(i);

                        String name = pokemonObject.getString(JSON_PRIMITIVE_NAME);
                        int id = pokemonObject.getInt(JSON_PRIMITIVE_ID);
                        String candy = pokemonObject.getString(JSON_PRIMITIVE_CANDY);
                        String image = pokemonObject.getString(JSON_PRIMITIVE_IMAGE);
                        image = image.replace("http:", "https:");

                        Pokemon pokemon = new Pokemon(name, id, candy, image);

                        // adding pokemon to the list
                        pokemonsList.add(pokemon);

                    }
                } catch (final JSONException e) {
                    Log.e(LOG_TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(LOG_TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server.",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            ArrayAdapter<Pokemon> adapter
                    = new PokemonAdapter(MainActivity.this, pokemonsList);

            ListView listView = findViewById(R.id.list);
            listView.setAdapter(adapter);
        }
    }
}
