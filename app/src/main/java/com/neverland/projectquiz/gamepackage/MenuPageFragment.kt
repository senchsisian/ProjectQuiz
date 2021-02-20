package com.neverland.projectquiz.gamepackage

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.neverland.projectquiz.*
import com.neverland.projectquiz.autorizationandregister.AuthorizationFragment

@Suppress("UNREACHABLE_CODE")
class MenuPageFragment : Fragment() {
    private var playButton: Button? = null
    private var pointsOfGame: Button? = null
    private var usernameText: TextView? = null
    private lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        sharedPreferences=context!!.getSharedPreferences(AUTHORIZATION, Context.MODE_PRIVATE)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        Action Bar Menu ակտիվացում
        inflater.inflate(R.menu.menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        //Մենյուի տարրերի  Listener ապահովում
        when(item.itemId){
            R.id.exit_menu->{
                sharedPreferences.edit()?.putString(GET_EMAIL, "")?.apply()
                sharedPreferences.edit()?.putString(GET_PASS, "")?.apply()
                (activity as MainActivity).supportActionBar?.hide()
                (activity as MainActivity).supportFragmentManager.beginTransaction().apply {
                    this.replace(R.id.main_activity, AuthorizationFragment(), AUTHORIZATION_FRAGMENT_TAG)
                    commit()
                }
            }

        }
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val infLater = inflater.inflate(R.layout.fragment_menu_page, container, false)
        initViews(infLater)
        //ստանում ենք խաղացողի անունը-օգտանունը
        val sharedUsername = sharedPreferences.getString(GET_USERNAME, "")
        usernameText?.text=sharedUsername
        return infLater
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playButton!!.setOnClickListener {
            //խաղալ կոճակի հրահանգը
            (activity as MainActivity).supportActionBar?.hide()
            val fragmentTransaction =
                (activity as MainActivity).supportFragmentManager.beginTransaction()
            fragmentTransaction.apply {
                this.add(R.id.main_activity, StartPageFragment(), START_PAGE_FRAGMENT_TAG)
                this.addToBackStack( null)
                commit()
            }
        }


    }

    private fun initViews(view: View) {
        //ֆրագմենտի տարրերի վերագրում
        playButton = view.findViewById(R.id.playButton)
        pointsOfGame = view.findViewById(R.id.points_of_game)
        usernameText = view.findViewById(R.id.username_text)
    }

}