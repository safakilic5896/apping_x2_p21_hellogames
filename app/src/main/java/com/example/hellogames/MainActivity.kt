package com.example.hellogames

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity() {

    fun ArrayWithUniqueValue(list: IntArray) {
        val l = listOf(0, 1, 2, 3, 4, 5, 6, 7)
      Collections.shuffle(l)
      for (i in 0..list.size - 1) {
          list[i] = l[i]
      }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create an explicit intent
        val explicitIntent = Intent(this, Main2Activity::class.java)

        val itemGame =  IntArray(4)
        val idGame = IntArray(4)

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

        val wsCallback: Callback<List<GameList>> = object :Callback<List<GameList>> {
            override fun onFailure(call: Call<List<GameList>>, t: Throwable) {
              Log.w("Failure", "Webservice call failed")
            }

            override fun onResponse(
                call: Call<List<GameList>>,
                response: Response<List<GameList>>) {
                if (response.code() == 200) {
                    val responseData = response.body()

                    ArrayWithUniqueValue(itemGame)
                   // itemGame[0] = (0..8).random()
                    idGame[0] = responseData?.get(itemGame[0])!!.id
                    Glide.with(this@MainActivity)
                        .load(
                            responseData?.get(itemGame[0])?.picture)
                        .into(imageView)

                    //itemGame[1] = (0..8).random()
                    idGame[1] = responseData?.get(itemGame[1])!!.id
                    Glide.with(this@MainActivity)
                        .load(
                            responseData?.get(itemGame[1])?.picture)
                        .into(imageView2)

                   // itemGame[2] = (0..8).random()
                    idGame[2] = responseData?.get(itemGame[2])!!.id
                    Glide.with(this@MainActivity)
                        .load(
                            responseData?.get(itemGame[2])?.picture)
                        .into(imageView3)

                   // itemGame[3] = (0..8).random()
                    idGame[3] = responseData?.get(itemGame[3])!!.id
                    Glide.with(this@MainActivity)
                        .load(
                            responseData?.get(itemGame[3])?.picture)
                        .into(imageView4)
                }
            }

        }

        // Finally, use the service to enqueue the callback
        // This will asynchronously call the method
        service.listAllGames().enqueue(wsCallback)


        imageView.setOnClickListener {
            // Insert extra data in the intent
            explicitIntent.putExtra("ID",idGame[0].toString())

            // Start the other activity by sending the intent
            startActivity(explicitIntent)
        }

        imageView2.setOnClickListener {
            // Insert extra data in the intent
            explicitIntent.putExtra("ID",idGame[1].toString())

            // Start the other activity by sending the intent
            startActivity(explicitIntent)
        }

        imageView3.setOnClickListener {
            // Insert extra data in the intent
            explicitIntent.putExtra("ID",idGame[2].toString())

            // Start the other activity by sending the intent
            startActivity(explicitIntent)
        }

        imageView4.setOnClickListener {
            // Insert extra data in the intent
            explicitIntent.putExtra("ID",idGame[3].toString())

            // Start the other activity by sending the intent
            startActivity(explicitIntent)
        }
    }

}
