package com.example.hellogames

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        //URL

        var url: String = ""

        // Define an implicit intent
        val implicitIntent = Intent(Intent.ACTION_VIEW)

        // retrieve the intent that caused the activity to open
        val originIntent = intent

        // extract data from the intent
        val message = originIntent.getStringExtra("ID")

        // The base URL where the WebService is located
        val baseURL = "https://androidlessonsapi.herokuapp.com/api/game/"
        // Use GSON library to create our JSON parser
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())

        // Create a Retrofit client object targeting the provided URL
        // and add a JSON converter (because we are expecting json responses)
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()

        // Use the client to create a service:
        // an object implementing the interface to the WebService
        val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)

        val wsCallback: Callback<GameDetails> = object :Callback<GameDetails> {
            override fun onFailure(call: Call<GameDetails>, t: Throwable) {
                Log.w("FAILED", "FAILED TO CALL WS")
            }

            override fun onResponse(call: Call<GameDetails>, response: Response<GameDetails>) {
                val responseData = response.body()
                name.setText(responseData?.name)
                type.setText(responseData?.type)
                players.setText(responseData?.players.toString())
                year.setText(responseData?.year.toString())
                description.setText(responseData?.description_en)
                url += responseData!!.url
                Glide.with(this@Main2Activity)
                    .load(
                        responseData?.picture)
                    .into(imageView5)
            }

        }
        // Finally, use the service to enqueue the callback
        // This will asynchronously call the method
        service.detailGame(message.toInt()).enqueue(wsCallback)

        button.setOnClickListener {
            // Add the required data in the intent (here the URL we want to open)
            implicitIntent.data = Uri.parse(url)

            // Launch the intent
            startActivity(implicitIntent)

        }
    }
}
