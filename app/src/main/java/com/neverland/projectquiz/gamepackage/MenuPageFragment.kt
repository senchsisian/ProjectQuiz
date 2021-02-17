package com.neverland.projectquiz.gamepackage

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.neverland.projectquiz.*
import com.neverland.projectquiz.autorizationandregister.AuthorizationFragment

@Suppress("UNREACHABLE_CODE")
class MenuPageFragment : Fragment() {
    private var playButton: Button? = null
    private var pointsOfGame: Button? = null
    private var usernameText: TextView? = null
    private val startPageFragment = StartPageFragment()
    private lateinit var fragmentTransaction: FragmentTransaction
    private lateinit var sharedPreferences : SharedPreferences
    private var authorizationFragment = AuthorizationFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        sharedPreferences=context!!.getSharedPreferences(AUTHORIZATION, Context.MODE_PRIVATE)
        (activity as MainActivity).supportActionBar?.show()
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
        when(item.itemId){
            R.id.exit_menu->{
                sharedPreferences.edit()?.putString(GET_EMAIL, "")?.apply()
                sharedPreferences.edit()?.putString(GET_PASS, "")?.apply()
                sharedPreferences.edit()?.putString(GET_USERNAME, "")?.apply()
                (activity as MainActivity).supportFragmentManager.beginTransaction().apply {
                    this.replace(R.id.main_activity, authorizationFragment, AUTHORIZATION_FRAGMENT_TAG)
                    this.addToBackStack(null)
                    commit()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val infLater = inflater.inflate(R.layout.fragment_menu_page, container, false)
        initViews(infLater)
        val sharedUsername = sharedPreferences.getString(GET_USERNAME, "")
        usernameText?.text=sharedUsername
        return infLater
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playButton!!.setOnClickListener {
            fragmentTransaction =
                (activity as MainActivity).supportFragmentManager.beginTransaction()
            fragmentTransaction.apply {
                this.add(R.id.main_activity, startPageFragment, START_PAGE_FRAGMENT_TAG)
                addToBackStack(null)
                commit()
            }
            (activity as MainActivity).supportActionBar?.hide()
        }

    }

    private fun initViews(view: View) {
        playButton = view.findViewById(R.id.playButton)
        pointsOfGame = view.findViewById(R.id.points_of_game)
        usernameText = view.findViewById(R.id.username_text)
    }

}