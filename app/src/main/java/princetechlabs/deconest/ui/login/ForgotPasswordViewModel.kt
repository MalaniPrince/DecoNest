package princetechlabs.deconest.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _resetResult = MutableLiveData<ResetResult>()
    val resetResult: LiveData<ResetResult> = _resetResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun sendResetLink(email: String) {
        _isLoading.value = true
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    _resetResult.value = ResetResult.Success
                } else {
                    val exception = task.exception
                    _resetResult.value =
                        ResetResult.Error(exception?.message ?: "An unknown error occurred")
                }
            }
    }

    sealed class ResetResult {
        object Success : ResetResult()
        data class Error(val message: String) : ResetResult()
    }
}
