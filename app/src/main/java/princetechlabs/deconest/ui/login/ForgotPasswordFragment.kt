package princetechlabs.deconest.ui.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.FragmentForgotPasswordBinding
import princetechlabs.deconest.ui.utils.NetworkUtils

class ForgotPasswordFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ForgotPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ForgotPasswordViewModel::class.java]

        setupClickListeners()
        observeViewModel()
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.tvBackToLogin.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSendResetLink.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            if (validateEmail(email)) {
                if (NetworkUtils.isInternetAvailable(requireContext())) {
                    viewModel.sendResetLink(email)
                } else {
                    showSnackbar(getString(R.string.error_no_internet))
                }
            }
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            setLoadingState(isLoading)
        }

        viewModel.resetResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ForgotPasswordViewModel.ResetResult.Success -> {
                    showSuccessState()
                }
                is ForgotPasswordViewModel.ResetResult.Error -> {
                    handleError(result.message)
                }
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        binding.tilEmail.error = null
        
        return when {
            email.isEmpty() -> {
                binding.tilEmail.error = getString(R.string.error_empty_email)
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.tilEmail.error = getString(R.string.error_invalid_email)
                false
            }
            else -> true
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        binding.btnSendResetLink.isEnabled = !isLoading
        binding.btnSendResetLink.text = if (isLoading) "" else getString(R.string.btn_send_reset_link)
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showSuccessState() {
        showSnackbar(getString(R.string.success_reset_link_sent))
        binding.btnSendResetLink.isEnabled = false
        
        // Navigate back after 3 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            if (isAdded) {
                findNavController().navigateUp()
            }
        }, 3000)
    }

    private fun handleError(message: String) {
        if (message.contains("user not found", ignoreCase = true) || 
            message.contains("no user record", ignoreCase = true)) {
            binding.tilEmail.error = getString(R.string.error_user_not_found)
        } else {
            showSnackbar(message)
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
