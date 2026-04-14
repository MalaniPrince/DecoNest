package princetechlabs.deconest.ui.login

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.FragmentSignUpBinding
import princetechlabs.deconest.ui.utils.CustomDialog
import princetechlabs.deconest.ui.utils.PreferenceHelper

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.SignupButton.setOnClickListener {
            val name = binding.EnterName.text.toString().trim()
            val userEmail = binding.EnterEmail.text.toString().trim()
            val password = binding.EnterPassward.text.toString().trim()
            val confirmPassword = binding.EnterComPassword.text.toString().trim()

            if (name.isEmpty()) {
                CustomDialog.ShowToastMessage(requireContext(), "Please enter your name")
                return@setOnClickListener
            }

            if (userEmail.isEmpty()) {
                CustomDialog.ShowToastMessage(requireContext(), "Please enter your email")
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                CustomDialog.ShowToastMessage(requireContext(), "Please enter a valid email address")
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                CustomDialog.ShowToastMessage(requireContext(), "Please enter a password")
                return@setOnClickListener
            }

            if (password.length < 6) {
                CustomDialog.ShowToastMessage(requireContext(), "Password must be at least 6 characters")
                return@setOnClickListener
            }

            if (confirmPassword.isEmpty()) {
                CustomDialog.ShowToastMessage(requireContext(), "Please confirm your password")
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                CustomDialog.ShowToastMessage(requireContext(), "Passwords do not match")
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(userEmail, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Log.d("SignUpFragment", "createUserWithEmail:success")
                        PreferenceHelper.setName(requireContext(), name)
                        PreferenceHelper.setUserEmail(requireContext(), userEmail)
                        CustomDialog.ShowToastMessage(requireContext(), "Account created successfully!")
                        findNavController().navigate(R.id.LoginFragment)
                    } else {
                        Log.w("SignUpFragment", "createUserWithEmail:failure", task.exception)
                        val errorMsg = task.exception?.message ?: "Authentication failed."
                        CustomDialog.ShowToastMessage(requireContext(), errorMsg)
                    }
                }
        }

        binding.buttonLogin.setOnClickListener {
            findNavController().navigate(R.id.LoginFragment)
        }
    }
}
