package princetechlabs.deconest.ui.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.FragmentLoginBinding
import princetechlabs.deconest.ui.home.HomeMainActivity
import princetechlabs.deconest.ui.utils.CustomDialog
import princetechlabs.deconest.ui.utils.PreferenceHelper

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.LoginButton.setOnClickListener {
            val userEmail = binding.InputEmail.text.toString()
            val password = binding.InputPassward.text.toString()

            if (userEmail.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(userEmail, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "signInWithEmail:success")
                            PreferenceHelper.setUserEmail(requireContext(), userEmail)
                            startActivity(Intent(requireContext(), HomeMainActivity::class.java))
                            requireActivity().finish()
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            CustomDialog.ShowToastMessage(requireContext(),"Authetication Failed")
                        }
                    }
            } else {
                CustomDialog.ShowToastMessage(
                    requireContext(),
                    "Enter valid email and password"
                )
            }
        }

        binding.CreateAccount.setOnClickListener {
            findNavController().navigate(R.id.SignUpFragment)
        }
    }
}
