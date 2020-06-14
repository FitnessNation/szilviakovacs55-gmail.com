package com.example.fitnessnation.fragments

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessnation.LogInFragment

import com.example.fitnessnation.R
import com.example.fitnessnation.Recycler
import com.example.fitnessnation.User
import com.example.fitnessnation.activitites.LoginActivity
import kotlinx.android.synthetic.main.fragment_log_in.*
import kotlinx.android.synthetic.main.fragment_log_in.Password
import kotlinx.android.synthetic.main.fragment_log_in.Username
import kotlinx.android.synthetic.main.fragment_sign_up.*
import java.sql.Types.NULL
import java.util.ArrayList


class SignUpFragment : Fragment() {
    private var username: EditText? = null
    private var password: EditText? = null
    private var users: List<User>? = null
    private var loginButton: Button? = null
    private var vieww: View? = null
    lateinit var recyclerView: RecyclerView
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: Recycler? = null

    companion object {
        fun newInstance() = SignUpFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Toast.makeText(getActivity()?.getApplicationContext(), "SignUp", Toast.LENGTH_LONG).show();
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        users = LoginActivity.appDatabase.dao().getUsers()

        username = view!!.findViewById(R.id.Username)
        password = view!!.findViewById(R.id.Password)

        loginButton = view!!.findViewById(R.id.login_button)

        signup_button.setOnClickListener(View.OnClickListener {
            val username = Username.getText().toString()
            val password = Password.getText().toString()
            val weight = weight.getText().toString()
           // weight.toIntOrNull();

            val height = height.getText().toString()
            //height.toIntOrNull();


            var gender:String="female"

                if(female.isChecked())
                {
                    gender="female"
                   // Toast.makeText(activity, "Female", Toast.LENGTH_SHORT).show()
                }
                else if(male.isChecked())
                {
                    gender="male"
                   // Toast.makeText(activity, "Male", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(activity, "You didn't fill all the boxes11", Toast.LENGTH_SHORT).show()
                }

           SignUpButtonExecute(username, password,weight,height,gender)
            users = LoginActivity.appDatabase.dao().getUsers()

        })
    }

    fun SignUpButtonExecute(username: String, password: String,weight:String,height:String, gender: String) {

        val w=weight.toIntOrNull()
        val h=height.toIntOrNull()
     if(weight.toIntOrNull()==null|| height.toIntOrNull()==null)
     {
    Toast.makeText(activity, "Weight and height should be numbers", Toast.LENGTH_SHORT).show()
     }
        else
     {
         if (username == "" || password == "") {
             Toast.makeText(activity, "You didn't fill all the boxes", Toast.LENGTH_SHORT).show()
         } else {
             val user = User()
             user.setUsername(username)
             user.setPassword(password)
             if (w != null) {
                 user.setWeight(w)
             }
             if (h != null) {
                 user.setHeight(h)
             }
              user.setGender(gender)
              if (findUser(user)) {
                  Toast.makeText(activity, "User is already registered", Toast.LENGTH_SHORT).show()
              } else {
                  LoginActivity.appDatabase.dao().addUser(user)
                  Toast.makeText(activity, "Succesfully registered!", Toast.LENGTH_SHORT).show()
                  toTheLoginFragment();
              }
         }
     }


    }


    private fun toTheLoginFragment()
    {
        val transaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.primary_frame_s_session, LogInFragment.newInstance())
        transaction.addToBackStack(LogInFragment.toString())
        transaction.commit()
    }

    fun findUser(user: User): Boolean {
        for (usr in users!!) {
            if (usr.getUsername().equals(user.getUsername())) return true
        }
        return false
    }


    }
