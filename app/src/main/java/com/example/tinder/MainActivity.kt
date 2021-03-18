/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.tinder

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.tinder.ui.TinderTheme
import com.example.tinder.ui.composables.MainUi
import com.example.tinder.util.NetworkHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Launcher Activity
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var networkHelper: NetworkHelper

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * Using Jetpack compose for the UI instead of XML
         */
        setContent {
            TinderTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    /**
                     * App is the main composable for this app. It is the entry point
                     * for all different views for this activity
                     */
                    MainUi(mainViewModel, { swipeResult, person ->
                        mainViewModel.executeNext(swipeResult, person)
                    }, {
                        // on favourite click
                        startActivity(Intent(this@MainActivity, FavouritesActivity::class.java))
                    })
                }
            }
        }
        /**
         * Getting the profile after loading the view
         */
        if(networkHelper.isNetworkConnected()){
            mainViewModel.getProfiles()
        }else{
            Toast.makeText(
                this,
                "No Internet Connected",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

/**
 * Test Function
 */
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

/**
 * Test Function
 */
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TinderTheme {
        Greeting("Android")
    }
}