package princeinfotech.deconest.ui.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import princeinfotech.deconest.R
import princeinfotech.deconest.databinding.FragmentLoginBinding
import princeinfotech.deconest.databinding.FragmentSignUpBinding
import princeinfotech.deconest.ui.home.HomeMainActivity

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSignUpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.SignupButton.setOnClickListener {
                findNavController().navigate(R.id.LoginFragment)


        }
       binding.buttonLogin.setOnClickListener{
           findNavController().navigate(R.id.LoginFragment)
       }
    }
}