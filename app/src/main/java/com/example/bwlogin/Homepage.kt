package com.example.bwlogin

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import org.w3c.dom.Text

class Homepage : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    internal var doubleBackToExitPressedOnce = false
    lateinit var mDatabase : DatabaseReference
    val mauth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        displaySelectedScreen(R.id.nav_menu1)

        /*var token = getSharedPreferences("email", Context.MODE_PRIVATE)
        var neverused = token.getString("loginEmail", " ")

        val Logout = findViewById<View>(R.id.action_settings)

        Logout.setOnClickListener{
            var editor = token.edit()
            editor.putString("loginEmail", " ")
            editor.commit()

            startActivity(Intent(this, MainActivity::class.java))
            finish()}*/


    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this,"Please click BACK again to exit",Toast.LENGTH_SHORT).show()

            Handler().postDelayed({doubleBackToExitPressedOnce = false} , 2000)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.homepage, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item!!.itemId==R.id.action_settings){
            mauth.signOut()
            intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            finish()
            startActivity(intent)
            Toast.makeText(this,"Logged Out",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun displaySelectedScreen(itemId: Int) {
        var fragment:Fragment?=null
        when (itemId) {
            R.id.nav_menu1 -> fragment = insta()
            //R.id.nav_menu2 -> fragment = goodreads()
            R.id.nav_menu3 -> fragment = comm3()
            R.id.nav_menu5 -> fragment = settings()
        }

//replacing the fragment
        if (fragment != null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.content_frame, fragment)
            ft.commit()
        }

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        displaySelectedScreen(item.itemId)
        return true
    }
}
