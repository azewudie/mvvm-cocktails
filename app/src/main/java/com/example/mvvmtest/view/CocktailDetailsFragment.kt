package com.example.mvvmtest.view
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmtest.R
import com.example.mvvmtest.databinding.FragmentCocktailDetailsBinding
import com.example.mvvmtest.databinding.LayoutIngredientsBinding
import com.example.mvvmtest.model.CocktailDetails
import com.example.mvvmtest.viewmodel.CocktailViewModel
import com.squareup.picasso.Picasso

private const val TAG = "CocktailDetailsFragment"
class CocktailDetailsFragment: Fragment() {

    companion object{
        const val DETAILS_ID_DRINK = "DETAILS_ID_DRINK"
        fun newInstance(idDrink : String) =
            CocktailDetailsFragment().apply{
                arguments = Bundle().apply{
                    putString(DETAILS_ID_DRINK,idDrink)
                }

            }
    }
    // todo create the binding for this fragment
    // todo FragmentCocktailDetailsBinding

    private lateinit var binding: FragmentCocktailDetailsBinding
    private lateinit var mergeBinding: LayoutIngredientsBinding
    private val viewModel: CocktailViewModel by lazy {
        ViewModelProvider(
            this
        ).get(CocktailViewModel::class.java) //->meaning: pass owner and get the key: viewmodel
        // optional  [CocktailViewModel::class.java]
    }

    //todo override onCreateView to init binding
    // todo in  the onCreateView get the arguments
    //todo from the arguments invoke viewModel.getCocktailDetails()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentCocktailDetailsBinding.inflate(
            inflater,
            container,
            false
        )
        mergeBinding = LayoutIngredientsBinding.bind(binding.root) //-> if use merge layout
        arguments?.getString(DETAILS_ID_DRINK)?.let{
            getCocktailDetails(it)
        }

        initObservables()
        return binding.root
    }

    private fun initObservables() {
        viewModel.cocktailDetails.observe(viewLifecycleOwner,
            Observer{
                updateView(it)

            })

    }
    private fun updateView(data: CocktailDetails) {
        mergeBinding .ingredientOne.text = data.drinks[0].strIngredient1
        mergeBinding .ingredientTwo.text = data.drinks[0].strIngredient2
        mergeBinding .ingredientThree.text = data.drinks[0].strIngredient3
        mergeBinding .ingredientFour.text = data.drinks[0].strIngredient3
        binding.cocktailNameDetails.text = data.drinks[0].strDrink
        Picasso.get().load(data.drinks[0].strDrinkThumb)
            .placeholder(R.drawable.ic_android_black_24dp)
            .into(
            binding.cocktailImageDetails
        )

    }

    private fun  getCocktailDetails(idDrink: String){
        viewModel.getCocktailDetails(idDrink)
    }

}


    // todo initObservables and viewModel.cocktailDetails.observer...
    //todo display the data binding tv..




