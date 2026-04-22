package princetechlabs.deconest.ui.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import princetechlabs.deconest.ui.data.DummyProductResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ProductApiService {

    @GET("products/category/furniture")
    suspend fun getFurnitureProducts(
        @Query("limit") limit: Int = 30
    ): Response<DummyProductResponse>

    @GET("products/category/home-decoration")
    suspend fun getHomeDecorationProducts(
        @Query("limit") limit: Int = 30
    ): Response<DummyProductResponse>

    @GET("products/category/kitchen-accessories")
    suspend fun getKitchenProducts(
        @Query("limit") limit: Int = 20
    ): Response<DummyProductResponse>

    @GET("products/category/womens-bags")
    suspend fun getLuxuryProducts(
        @Query("limit") limit: Int = 15
    ): Response<DummyProductResponse>

    @GET("products")
    suspend fun searchProducts(
        @Query("q") query: String,
        @Query("limit") limit: Int = 30
    ): Response<DummyProductResponse>

    companion object {
        private const val BASE_URL = "https://dummyjson.com/"

        fun create(): ProductApiService {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ProductApiService::class.java)
        }
    }
}
