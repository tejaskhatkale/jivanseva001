package com.beingdeveloper.jivanseva

import ViewPagerAdapter
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.beingdeveloper.jivanseva.fragments.ActionBarFragment
import com.beingdeveloper.jivanseva.fragments.BusinessManagFragment
import com.beingdeveloper.jivanseva.fragments.EPinManagFragment
import com.beingdeveloper.jivanseva.fragments.EWalletFragment
import com.beingdeveloper.jivanseva.fragments.EditProfileFragment
import com.beingdeveloper.jivanseva.fragments.PayoutFragment
import com.beingdeveloper.jivanseva.fragments.ReportFragment
import com.beingdeveloper.jivanseva.fragments.ShareAppFragment
import com.beingdeveloper.jivanseva.fragments.TeamManagFragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    
    private lateinit var toolbar : Toolbar   // toolbar
    private lateinit var drawerLayout: DrawerLayout    // drawer_layout
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.action_bar)

        toolbar = findViewById(R.id.action_bar)        // finding toolbar
        setSupportActionBar(toolbar)                   // setting toolbar

        drawerLayout = findViewById(R.id.drawer_layout)    // finding correct view for drawer layout
        val menuIcon = findViewById<ImageView>(R.id.menu_icon)

        menuIcon.setOnClickListener{
            drawerLayout.openDrawer((GravityCompat.END))
        }

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)

        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        // Connect TabLayout with ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Business"
                1 -> "Income"
                else -> null
            }
        }.attach()

        // fragments code to connect each fragment to its respective code
        // Step 1 --> add toolbar and drawerLayout
        // Step 2 --> add NavigationView
        // Step 3 --> Set Toggle
        // Step 4 --> Define replaceFragment function
        // Step 5 --> Handle onBackPressed function {overridden}

        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener(this)

        //set toggle
        // this refers to mainActivity
        // drawerLayout = sidebar that opens when icon is clicked
        // toolbar = actionBar {first horizontal section in app}
        // strings "open_nav" & "close_nav"  used to access opening and closing of navigation bar



        onBackPressedCallback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                //check is drawer is open
                if(drawerLayout.isDrawerOpen(GravityCompat.END)){
                    //close the drawer if open
                    drawerLayout.closeDrawer(GravityCompat.END)
                }
                else{
                    //Allow back press to proceed if drawer is closed
                    isEnabled = false //disable callback temporarily
                    onBackPressedDispatcher.onBackPressed()   //call back action
                    isEnabled = true // re-enable callback
                }
            }
        }

        onBackPressedDispatcher.addCallback(this,onBackPressedCallback)

        // end of onCreate Function
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navigation_view -> replaceFragment(ActionBarFragment())
            R.id.edit_profile -> replaceFragment(EditProfileFragment())
            R.id.team -> replaceFragment(TeamManagFragment())
            R.id.e_pin -> replaceFragment(EPinManagFragment())
            R.id.e_wallet -> replaceFragment(EWalletFragment())
            R.id.payout -> replaceFragment(PayoutFragment())
            R.id.business ->replaceFragment(BusinessManagFragment())
            R.id.report -> replaceFragment(ReportFragment())
            R.id.share -> replaceFragment(ShareAppFragment())
        }
        drawerLayout.closeDrawer(GravityCompat.END)
        return true
    }

    private fun replaceFragment(fragment: Fragment){
        val transaction:FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    override fun onDestroy() {
        super.onDestroy()
        onBackPressedCallback.remove()
    }
}
