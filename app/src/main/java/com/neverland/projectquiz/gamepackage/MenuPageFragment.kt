package com.neverland.projectquiz.gamepackage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.neverland.projectquiz.*

class MenuPageFragment : Fragment() {
    private var changeAvatar: Button? = null
    private var playButton: Button? = null
    private var pointsOfGame: Button? = null
    private var optionsButton: Button? = null
    private val startPageFragment = StartPageFragment()
    private lateinit var fragmentTransaction: FragmentTransaction

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val infLater = inflater.inflate(R.layout.fragment_menu_page, container, false)
        initViews(infLater)
        return infLater
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playButton!!.setOnClickListener {

            fragmentTransaction =
                (activity as MainActivity).supportFragmentManager.beginTransaction()
            fragmentTransaction.apply {
                this.add(R.id.main_activity, startPageFragment, START_PAGE_FRAGMENT_TAG)
                commit()
            }
        }

    }

    private fun initViews(view: View) {
        changeAvatar = view.findViewById(R.id.change_avatar)
        playButton = view.findViewById(R.id.playButton)
        pointsOfGame = view.findViewById(R.id.points_of_game)
        optionsButton = view.findViewById(R.id.optionsButton)
    }

}