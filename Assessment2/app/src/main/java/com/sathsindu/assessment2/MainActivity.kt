package com.sathsindu.assessment2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.sathsindu.assessment2.service.AuthService
import com.sathsindu.assessment2.ssl.CustomTLSConfiguration.customHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    

    private var newusername: EditText? = null
    private var newpassword: EditText? = null
    private var authService: AuthService? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newusername = findViewById(R.id.username)
        newpassword = findViewById(R.id.password)
        val loginButton1 = findViewById<Button>(R.id.loginButton)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/") // Replace with your server base URL
            .client(customHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        authService = retrofit.create(AuthService::class.java)

        loginButton1?.setOnClickListener(View.OnClickListener { v: View? ->
            val username = newusername?.text.toString().trim { it <= ' ' }
            val password = newpassword?.text.toString().trim { it <= ' ' }
            login(username, password)
        })
    }

    private fun login(username: String, password: String) {
        val loginRequest = LoginRequest(username, password)
        val call = authService!!.login(loginRequest)
        call!!.enqueue(object : Callback<LoginResponse?> {
            override fun onResponse(
                call: Call<LoginResponse?>,
                response: Response<LoginResponse?>
            ) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        val jwtToken = loginResponse.token
                        // Store the JWT token securely (e.g., SharedPreferences or encrypted storage)
                        val sharedPreferences = getSharedPreferences("MyPrefs",
                            AppCompatActivity.MODE_PRIVATE
                        )
                        val editor = sharedPreferences.edit()
                        editor.putString("jwtToken", jwtToken)
                        editor.apply()

                        // Navigate to the next activity (e.g., HomeActivity)
                        val intent = Intent(this@MainActivity, Nav_screen::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    // This code handles unsuccessful login and displays on the bottom (e.g., show an error message)
                    Toast.makeText(
                        this@MainActivity,
                        "Invalid credentials, please try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                t.printStackTrace()
                // This code handles network failure or other errors
                Toast.makeText(this@MainActivity, "Login failed: " + t.message, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}
