package thiengo.com.br.blueshoes.data

import thiengo.com.br.blueshoes.domain.DeliveryAddress

class DeliveryAddressesDataBase {

    companion object{

        fun getItems()
            = mutableListOf(
                DeliveryAddress(
                    "Rua das Oliveiras",
                    1366,
                    "Condomínio Aldeias",
                    "29154-630",
                    "Colina de Laranjeiras",
                    "Serra",
                    7
                ),
                DeliveryAddress(
                    "Av. Jayme Clayton",
                    856,
                    "Alphaville",
                    "22598-611",
                    "Limeira",
                    "Tataupé",
                    24
                ),
                DeliveryAddress(
                    "Rua Almeida Presidente",
                    2563,
                    "Happy Days",
                    "25668-178",
                    "Limeira",
                    "Sobral",
                    5
                ),
                DeliveryAddress(
                    "Rua das Emas",
                    58,
                    "Ao lado do Hospital Jorge Santos",
                    "23665-558",
                    "Setor Segundo",
                    "Itajaí",
                    23
                )
            )
    }
}