package thiengo.com.br.blueshoes.domain

import thiengo.com.br.blueshoes.view.FormActivity

class AccountSettingItem(
    val label: String,
    val description: String,
    val activityClass: Class<out FormActivity>
)