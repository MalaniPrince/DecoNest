package princetechlabs.deconest.ui.utils

import princetechlabs.deconest.ui.data.AddressData

object AddressRepository {
    val addresses = mutableListOf<AddressData>()

    fun addAddress(address: AddressData) {
        addresses.add(address)
    }

    fun removeAddress(id: String) {
        addresses.removeAll { it.id == id }
    }
}
