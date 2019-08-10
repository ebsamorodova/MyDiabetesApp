package ru.hse.cs.ai.kate.app

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.appcompat.widget.Toolbar as Toolbar1
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val navController
        get() = findNavController(R.id.nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(my_toolbar)
        my_toolbar.setTitleTextColor(Color.WHITE)

        setupActionBarWithNavController(navController, drawer_layout)
        nav_view.setupWithNavController(navController)

        nav_view.setNavigationItemSelectedListener {
            val navTarget = when (it.itemId) {
                R.id.action_addition_info -> R.id.addInfoFragment
                R.id.action_contact_doctor -> R.id.doctorFragment
                R.id.action_help -> R.id.helpFragment
                R.id.action_notes -> R.id.notesFragment
                R.id.action_profile -> R.id.profileFragment
                R.id.action_reminder -> R.id.reminderFragment
                R.id.action_settings -> R.id.settingsFragment
                else -> null
            }
            if (navTarget != null) {
                navController.navigate(navTarget)
                drawer_layout.closeDrawer(GravityCompat.START)
            }
            false
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigateUp(findNavController(R.id.nav_host_fragment), drawer_layout)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navTarget = when (item.itemId) {
            R.id.action_settings -> R.id.settingsFragment
            else -> null
        }
        if (navTarget != null) {
            navController.navigate(navTarget)
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

}
