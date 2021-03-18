package com.example.tinder

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.tinder.ui.TinderTheme
import org.junit.Rule
import org.junit.Test

class FavouriteUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<FavouritesActivity>()

    @Test
    fun test() {
        // Start the app
        composeTestRule.setContent {
            TinderTheme {
//                FavouritesUi(FavouriteViewModel(Repository(LocalSource())))
            }
        }
    }
}