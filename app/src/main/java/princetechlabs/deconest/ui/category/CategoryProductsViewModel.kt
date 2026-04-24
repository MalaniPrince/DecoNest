package princetechlabs.deconest.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import princetechlabs.deconest.repository.ProductRepository
import princetechlabs.deconest.ui.data.Product

class CategoryProductsViewModel : ViewModel() {

    private val repository = ProductRepository()

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun fetchProductsByCategory(category: String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getProductsByCategory(category).collectLatest { result ->
                _isLoading.value = false
                result.fold(
                    onSuccess = { productList ->
                        _products.value = productList
                        _errorMessage.value = null
                    },
                    onFailure = { error ->
                        _errorMessage.value = error.message ?: "An unknown error occurred"
                    }
                )
            }
        }
    }
}
