package thiengo.com.br.blueshoes.data

import thiengo.com.br.blueshoes.domain.CreditCard

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