package com.example.ahmed.alcassessment.presentation.cards;

import android.graphics.Color;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ahmed.alcassessment.R;
import com.example.ahmed.alcassessment.data.model.Card;

import java.util.Locale;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import ua.naiksoftware.threedotsprogress.ThreeDotsProgressView;

/**
 * Created by ahmed on 10/8/17.
 */

class CardHolder extends RecyclerView.ViewHolder {
    private CardsActivity activity;
    private Card card;
    private boolean isSyncing;

    @BindView(R.id.menu)
    ImageView menu;

    @BindView(R.id.cryptoCurrency)
    TextView cryptCurrencySymbol;

    @BindView(R.id.otherCurrency)
    TextView otherCurrencySymbol;

    @BindView(R.id.textCryptCurrency)
    TextView cryptCurrencyText;

    @BindView(R.id.textOtherCurrency)
    TextView otherCurrencyText;

    @BindView(R.id.currentRate)
    TextView currentRate;

    @BindView(R.id.rate_in_sync)
    ThreeDotsProgressView cardIsSyncing;

    @BindView(R.id.divider_two)
    LinearLayout dividerTwo;

    @BindArray(R.array.crytoCurrencies)
    String[] crytoSymbols;

    @BindArray(R.array.otherCurrencies)
    String[]  otherSymbols;

    CardHolder(View itemView, CardsActivity activity) {
        super(itemView);
        this.activity = activity;
        ButterKnife.bind(this, itemView);

        if (activity.prefs.getListType() == 1){
            cryptCurrencyText.setVisibility(View.GONE);
            otherCurrencyText.setVisibility(View.GONE);
            dividerTwo.setVisibility(View.GONE);
        }
    }

    void bindCard(Card card, int position){
        this.card = card;
        cryptCurrencySymbol.setText(getCryptoSymbol(card.getFrom()));
        otherCurrencySymbol.setText(getSymbol(card.getTo()));

        cryptCurrencyText.setText(card.getFrom());
        otherCurrencyText.setText(card.getTo());
        if (card.getCurrentRate() == 0.0){
            cardIsSyncing.setVisibility(View.GONE);
            currentRate.setText(R.string.faied_to_retrieve);
        }
        else if (card.getCurrentRate() == 0.0 && isSyncing){
            cardIsSyncing.setVisibility(View.VISIBLE);
            currentRate.setVisibility(View.INVISIBLE);
        }else if (card.getCurrentRate() == 1){
            cardIsSyncing.setVisibility(View.GONE);
            currentRate.setText(R.string.faied_to_retrieve);
        }else {
            String rate = String.format(Locale.ENGLISH, "1 %s : %.2f %s",
                    getCryptoSymbol(card.getFrom()), card.getCurrentRate(),
                    getSymbol(card.getTo()));
            cardIsSyncing.setVisibility(View.GONE);
            currentRate.setText(rate);
        }
        Log.d("Holder", "bind Card called on exchanged");
        setListeners(position);
    }

   private void setListeners(int position){
       menu.setOnClickListener(menuView -> {
           PopupMenu popupMenu = new PopupMenu(activity, itemView);
           popupMenu.inflate(R.menu.item_menu);

           popupMenu.setOnMenuItemClickListener(item -> {
               switch (item.getItemId()){
                   case R.id.sync_rates:
                       isSyncing = true;
                       currentRate.setVisibility(View.GONE);
                       cardIsSyncing.setVisibility(View.VISIBLE);
                       activity.syncCard(card, position);
                       return true;
                   case R.id.delete_card:
                       activity.deleteCard(card, position);
                       return true;
               }
               return false;
           });
           popupMenu.setOnDismissListener(dismissMenu ->
                   itemView.setBackgroundColor(Color.WHITE));

           popupMenu.show();
           itemView.setBackgroundColor(Color.LTGRAY);
       });

        itemView.setOnClickListener(view -> activity.openConversionDialog(card));
    }

    private String getCryptoSymbol(String currencyText){
        switch (currencyText){
            case "Ether":
                return crytoSymbols[1];
            default:
                return crytoSymbols[0];
        }
    }

    private String getSymbol(String currencyText){
        switch (currencyText){
            case "Naira":
                return otherSymbols[0];
            case "Dollar":
                return otherSymbols[1];
            case "Euro":
                return otherSymbols[2];
            case "British Pound":
                return otherSymbols[3];
            case "Chinese Yuan":
                return otherSymbols[4];
            case "Indian Rupee":
                return otherSymbols[5];
            case "Israel Shekel":
                return otherSymbols[6];
            case "Hong Kong Dollar":
                return otherSymbols[7];
            case "Philippine Peso":
                return otherSymbols[8];
            case "Qatar Riyal":
                return otherSymbols[9];
            case "Russian Ruble":
                return otherSymbols[10];
            case "Brazil Real":
                return otherSymbols[11];
            case "Switzerland Franc":
                return otherSymbols[12];
            case "Malaysian Ringgit":
                return otherSymbols[13];
            case "South African Rand":
                return otherSymbols[14];
            case "Sweden Krona":
                return otherSymbols[15];
            case "Taiwan New Dollar":
                return otherSymbols[16];
            case "Ukraine Hryvnia":
                return otherSymbols[17];
            case "Uruguay Peso":
                return otherSymbols[18];
            case "Turkish Lira":
                return otherSymbols[19];
        }
        return "";
    }
}
