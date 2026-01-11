package princetechlabs.deconest.ui.login

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
import princetechlabs.deconest.databinding.FragmentSignUpBinding
import princetechlabs.deconest.ui.utils.CustomDialog

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
            val userEmail = binding.EnterEmail.text.toString()
            val password = binding.EnterComPassword.text.toString()

            if (userEmail.isNotEmpty() && password.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(userEmail, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            Log.d("SignUpFragment", "createUserWithEmail:success")
                            findNavController().navigate(R.id.LoginFragment)
                        } else {
                            Log.w("SignUpFragment", "createUserWithEmail:failure", task.exception)
                            CustomDialog.ShowToastMessage(requireContext(),"Authentication failed.")
                        }
                    }
            } else {
                CustomDialog.ShowToastMessage(requireContext(),"Please enter email and password")
            }
        }

        binding.buttonLogin.setOnClickListener {
            findNavController().navigate(R.id.LoginFragment)
        }
    }
}
