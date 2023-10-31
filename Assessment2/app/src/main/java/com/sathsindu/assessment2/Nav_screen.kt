package com.sathsindu.assessment2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.sathsindu.assessment2.service.HelloService
import com.sathsindu.assessment2.ssl.CustomTLSConfiguration.customHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Nav_screen : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    private var helloService: HelloService? = null
    private var txtDynamicMessage: TextView? = null
    private var drawerLayout: DrawerLayout? = null
    private var navigationView: NavigationView? = null
    private var toolbar: Toolbar? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        txtDynamicMessage = findViewById(R.id.txtDynamicMessage)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/") // Replace with your server base URL
            .client(customHttpClient(jwtToken))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        helloService = retrofit.create(HelloService::class.java)
        val call = helloService?.hello()
        call!!.enqueue(object : Callback<HelloResponse?> {
            override fun onResponse(
                call: Call<HelloResponse?>,
                response: Response<HelloResponse?>
            ) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    txtDynamicMessage?.text = loginResponse!!.message
                } else {
                    // go back to login activity if it is an unauthorized error
                    if (response.code() == 401) {
                        val intent = Intent(this@Nav_screen,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {

                        Toast.makeText(this@Nav_screen, "Invalid response.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

            override fun onFailure(call: Call<HelloResponse?>, t: Throwable) {
                t.printStackTrace()

                val intent = Intent(this@Nav_screen, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })


        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navView)
        toolbar = findViewById(R.id.toolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()
        navigationView?.bringToFront()
        navigationView?.setNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.Item1 ->                 // Handle menu item 1 action
                replaceFragment(NewFragment::class.java, null)
            R.id.Item2 -> {
                val intentToNavigateToRecyclerScreen = Intent(this, RecyclerScreen::class.java)
                startActivity(intentToNavigateToRecyclerScreen)
            }
        }

        // Close the drawer after handling item selection
        drawerLayout!!.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        // Close the drawer when the back button is pressed
        if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
            drawerLayout!!.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun replaceFragment(fragmentClass: Class<out Fragment?>, bundle: Bundle?) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerView, fragmentClass, bundle)
            .setReorderingAllowed(true)
            .addToBackStack("name")
            .commit()

    }
    private val jwtToken: String?
        private get() {
            val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
            return sharedPreferences.getString("jwtToken", null)
        }

}