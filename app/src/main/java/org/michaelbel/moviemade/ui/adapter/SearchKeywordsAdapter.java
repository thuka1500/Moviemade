package org.michaelbel.moviemade.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.Keyword;
import org.michaelbel.moviemade.ui.view.cell.TextCell;

import java.util.List;

public class SearchKeywordsAdapter extends RecyclerView.Adapter {

    private List<TmdbObject> searches;

    public SearchKeywordsAdapter(List<TmdbObject> searches) {
        this.searches = searches;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        return new Holder(new TextCell(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Keyword keyword = (Keyword) searches.get(position);

        TextCell cell = (TextCell) holder.itemView;
        cell.setText(keyword.name);
        cell.setDivider(true);
    }

    @Override
    public int getItemCount() {
        return searches != null ? searches.size() : 0;
    }

    public List<TmdbObject> getKeywords() {
        return searches;
    }
}