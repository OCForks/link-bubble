package com.linkbubble.ui;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.linkbubble.R;

import java.util.ArrayList;
import java.util.List;


public class SearchURLCustomAdapter extends ArrayAdapter<SearchURLSuggestions> {

    public static final String CONSTRAINT_TEXT_COLOR = "'#A349A4'";

    private static final String SEARCH_FOR_PREPEND = "Search for ";
    private static final String SEARCH_WITH = " with ";
    private static final String SEARCH_ON = " on ";
    private static final String SEARCH_DUCK_DUCK_GO = "Duck Duck Go";
    private static final String SEARCH_GOOGLE = "Google";
    private static final String SEARCH_YAHOO = "Yahoo";
    private static final String SEARCH_AMAZON = "Amazon";

    private LayoutInflater layoutInflater;
    List<SearchURLSuggestions> mSuggestions;
    private int viewResourceId;

    private Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((SearchURLSuggestions)resultValue).Name;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() != 0) {
                ArrayList<SearchURLSuggestions> suggestions = new ArrayList<SearchURLSuggestions>();
                for (SearchURLSuggestions suggestion : mSuggestions) {
                    // Note: change the "startsWith" to "contains" if you only want starting matches
                    if (suggestion.Name.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(suggestion);
                    }
                }


                //for search engines
                SearchURLSuggestions searchSuggestion1 = new SearchURLSuggestions();
                SearchURLSuggestions searchSuggestion2 = new SearchURLSuggestions();
                SearchURLSuggestions searchSuggestion3 = new SearchURLSuggestions();
                SearchURLSuggestions searchSuggestion4 = new SearchURLSuggestions();
                searchSuggestion1.Value = SEARCH_FOR_PREPEND + "\"" + "<font color=" + CONSTRAINT_TEXT_COLOR + ">" + constraint +
                        "</font>" + "\"" + SEARCH_WITH + SEARCH_DUCK_DUCK_GO;
                searchSuggestion1.Name = constraint.toString();
                searchSuggestion1.EngineToUse = SearchURLSuggestions.SearchEngine.DUCKDUCKGO;
                searchSuggestion2.Value = SEARCH_FOR_PREPEND + "\"" + "<font color=" + CONSTRAINT_TEXT_COLOR + ">" + constraint +
                        "</font>" + "\"" + SEARCH_WITH + SEARCH_GOOGLE;
                searchSuggestion2.Name = constraint.toString();
                searchSuggestion2.EngineToUse = SearchURLSuggestions.SearchEngine.GOOGLE;
                searchSuggestion3.Value = SEARCH_FOR_PREPEND + "\"" + "<font color=" + CONSTRAINT_TEXT_COLOR + ">" + constraint +
                        "</font>" + "\"" + SEARCH_WITH + SEARCH_YAHOO;
                searchSuggestion3.Name = constraint.toString();
                searchSuggestion3.EngineToUse = SearchURLSuggestions.SearchEngine.YAHOO;
                searchSuggestion4.Value = SEARCH_FOR_PREPEND + "\"" + "<font color=" + CONSTRAINT_TEXT_COLOR + ">" + constraint +
                        "</font>" + "\"" + SEARCH_ON + SEARCH_AMAZON;
                searchSuggestion4.Name = constraint.toString();
                searchSuggestion4.EngineToUse = SearchURLSuggestions.SearchEngine.AMAZON;

                suggestions.add(searchSuggestion1);
                suggestions.add(searchSuggestion2);
                suggestions.add(searchSuggestion3);
                suggestions.add(searchSuggestion4);
                //
                results.values = suggestions;
                results.count = suggestions.size();
            }
            else {
                results.values = mSuggestions;
                results.count = mSuggestions.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
            clear();
            if (results != null && results.count > 0) {
                // we have filtered results
                addAll((ArrayList<SearchURLSuggestions>) results.values);
            }
            notifyDataSetChanged();
        }
    };

    public SearchURLCustomAdapter(Context context, int textViewResourceId, List<SearchURLSuggestions> suggestions) {
        super(context, textViewResourceId, suggestions);
        // copy all the customers into a master list
        mSuggestions = new ArrayList<SearchURLSuggestions>(suggestions.size());
        mSuggestions.addAll(suggestions);
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewResourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        TextView name = (TextView) view;
        SearchURLSuggestions suggestion = getItem(position);
        name.setText(Html.fromHtml(suggestion.Value));

        return view;
    }

    //@Override
    public Filter getFilter() {
        return mFilter;
    }
}
