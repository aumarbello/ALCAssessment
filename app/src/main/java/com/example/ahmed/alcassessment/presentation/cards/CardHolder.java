package com.example.ahmed.alcassessment.presentation.cards;

import android.graphics.Color;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmed.alcassessment.R;
import com.example.ahmed.alcassessment.data.model.Card;

import java.util.Locale;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ahmed on 10/8/17.
 */

class CardHolder extends RecyclerView.ViewHolder {
    private CardsActivity activity;
    private Card card;

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
    ProgressBar cardIsSyncing;

    @BindArray(R.array.crytoCurrencies)
    String[] crytoSymbols;

    @BindArray(R.array.otherCurrencies)
    String[]  otherSymbols;

    CardHolder(View itemView, CardsActivity activity) {
        super(itemView);
        this.activity = activity;
        ButterKnife.bind(this, itemView);
    }

    void bindCard(Card card, int position){
        this.card = card;
        cryptCurrencySymbol.setText(getCryptoSymbol(card.getFrom()));
        otherCurrencySymbol.setText(getSymbol(card.getTo()));

        cryptCurrencyText.setText(card.getFrom());
        otherCurrencyText.setText(card.getTo());
        Log.d("Holder", "Current rate - " + card.getCurrentRate());
        if (card.getCurrentRate() == 0.0){
            cardIsSyncing.setVisibility(View.VISIBLE);
            currentRate.setVisibility(View.INVISIBLE);
        }else if (card.getCurrentRate() == 123){
            currentRate.setTextSize(10);
            currentRate.setText(R.string.faied_to_retrieve);
        }else {
            String rate = String.format(Locale.ENGLISH, "1 %s : %.2f %s",
                    getCryptoSymbol(card.getFrom()), card.getCurrentRate(),
                    getSymbol(card.getTo()));

            currentRate.setText(rate);
        }
        setListeners(position);
    }

   private void setListeners(int position){
       menu.setOnClickListener(menuView -> {
           PopupMenu popupMenu = new PopupMenu(activity, itemView);
           popupMenu.inflate(R.menu.item_menu);

           popupMenu.setOnMenuItemClickListener(item -> {
               switch (item.getItemId()){
                   case R.id.sync_rates:
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

        itemView.setOnClickListener(view -> {
            activity.openConversionDialog(card);
            Toast.makeText(activity, "Position of clicked item - " + position,
                    Toast.LENGTH_SHORT).show();
        });
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
            case "Indian Rupees":
                return otherSymbols[5];
            case "Israel Shekel":
                return otherSymbols[6];
            case "Laos Kip":
                return otherSymbols[7];
            case "Netherlands Antilles Guilder":
                return otherSymbols[8];
            case "Qatari Riyal":
                return otherSymbols[9];
            case "Russian Ruble":
                return otherSymbols[10];
            case "Brazilian Real":
                return otherSymbols[11];
            case "Switzerland Franc":
                return otherSymbols[12];
            case "Seychelles Rupee":
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
