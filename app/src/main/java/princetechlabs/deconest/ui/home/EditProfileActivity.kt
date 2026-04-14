package princetechlabs.deconest.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.ActivityEditProfileBinding
import princetechlabs.deconest.ui.utils.PreferenceHelper

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        binding.btnEditProfileBack.setOnClickListener { finish() }

        val name = PreferenceHelper.getName(this) ?: ""
        val phone = PreferenceHelper.getPhone(this) ?: ""
        val email = PreferenceHelper.getUserEmail(this) ?: ""

        binding.etEditName.setText(name)
        binding.etEditPhone.setText(phone)
        binding.tvEditEmail.text = email

        binding.btnSaveProfile.setOnClickListener {
            val newName = binding.etEditName.text.toString().trim()
            val newPhone = binding.etEditPhone.text.toString().trim()

            if (newName.isEmpty()) {
                Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            PreferenceHelper.setName(this, newName)
            PreferenceHelper.setPhone(this, newPhone)
            Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
