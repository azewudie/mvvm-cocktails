package com.example.mvvmtest.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmtest.model.CocktailDetails
import com.example.mvvmtest.model.CocktailSearch
import com.example.mvvmtest.model.remote.API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CocktailViewModel:ViewModel(){
    private val _cocktailSearchResult = MutableLiveData<CocktailSearch>()
    // Backing Field
    val cocktailSearchResult:LiveData<CocktailSearch>

    get() = _cocktailSearchResult
    private val _cocktailDetails = MutableLiveData<CocktailDetails>()
    val cocktailDetails:LiveData<CocktailDetails>
    get() =_cocktailDetails

    private val _errorMessages = MutableLiveData("")

    val errorMessages:LiveData<String>
    get() = _errorMessages

    fun searchCocktail(cocktailName: String){
        API.cocktailApi.queryCocktailByName(cocktailName)
            .enqueue(
                object: Callback<CocktailSearch>{
                    /**
                     * Invoked for a received HTTP response.
                     *
                     *
                     * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
                     * Call [Response.isSuccessful] to determine if the response indicates success.
                     */
                    override fun onResponse(
                        call: Call<CocktailSearch>,
                        response: Response<CocktailSearch>
                    ) {
                       if(response.isSuccessful){
                           response.body()?.let{
                               _cocktailSearchResult.value = it
                           }?: kotlin.run { _errorMessages.value = response.message() }
                       }else{
                           _errorMessages.value = response.message()
                       }
                    }

                    /**
                     * Invoked when a network exception occurred talking to the server or when an unexpected exception
                     * occurred creating the request or processing the response.
                     */
                    override fun onFailure(call: Call<CocktailSearch>, t: Throwable) {
                        t.printStackTrace()
                       _errorMessages.value = t.message?: "Unknown error"
                    }
                }
            )
    }
    fun getCocktailDetail(cocktailID:String){
        API.cocktailApi.queryCocktailDetails(cocktailID).enqueue(
            object :Callback<CocktailDetails>{
                /**
                 * Invoked for a received HTTP response.
                 * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
                 * Call [Response.isSuccessful] to determine if the response indicates success.
                 */
                override fun onResponse(
                    call: Call<CocktailDetails>,
                    response: Response<CocktailDetails>
                ) {
                   if(response.isSuccessful){
                       response.body()?.let{
                           _cocktailDetails.value = it
                       }?:kotlin.run {
                           _errorMessages.value = response.message()
                       }
                   }else{
                       _errorMessages.value = response.message()
                   }
                }
                /**
                 * Invoked when a network exception occurred talking to the server or when an unexpected exception
                 * occurred creating the request or processing the response.
                 */
                override fun onFailure(call: Call<CocktailDetails>, t: Throwable) {
                  t.printStackTrace()
                    _errorMessages.value = t.message ?:"Unknown error"
                }

            }
        )
    }













}