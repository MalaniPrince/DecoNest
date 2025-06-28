package princeinfotech.deconest.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import princeinfotech.deconest.R


import princeinfotech.deconest.databinding.FragmentLoginBinding

import princeinfotech.deconest.ui.home.HomeMainActivity
import princeinfotech.deconest.ui.utils.PreferenceHelper


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.LoginButton.setOnClickListener {
            val userEmail = binding.InputEmail.text.toString()
            val password = binding.InputPassward.text.toString()

            if (userEmail == "admin@gmail.com" && password == "123") {
                PreferenceHelper.setUserEmail(requireContext(),userEmail)
                startActivity(Intent(requireContext(), HomeMainActivity::class.java))
            } else {
                Toast.makeText(
                    requireContext(),
                    "Enter a Valid Username & Password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.CreateAccount.setOnClickListener{
            findNavController().navigate(R.id.SignUpFragment)
        }
        }

    }