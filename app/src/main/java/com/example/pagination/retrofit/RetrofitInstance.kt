import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://www.arbeitnow.com/api/"

    val apiService: JobApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())  // Gson converter to handle JSON response
            .build()
            .create(JobApiService::class.java)  // Create the API service
    }
}
