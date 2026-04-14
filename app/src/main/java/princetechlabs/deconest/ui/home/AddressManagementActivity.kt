package princetechlabs.deconest.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.ActivityAddressManagementBinding
import princetechlabs.deconest.databinding.DialogAddAddressBinding
import princetechlabs.deconest.ui.adpter.AddressAdapter
import princetechlabs.deconest.ui.data.AddressData
import princetechlabs.deconest.ui.utils.AddressRepository

class AddressManagementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddressManagementBinding
    private lateinit var adapter: AddressAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        setupRecyclerView()

        binding.btnBackAddress.setOnClickListener { finish() }

        binding.btnAddAddress.setOnClickListener {
            showAddAddressDialog()
        }
    }

    private fun showAddAddressDialog() {
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val dialogBinding = DialogAddAddressBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.btnSaveAddress.setOnClickListener {
            val name = dialogBinding.etName.text.toString().trim()
            val phone = dialogBinding.etPhone.text.toString().trim()
            val address = dialogBinding.etAddress.text.toString().trim()
            val city = dialogBinding.etCity.text.toString().trim()
            val pincode = dialogBinding.etPincode.text.toString().trim()

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || city.isEmpty() || pincode.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val maxId = AddressRepository.addresses.mapNotNull { it.id.toIntOrNull() }.maxOfOrNull { it } ?: 0
            val newId = (maxId + 1).toString()
            val newAddress = AddressData(newId, name, phone, "$address, $city - $pincode")
            AddressRepository.addAddress(newAddress)
            
            adapter.updateList(AddressRepository.addresses.toMutableList())
            Toast.makeText(this, "Address added successfully", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun setupRecyclerView() {
        adapter = AddressAdapter(AddressRepository.addresses.toMutableList()) { address, position ->
            AddressRepository.removeAddress(address.id)
            adapter.removeAddress(position)
            Toast.makeText(this, "Address deleted", Toast.LENGTH_SHORT).show()
        }
        binding.rvAddresses.layoutManager = LinearLayoutManager(this)
        binding.rvAddresses.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        adapter.updateList(AddressRepository.addresses.toMutableList())
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
