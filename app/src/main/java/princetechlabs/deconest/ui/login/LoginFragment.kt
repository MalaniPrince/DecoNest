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

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import princetechlabs.deconest.R
import princetechlabs.deconest.databinding.FragmentLoginBinding
import princetechlabs.deconest.ui.home.HomeMainActivity
import princetechlabs.deconest.ui.utils.AppConstant
import princetechlabs.deconest.ui.utils.CustomDialog
import princetechlabs.deconest.ui.utils.PreferenceHelper

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001


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

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(AppConstant.WEB_CLIENT_ID)
        .requestEmail()
        .build()

    googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

    binding.buttonContinueWithGoogle.setOnClickListener {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    binding.CreateAccount.setOnClickListener {
        findNavController().navigate(R.id.SignUpFragment)
    }
}

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == RC_SIGN_IN) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        handleSignInResult(task)
    }
}

private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
    try {
        val account = completedTask.getResult(ApiException::class.java)
        firebaseAuthWithGoogle(account)
    } catch (e: ApiException) {
        CustomDialog.ShowToastMessage(requireContext(),"Sign-in failed: ${e.message}")

    }
}

private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
    auth.signInWithCredential(credential)
        .addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
                CustomDialog.ShowToastMessage(requireContext(),"Login Successful")

                PreferenceHelper.setUserEmail(requireContext(), account.email)
                startActivity(Intent(requireActivity(), HomeMainActivity::class.java))
            } else {
                CustomDialog.ShowToastMessage(requireContext(),"Login Faild")
            }
        }
}
}
