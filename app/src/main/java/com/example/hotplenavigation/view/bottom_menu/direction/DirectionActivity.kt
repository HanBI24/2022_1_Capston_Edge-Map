package com.example.hotplenavigation.view.bottom_menu.direction

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.example.hotplenavigation.R
import com.example.hotplenavigation.base.BindingActivity
import com.example.hotplenavigation.databinding.FragmentCommBinding
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DirectionActivity : BindingActivity<FragmentCommBinding>(R.layout.fragment_comm) {

    override fun initView() {

    }
}