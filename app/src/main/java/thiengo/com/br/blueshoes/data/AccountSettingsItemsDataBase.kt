package thiengo.com.br.blueshoes.data

import android.content.Context
import thiengo.com.br.blueshoes.R
import thiengo.com.br.blueshoes.domain.AccountSettingItem
import thiengo.com.br.blueshoes.view.ConfigProfileActivity
import thiengo.com.br.blueshoes.view.config.connectiondata.ConfigConnectionDataActivity
import thiengo.com.br.blueshoes.view.config.creditcard.ConfigCreditCardsActivity
import thiengo.com.br.blueshoes.view.config.deliveryaddress.ConfigDeliveryAddressesActivity

class AccountSettingsItemsDataBase {

    companion object{

        fun getItems( context: Context )
            = listOf(
                AccountSettingItem(
                    context.getString( R.string.setting_item_profile ),
                    context.getString( R.string.setting_item_profile_desc ),
                    ConfigProfileActivity::class.java
                ),
                AccountSettingItem(
                    context.getString( R.string.setting_item_login ),
                    context.getString( R.string.setting_item_login_desc ),
                    ConfigConnectionDataActivity::class.java
                ),
                AccountSettingItem(
                    context.getString( R.string.setting_item_address ),
                    context.getString( R.string.setting_item_address_desc ),
                    ConfigDeliveryAddressesActivity::class.java
                ),
                AccountSettingItem(
                    context.getString( R.string.setting_item_credit_cards ),
                    context.getString( R.string.setting_item_credit_cards_desc ),
                    ConfigCreditCardsActivity::class.java
                )
            )
    }
}