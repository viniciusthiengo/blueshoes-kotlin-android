package thiengo.com.br.blueshoes.domain

class CreditCard(
    /*
     * number contém somente último conjunto de
     * números do cartão (4 ou 3 números).
     * */
    val number : String,
    val enterprise: String,
    val ownerFullName: String,
    val ownerRegNumber: String = "",
    val expiryMonth: Int = 0,
    val expiryYear: Int = 0,
    val securityNumber: String = ""
){
    fun getNumberAsHidden()
        = String.format("**** **** **** %s", number)

    fun getOwnerFullNameAsHidden() : String {
        val nameList = ownerFullName.split(" ")

        val firstName = nameList.first().substring(0, 2)
        val lastName = nameList.last()

        return String.format("%s... %s", firstName, lastName)
    }
}