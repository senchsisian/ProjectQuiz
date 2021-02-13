package com.neverland.projectquiz.autorizationandregister

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.neverland.projectquiz.*
import com.neverland.projectquiz.R
import com.neverland.projectquiz.gamepackage.MenuPageFragment
import com.neverland.projectquiz.gamepackage.StartPageFragment
import com.neverland.projectquiz.models.User

class AuthorizationFragment : Fragment() {
    private var progressBar: ProgressBar? = null
    private var btnSignIn: Button? = null
    private var btnRegister: Button? = null
    private var getEmail: String = EMPTY
    private var loginEmail: String = EMPTY
    private var getPass: String = EMPTY
    private var loginPass: String = EMPTY
    private var getUsername: String = EMPTY
    private var loginUsername: String = EMPTY
    private var isLogin: Boolean = false
    private var isRegister: Boolean = false
    private var menuPageFragment = MenuPageFragment()
    private var sharedPreferences =
        activity?.getSharedPreferences("authorization", Context.MODE_PRIVATE)
    private lateinit var fragmentTransaction: FragmentTransaction
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseDatabase
    private lateinit var users: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Firebase-ի կարգավրումներ և նախապատրաստում
        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()
        users = db.getReference("Users")

        val sharedEmail = sharedPreferences?.getString(GET_EMAIL, "")
        val sharedPass = sharedPreferences?.getString(GET_PASS, "")
        Toast.makeText(
            activity,
            "sharedEmail & sharedPass $sharedEmail $sharedPass",
            Toast.LENGTH_LONG
        ).show()
        if (sharedEmail != "" && sharedPass != "" && sharedEmail != null && sharedPass != null) {
            fragmentTransaction =
                (activity as MainActivity).supportFragmentManager.beginTransaction()
            fragmentTransaction.apply {
                this.add(R.id.main_activity, menuPageFragment, MENU_PAGE_FRAGMENT_TAG)
                commit()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val infVar = inflater.inflate(R.layout.fragment_authorization, container, false)
        btnSignIn = infVar.findViewById(R.id.btnSignIn)
        btnRegister = infVar.findViewById(R.id.btnRegister)
        progressBar = infVar.findViewById(R.id.getProgressBar)
        return infVar
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //կանչվում է մուտքագրման պատուհանը
        btnSignIn?.setOnClickListener {
            onPause()
            callDialogFragment(InputDialogFragment(), INPUT_DIALOG_FRAGMENT_TAG)
        }

        // կանչվում է գրանցման պատուհանը
        btnRegister?.setOnClickListener {
            onPause()
            callDialogFragment(RegisterDialogFragment(), REGISTER_DIALOG_FRAGMENT_TAG)
        }
    }

    override fun onResume() {  //Յուրա ջան, գրանցման և մուտքի համար onResume եմ օգտագործել, ճի՞շտ եմ արել։
        super.onResume()
        //Իրականացվում է մուտքի վավերացման տվյալների ստացում
        loginEmail = arguments?.getString(GET_EMAIL_INPUT_DIALOG) ?: EMPTY
        loginPass = arguments?.getString(GET_PASS_INPUT_DIALOG) ?: EMPTY
        isLogin = arguments?.getBoolean(IS_LOGIN) ?: isLogin

        //Իրականացվում է գրանցման վավերացման տվյալների ստացում
        getUsername = arguments?.getString(GET_USERNAME_REGISTER_DIALOG) ?: getUsername
        getEmail = arguments?.getString(GET_EMAIL_REGISTER_DIALOG) ?: getEmail
        getPass = arguments?.getString(GET_PASS_REGISTER_DIALOG) ?: getPass
        isRegister = arguments?.getBoolean(IS_REGISTER) ?: isRegister

        //Իրականացվում է մուտքի վավերացման տվյալների ստուգում  և մուտք

        isLogin = loginUser(loginPass, loginEmail, isLogin)

        //Իրականացվում է գրանցման տվյալների ստուգում և գրանցում
        isRegister = createUser(getPass, getUsername, getEmail, isRegister)
    }

    private fun callDialogFragment(getFragment: AppCompatDialogFragment, fragmentTag: String) {

        //Տվյալների փոխանցում bundle
        val bundle = Bundle()
        bundle.putString(GET_USERNAME, getUsername)
        bundle.putString(GET_EMAIL, getEmail)
        bundle.putString(GET_PASS, getPass)

        // DialogFragment կանչի գործընթացը
        getFragment.arguments = bundle
        val manager = (activity as MainActivity).supportFragmentManager
        getFragment.show(manager, fragmentTag)
    }

    private fun snackBarMake(getText: String, messageText: String = "") {

        //Լողացող պատուհանի գեներաացում
        Snackbar.make(
            (activity as MainActivity).findViewById(R.id.main_activity),
            getText + messageText,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun createUser(
        get_Pass: String,
        get_Username: String,
        get_Email: String,
        is_Register: Boolean
    ): Boolean {
        //Իրականացվում է գրանցման տվյալների ստուգում և գրանցում
        if (get_Pass != "" && get_Username != "" && get_Email != "" && is_Register) {
            firebaseAuth.createUserWithEmailAndPassword(get_Email, get_Pass)
                .addOnSuccessListener {
                    val user = User()
                    user.email = get_Email
                    user.pass = get_Pass
                    user.username = get_Username

                    users.child(firebaseAuth.currentUser!!.uid).setValue(user)
                        .addOnSuccessListener { snackBarMake(getText(R.string.register_ok).toString()) }
                }
                .addOnFailureListener {
                    snackBarMake(
                        getText(R.string.register_failed).toString(),
                        it.message.toString()
                    )
                }
        }
        return false
    }

    private fun loginUser(login_Pass: String, login_Email: String, is_Login: Boolean): Boolean {
        //Իրականացվում է մուտքի վավերացման տվյալների ստուգում  և մուտք
        if (login_Pass != "" && login_Email != "" && is_Login) {
            firebaseAuth.signInWithEmailAndPassword(login_Email, login_Pass)
                .addOnSuccessListener {

                    val progressRef = users.child(firebaseAuth.currentUser!!.uid)
                    val valueEventListener = object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (dSnapshot in snapshot.children) {
                                val key: String = dSnapshot.key.toString()
                                val value: String = dSnapshot.value.toString()
                                if (key == USERNAME) loginUsername =
                                    value  //մուտքի ժամանակ ստանում ենք օգտվողի անունը
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.v("error", error.message)
                        }
                    }
                    progressRef.addListenerForSingleValueEvent(valueEventListener)

                    snackBarMake(getText(R.string.login_ok).toString())

                    sharedPreferences?.edit()?.putString(GET_EMAIL, login_Email)?.apply()
                    sharedPreferences?.edit()?.putString(GET_PASS, login_Pass)?.apply()
                    sharedPreferences?.edit()?.putString(GET_USERNAME, loginUsername)?.apply()
                    fragmentTransaction =
                        (activity as MainActivity).supportFragmentManager.beginTransaction()
                    fragmentTransaction.apply {
                        this.add(R.id.main_activity, menuPageFragment, MENU_PAGE_FRAGMENT_TAG)
                        commit()
                    }
                }

                .addOnFailureListener {
                    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
                    snackBarMake(getText(R.string.login_failed).toString(), it.message.toString())
                }

        }
        return false
    }
}
