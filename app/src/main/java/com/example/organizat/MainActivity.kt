package com.example.organizat

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private var inicio_de_sesion: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar:Toolbar=findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer=findViewById(R.id.drawer_layout)
        toggle=ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigatrion_drawer_open,R.string.navigatrion_drawer_close)
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView: NavigationView= findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,inicio())
                .commit()
            checkUserSession()
        }
    }

    override fun onStart() {
        super.onStart()
        checkUserSession()
    }
    private fun checkUserSession(){
        val sharedPreferences = getSharedPreferences("user_prefs",0)
        val userEmail = sharedPreferences.getString("user_email",null)

        if(userEmail != null){
            Log.d("MainActivity","sesión ya iniciada con: $userEmail")
            goToMainFragment()
        }else{
            changeStatus()
        }
    }
    private fun goToMainFragment(){
        inicio_de_sesion=true
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val menu = navigationView.menu
        val loginItem = menu.findItem(R.id.item5)
        loginItem?.title = "Logout"
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,Inicio2())
            .commit()
    }
    private fun changeStatus(){
        inicio_de_sesion= false
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val menu = navigationView.menu
        val loginItem = menu.findItem(R.id.item5)
        loginItem?.title="Login"
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,inicio())
            .commit()
    }
    fun updateMenuTitle(newTitle: String){
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        val menu = navigationView.menu
        val loginItem= menu.findItem(R.id.item5)
        loginItem.title=newTitle
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment: Fragment =when(item.itemId){
            R.id.item1->crearTarea()
            R.id.item2->MostrarT()
            R.id.item5->login()
            else -> if(inicio_de_sesion) Inicio2() else inicio()
        }

        if(fragment is login && inicio_de_sesion){
            val sharedPreferences = getSharedPreferences("user_prefs",0)
            with(sharedPreferences.edit()){
                clear()
                apply()
            }
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()

            val navigationView = findViewById<NavigationView>(R.id.nav_view)
            val menu = navigationView.menu
            val loginItem = menu.findItem(R.id.item5)
            loginItem?.title="Login"

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,inicio())
                .commit()

            drawer.closeDrawer(GravityCompat.START)
            return true

        }
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .addToBackStack(null)
            .commit()
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}