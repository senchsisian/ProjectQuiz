package com.neverland.projectquiz.gamepackage

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.neverland.projectquiz.*

class AllRatingsPageFragment : Fragment() {
     private var factSOfHistory: TextView? =null
     private var kingdomOfVan: TextView? =null
     private var yervandunisFamily: TextView? =null
     private var artashesyansFamily: TextView? =null
     private var arshakunisFamily: TextView? =null
     private var bagratunidFamily: TextView? =null
     private var totalRating: TextView? =null
     private var closeButton: Button? =null
     private var backButton: TextView? =null
     private var deleteButton: TextView? =null

     private var mainScore=0
     private var factSOfHistoryScore=0
     private var kingdomOfVanScore=0
     private var yervandunisFamilyScore=0
     private var artashesyansFamilyScore=0
     private var arshakunisFamilyScore=0
     private var bagratunidFamilyScore=0

     private var sharedUsername=EMPTY

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var scoresPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = context!!.getSharedPreferences(GET_USERNAME, Context.MODE_PRIVATE)
        sharedUsername = sharedPreferences.getString(GET_USERNAME, EMPTY).toString()

        scoresPreferences = context!!.getSharedPreferences(SCORES_OF_GAME, Context.MODE_PRIVATE)
        mainScore=scoresPreferences.getInt(sharedUsername,0)
        factSOfHistoryScore=scoresPreferences.getInt("$sharedUsername $FACTS_OF_HISTORY ",0)
        kingdomOfVanScore=scoresPreferences.getInt("$sharedUsername $KINGDOM_OF_VAN ",0)
        yervandunisFamilyScore=scoresPreferences.getInt("$sharedUsername $YERVANDUNIS_FAMILY ",0)
        artashesyansFamilyScore=scoresPreferences.getInt("$sharedUsername $ARTASHESYANS_FAMILY ",0)
        arshakunisFamilyScore=scoresPreferences.getInt("$sharedUsername $ARSHAKUNIS_FAMILY ",0)
        bagratunidFamilyScore=scoresPreferences.getInt("$sharedUsername $BAGRATUNIS_FAMILY ",0)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_ratings_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        factSOfHistory!!.text=factSOfHistoryScore.toString()
        kingdomOfVan!!.text=kingdomOfVanScore.toString()
        yervandunisFamily!!.text=yervandunisFamilyScore.toString()
        artashesyansFamily!!.text=artashesyansFamilyScore.toString()
        arshakunisFamily!!.text=arshakunisFamilyScore.toString()
        bagratunidFamily!!.text=bagratunidFamilyScore.toString()
        totalRating!!.text=mainScore.toString()

        closeButton!!.setOnClickListener {
            (activity as MainActivity).supportActionBar?.show()
            val fragmentTransaction =
                (activity as MainActivity).supportFragmentManager.beginTransaction()
            fragmentTransaction.apply {
                this.replace(R.id.main_activity, MenuPageFragment(), MENU_PAGE_FRAGMENT_TAG)
                commit()
            }
        }
        backButton!!.setOnClickListener {
            (activity as MainActivity).supportActionBar?.show()
            val fragmentTransaction =
                (activity as MainActivity).supportFragmentManager.beginTransaction()
            fragmentTransaction.apply {
                this.replace(R.id.main_activity, MenuPageFragment(), MENU_PAGE_FRAGMENT_TAG)
                commit()
            }
        }
        deleteButton!!.setOnClickListener {
            scoresPreferences.edit()?.putInt(sharedUsername,0)?.apply()
            scoresPreferences.edit()?.putInt("$sharedUsername $FACTS_OF_HISTORY ",ZERO)?.apply()
            scoresPreferences.edit()?.putInt("$sharedUsername $KINGDOM_OF_VAN ",ZERO)?.apply()
            scoresPreferences.edit()?.putInt("$sharedUsername $YERVANDUNIS_FAMILY ",ZERO)?.apply()
            scoresPreferences.edit()?.putInt("$sharedUsername $ARTASHESYANS_FAMILY ", ZERO)?.apply()
            scoresPreferences.edit()?.putInt("$sharedUsername $ARSHAKUNIS_FAMILY ",ZERO)?.apply()
            scoresPreferences.edit()?.putInt("$sharedUsername $BAGRATUNIS_FAMILY ",ZERO)?.apply()
            factSOfHistory!!.text= ZERO_STR
            kingdomOfVan!!.text=ZERO_STR
            yervandunisFamily!!.text=ZERO_STR
            artashesyansFamily!!.text=ZERO_STR
            arshakunisFamily!!.text=ZERO_STR
            bagratunidFamily!!.text=ZERO_STR
            totalRating!!.text=ZERO_STR
        }
    }
    private fun initViews(view: View) {
        //իրականացվում է ֆրագմենտի view-երի ներկայացում
        factSOfHistory = view.findViewById(R.id.facts_of_hstory_rating)
        kingdomOfVan = view.findViewById(R.id.kingdom_of_van_rating)
        yervandunisFamily = view.findViewById(R.id.yervandunis_family_rating)
        artashesyansFamily = view.findViewById(R.id.artashesyans_familiy_rating)
        arshakunisFamily = view.findViewById(R.id.arshakunis_family_rating)
        bagratunidFamily = view.findViewById(R.id.bagratunis_family_rating)
        totalRating = view.findViewById(R.id.total_rating)
        closeButton = view.findViewById(R.id.close_rating_window)
        backButton = view.findViewById(R.id.goto_menu_page)
        deleteButton = view.findViewById(R.id.delete_all_scores)
    }

}