package com.example.myfitness

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.myfitness.adapters.MainPagerAdapter
import com.example.myfitness.fragments.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tabs)
        viewPager2 = findViewById(R.id.viewpager)

        val mainPagerAdapter = MainPagerAdapter(supportFragmentManager, lifecycle)

        mainPagerAdapter.addFragment(
            MainPagerAdapter.FragmentItem(
                R.drawable.ic_baseline_fitness_center_24,
                ExercisesFragment::class
            )
        )

        mainPagerAdapter.addFragment(
            MainPagerAdapter.FragmentItem(
                R.drawable.ic_baseline_wysiwyg_24,
                PlanFragment::class
            )
        )

        mainPagerAdapter.addFragment(
            MainPagerAdapter.FragmentItem(
                R.drawable.ic_baseline_show_chart_24,
                ProgressFragment::class
            )
        )

        mainPagerAdapter.addFragment(
            MainPagerAdapter.FragmentItem(
                R.drawable.ic_baseline_calculate,
                CalculatorFragment::class
            )
        )
        mainPagerAdapter.addFragment(
            MainPagerAdapter.FragmentItem(
                R.drawable.ic_baseline_person_24,
                ProfileFragment::class
            )
        )

        mainPagerAdapter.addFragment(
            MainPagerAdapter.FragmentItem(
                R.drawable.ic_baseline_date_range_24,
                CreateWorkoutPlanFragment::class
            )
        )

        viewPager2.adapter = mainPagerAdapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.setIcon(mainPagerAdapter.fragmentItems[position].iconRes)
        }.attach()
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "id_kanala"
            val channelName = "Obavijesti o vjezbanju"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}