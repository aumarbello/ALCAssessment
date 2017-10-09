package com.example.ahmed.alcassessment.presentation.cards;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.ahmed.alcassessment.R;
import com.example.ahmed.alcassessment.data.model.Card;
import com.example.ahmed.alcassessment.presentation.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ahmed on 10/6/17.
 */

public class CardsActivity extends BaseActivity {
    @Inject
    CardsPresenter presenter;

    @BindView(R.id.card_list)
    RecyclerView cardList;

    @BindView(R.id.swipe_to_re_sync)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.add_card)
    FloatingActionButton addCard;

    private CardAdapter adapter;
    private Unbinder unbinder;
    private List<Card> cards;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        getComponent().inject(this);
        presenter.AttachView(this);
        setContentView(R.layout.card_list);

        cards = presenter.getAllCards();

        unbinder = ButterKnife.bind(this);

        adapter = new CardAdapter(cards, this);

        cardList.setLayoutManager(new GridLayoutManager(this, 2));
        cardList.setAdapter(adapter);

        addCard.setOnClickListener(view -> {
            Toast.makeText(this, "Adding", Toast.LENGTH_SHORT).show();
        });
    }

    public void syncCard(Card card) {
        Snackbar.make(refreshLayout, "Syncing", Snackbar.LENGTH_SHORT);
    }

    public void deleteCard(Card card) {
        Snackbar.make(cardList, "Deleting", Snackbar.LENGTH_SHORT);
//        cards.remove(card);
    }

    public void updateAfterDelete() {
        Snackbar.make(cardList, "Updating", Snackbar.LENGTH_SHORT);
    }

    public void openConversionDialog(Card card) {
        Snackbar.make(cardList, "Opening", Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        unbinder.unbind();
    }
}
