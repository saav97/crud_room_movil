package com.example.appretorifie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.appretorifie.home.HomeScreen
import com.example.appretorifie.ui.theme.AppRetorifieTheme

data class Product(val nombre:String, val precio:Int)

val products: ArrayList<Product> = arrayListOf(
    Product("Notebook", 700000),
    Product("TAblet", 7000),
    Product("Iphone", 150),
    Product("Monitor", 15000000),
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppRetorifieTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(products)
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun GreetingPreview() {
    AppRetorifieTheme {
        HomeScreen(products)
    }
}