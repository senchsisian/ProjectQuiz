package com.neverland.projectquiz.gamepackage

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.neverland.projectquiz.*
import com.neverland.projectquiz.autorizationandregister.AuthorizationFragment
import java.io.IOException


@Suppress("UNREACHABLE_CODE", "DEPRECATION")
class MenuPageFragment : Fragment() {
    private var playButton: Button? = null
    private var pointsOfGame: Button? = null
    private var usernameText: TextView? = null
    private var avatarImage: ImageView? = null
    private var getAvatar: String = EMPTY
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        sharedPreferences = context!!.getSharedPreferences(AUTHORIZATION, Context.MODE_PRIVATE)
        getAvatar = sharedPreferences.getString(GET_AVATAR, EMPTY).toString()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        Action Bar Menu ակտիվացում
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @SuppressLint("ResourceAsColor")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        //Մենյուի տարրերի  Listener ապահովում
        when (item.itemId) {
            R.id.exit_menu -> {
                sharedPreferences.edit()?.putString(GET_EMAIL, EMPTY)?.apply()
                sharedPreferences.edit()?.putString(GET_PASS, EMPTY)?.apply()
                (activity as MainActivity).supportActionBar?.hide()
                (activity as MainActivity).supportFragmentManager.beginTransaction().apply {
                    this.replace(
                        R.id.main_activity,
                        AuthorizationFragment(),
                        AUTHORIZATION_FRAGMENT_TAG
                    )
                    commit()
                }
            }
            R.id.avatar_menu -> {
                changeAvatar()
            }
            R.id.info_menu -> {
                val manager = (activity as MainActivity).supportFragmentManager
                val getFragment= AboutOfGame()
                getFragment.show(manager, ABOUT_OF_GAME_TAG)
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
        val sharedUsername = sharedPreferences.getString(GET_USERNAME, EMPTY)
        usernameText?.text = sharedUsername
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
                this.addToBackStack(null)
                commit()
            }
        }
        pointsOfGame!!.setOnClickListener {
            //խաղալ կոճակի հրահանգը
            (activity as MainActivity).supportActionBar?.hide()
            val fragmentTransaction =
                (activity as MainActivity).supportFragmentManager.beginTransaction()
            fragmentTransaction.apply {
                this.add(R.id.main_activity, AllRatingsPageFragment(), ALL_RATING_PAGE_FRAGMENT_TAG)
                this.addToBackStack(null)
                commit()
            }
        }

        avatarImage!!.setOnClickListener {
            changeAvatar()
        }
    }

    private fun initViews(view: View) {
        //ֆրագմենտի տարրերի վերագրում
        playButton = view.findViewById(R.id.playButton)
        pointsOfGame = view.findViewById(R.id.points_of_game)
        usernameText = view.findViewById(R.id.username_text)
        avatarImage = view.findViewById(R.id.getAvatar)
        if (getAvatar != EMPTY) {
            context?.let {
                avatarImage?.setImageURI(null)
                avatarImage?.setImageBitmap(getBitmap(it, Uri.parse(getAvatar)))
            }
        }
    }

    //Իրականացվում է օպերացիոն համակարգի վերսիայի ստուգում և նկարի ձևավորում
    private fun getBitmap(context: Context, imageUri: Uri): Bitmap? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(
                ImageDecoder.createSource(
                    context.contentResolver,
                    imageUri
                )
            )
        } else {
            context
                .contentResolver
                .openInputStream(imageUri)?.use { inputStream ->
                    BitmapFactory.decodeStream(inputStream)
                }
        }
    }

    private fun changeAvatar() {
        //բացում է հեռախոսի գալերիան՝նկարադարանը
        val photoPickerIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        photoPickerIntent.type = IMAGE
        (activity as MainActivity).startActivityForResult(
            Intent.createChooser(photoPickerIntent, "Select Picture"),
            GALLERY_REQUEST,
            null
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //ստանում է ընտրված նկարի հասցեն
        when (requestCode) {
            GALLERY_REQUEST -> if (resultCode == RESULT_OK) {
                val selectedImage: Uri? = data?.data
                try {
                    setAvatar(selectedImage)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    //նկարը տեղադրում imageview-ի մեջ
    private fun setAvatar(uri: Uri?) {
        getAvatar = uri.toString()
        sharedPreferences.edit()?.putString(GET_AVATAR, getAvatar)?.apply()
        avatarImage?.setImageURI(null)
        avatarImage?.setImageURI(uri)
    }
}