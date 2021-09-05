package com.foodtogo.user.cardutils;

import android.content.Context;

import com.foodtogo.user.R;

import java.util.ArrayList;
import java.util.List;


public class CreditCardPatterns implements CreditCardEditText.CreditCartEditTextInterface {

    public static final String VISA = "^4[0-9]{12}(?:[0-9]{3})?$";
    public static final String MASTERCARD = "^5[1-5][0-9]{14}$";
    public static final String AMERICAN_EXPRESS = "^3[47][0-9]{13}$";
    public static final String DISCOVER = "^6(?:011|5[0-9]{2})[0-9]{12}$";
    public static final String JCB = "^(?:2131|1800|35d{3})d{11}$";
    public static final String DINERS_CLUB = "^3(?:0[0-5]|[68][0-9])[0-9]{11}$";

    private Context mContext;

    public CreditCardPatterns(Context context) {
        mContext = context;
    }

    @Override
    public List<CreditCardEditText.CreditCard> mapOfRegexStringAndImageResourceForCreditCardEditText(CreditCardEditText creditCardEditText) {
        List<CreditCardEditText.CreditCard> listOfPatterns = new ArrayList<CreditCardEditText.CreditCard>();

        CreditCardEditText.CreditCard visa = new CreditCardEditText.CreditCard(VISA, mContext.getResources().getDrawable(R.drawable.visa), CreditCardTypeEnum.VISA.cartType);
        CreditCardEditText.CreditCard mastercard = new CreditCardEditText.CreditCard(MASTERCARD, mContext.getResources().getDrawable(R.drawable.mastercard), CreditCardTypeEnum.MASTER_CARD.cartType);
        CreditCardEditText.CreditCard amex = new CreditCardEditText.CreditCard(AMERICAN_EXPRESS, mContext.getResources().getDrawable(R.drawable.amex), CreditCardTypeEnum.AMERICAN_EXPRESS.cartType);
        CreditCardEditText.CreditCard discover = new CreditCardEditText.CreditCard(DISCOVER, mContext.getResources().getDrawable(R.drawable.ds), CreditCardTypeEnum.DISCOVER.cartType);
        CreditCardEditText.CreditCard jcb = new CreditCardEditText.CreditCard(JCB, mContext.getResources().getDrawable(R.drawable.jcb), CreditCardTypeEnum.JCB.cartType);
        CreditCardEditText.CreditCard dinersClub = new CreditCardEditText.CreditCard(DINERS_CLUB, mContext.getResources().getDrawable(R.drawable.dc), CreditCardTypeEnum.DINERS_CLUB.cartType);
        listOfPatterns.add(visa);
        listOfPatterns.add(mastercard);
        listOfPatterns.add(amex);
        listOfPatterns.add(discover);
        listOfPatterns.add(jcb);
        listOfPatterns.add(dinersClub);

        return listOfPatterns;
    }
}