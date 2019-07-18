package thiengo.com.br.blueshoes.data

import android.content.Context
import thiengo.com.br.blueshoes.R
import thiengo.com.br.blueshoes.domain.AccountSettingItem
import thiengo.com.br.blueshoes.domain.CreditCard
import thiengo.com.br.blueshoes.view.ConfigProfileActivity
import thiengo.com.br.blueshoes.view.config.connectiondata.ConfigConnectionDataActivity
import thiengo.com.br.blueshoes.view.config.creditcard.ConfigCreditCardsActivity

class CreditCardsDataBase {

    companion object{

        fun getItems()
            = mutableListOf(
                CreditCard(
                    "6502",
                    "Visa",
                    "Tony Stark"
                ),
                CreditCard(
                    "9270",
                    "Mastercard",
                    "Scarlett Johansson"
                ),
                CreditCard(
                    "661",
                    "American Express",
                    "Margot Robbie"
                ),
                CreditCard(
                    "8738",
                    "Visa",
                    "Vivian Hernandez"
                ),
                CreditCard(
                    "9011",
                    "Visa",
                    "Andrew Jackson"
                )
            )
    }
}