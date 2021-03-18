package com.example.tinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Surface
import com.example.tinder.ui.TinderTheme
import com.example.tinder.ui.composables.FavouritesUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesActivity : AppCompatActivity() {
    private val favouriteViewModel: FavouriteViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TinderTheme {
                Surface {
                    FavouritesUi(favouriteViewModel)
                }
            }
        }

        favouriteViewModel.getProfiles()
    }
}