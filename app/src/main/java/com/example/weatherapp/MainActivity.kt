package com.example.weatherapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherapp.ui.theme.WeatherAppTheme
import org.json.JSONObject


const val  APIKEY = "34da9c2cf51f434386f63928252401"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Greeting(
                name = "Almaty",
                this
            )
        }
    }
}

@Composable
fun Greeting(name: String , context: Context) {
    var state =  remember{
        mutableStateOf("Unknown")
    }

    Column(modifier = Modifier.fillMaxSize().padding(5.dp)) {
        Box(modifier = Modifier.fillMaxHeight(0.5f).fillMaxWidth() , contentAlignment = Alignment.Center){
            Text(text = state.value)
        }

        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(10.dp) , contentAlignment = Alignment.BottomCenter){
            Button(onClick = {
                getResult(name , state , context)
            } , modifier = Modifier.padding(bottom = 20.dp).fillMaxWidth()) {
                Text(text ="Refresh")
            }
        }
    }

}


fun getResult(city:String  , state:MutableState<String> , context:Context ){
    val url = "https://api.weatherapi.com/v1/forecast.json?key=$APIKEY&q=$city&aqi=no"

    val queue  =  Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        com.android.volley.Request.Method.GET ,
        url, {
            response ->
            val obj = JSONObject(response)
            state.value = obj.getJSONObject("current").getString("temp_c")
        },{
            error->
            Log.e("MyTag" , "Error $error")
        }
    )
    queue.add(stringRequest)
}

