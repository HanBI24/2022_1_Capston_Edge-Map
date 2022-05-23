package com.example.hotplenavigation.view.bottom_menu.setting

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.hotplenavigation.R
import com.example.hotplenavigation.base.BindingActivity
import com.example.hotplenavigation.databinding.FragmentCommBinding
import com.example.hotplenavigation.util.LoginState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

@AndroidEntryPoint
class CommActivity : BindingActivity<FragmentCommBinding>(R.layout.fragment_comm) {
    private val commActivityViewModel: CommActivityViewModel by viewModels()
    private lateinit var fetchJob: Job
    private var tokenId: String? = null // Google Auth 인증에 성공하면 token 값으로 설정된다

    /* GoogleSignInOptions */
    private val googleSignInOptions: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    /* GoogleSignIn */
    private val googleSignIn by lazy {
        GoogleSignIn.getClient(this, googleSignInOptions)
    }

    /* FirebaseAuth */
    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    /* Google Auth 로그인 결과 수신 */
    private val loginLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d(TAG, "loginLauncher - result : $result")
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    task.getResult(ApiException::class.java)?.let { account ->
                        Log.d(TAG, "loginLauncher - firebaseAuthWithGoogle : ${account.id}")
                        tokenId = account.idToken
                        commActivityViewModel.saveToken(
                            tokenId ?: throw java.lang.Exception()
                        ) // Loading 상태 이후 Login 상태로 변경
                    } ?: throw Exception()
                } catch (e: Exception) {
                    e.printStackTrace()
                    handleErrorState() // Error 상태
                }
            } else {
                handleErrorState() // Error 상태
            }
        }

    override fun initView() {
        fetchJob = commActivityViewModel.fetchData(tokenId)
        initViews()
        observeData()
    }

    /* view 기본 설정 */
    private fun initViews() = with(binding) {
        tokenId?.let { // 로그인 된 상태
//            Toast.makeText(this@CommActivity, "Login", Toast.LENGTH_SHORT).show()
            Log.d("CommActivity", "Login")
            binding.tvReqLogin.text = "로그인 되었습니다."
        } ?: kotlin.run { // 로그인 안된 상태
//            Toast.makeText(this@CommActivity, "Not Login", Toast.LENGTH_SHORT).show()
            Log.d("CommActivity", "Not Login")
            binding.tvReqLogin.text = "로그인 안 되었습니다."
        }

        binding.btnGoogle.setOnClickListener { // 로그인 버튼 클릭 시
            val signInIntent: Intent = googleSignIn.signInIntent
            loginLauncher.launch(signInIntent) // loginLauncher 로 결과 수신하여 처리
        }
        binding.btnLogout.setOnClickListener { // 로그아웃 버튼 클릭 시
            commActivityViewModel.signOut()

            val opt = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
            val client = GoogleSignIn.getClient(this@CommActivity, opt)
            client.signOut()
            client.revokeAccess()
        }
    }

    /* viewModel 을  관찰하여 상태 변화에 따라 처리 */
    private fun observeData() = commActivityViewModel.loginStateLiveData.observe(this) {
        Log.d(TAG, "observeData() - it : $it")
        when (it) {
            is LoginState.UnInitialized -> initViews()
            is LoginState.Loading -> handleLoadingState()
            is LoginState.Login -> handleLoginState(it)
            is LoginState.Success -> handleSuccessState(it)
            is LoginState.Error -> handleLoadingState()
        }
    }

    /* Loading 상태인 경우 */
    private fun handleLoadingState() = with(binding) {
//        binding.pb.visibility = View.VISIBLE
//        groupLoginRequired.isGone = true
//        groupLogoutRequired.isGone = true
    }

    /* Google Auth Login 상태인 경우 */
    private fun handleLoginState(state: LoginState.Login) = with(binding) {
//        binding.pb.visibility = View.VISIBLE
        val credential = GoogleAuthProvider.getCredential(state.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this@CommActivity) { task ->
                if (task.isSuccessful) { // Login 성공
                    commActivityViewModel.setUserInfo(firebaseAuth.currentUser) // Login 상태 이후 Success 상태로 변경, 정보 설정
                } else { // Login 실패
                    commActivityViewModel.setUserInfo(null)
                }
            }
    }

    /* Google Auth Login Success 상태인 경우 */
    private fun handleSuccessState(state: LoginState.Success) = with(binding) {
//        binding.pb.visibility = View.VISIBLE
        when (state) {
            is LoginState.Success.Registered -> { // Google Auth 등록된 상태
                handleRegisteredState(state) // Success.Registered 상태로 변경
            }
            is LoginState.Success.NotRegistered -> { // Google Auth 미등록된 상태
//                Toast.makeText(this@CommActivity, "NotRegistered", Toast.LENGTH_SHORT).show()
                Log.d("CommActivity", "Not Registered")
//                groupLoginRequired.isVisible = true
//                groupLogoutRequired.isGone = true
            }
        }
    }

    /* Google Auth Login Registered 상태인 경우 */
    private fun handleRegisteredState(state: LoginState.Success.Registered) = with(binding) {
//        groupLogoutRequired.isVisible = true
//        groupLoginRequired.isGone = true
//        Glide.with(this@CommActivity)
//            .load(state.profileImgeUri.toString())
//            .transition(
//                DrawableTransitionOptions.withCrossFade(
//                    DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
//                )
//            )
//            .diskCacheStrategy(DiskCacheStrategy.ALL)
//            .apply {
//                transforms(CenterCrop(), RoundedCorners(60f.fromDpToPx()))
//            }
//            .into(ivProfile)
//        tvUsername.text = state.userName
//        Toast.makeText(this@CommActivity, "Google Auth Login Registered 상태인 경우", Toast.LENGTH_SHORT).show()
        Log.d("CommActivity", "Google Auth Login Registered 상태인 경우")
    }

    /* Error 상태인 경우 */
    private fun handleErrorState() = with(binding) {
//        Toast.makeText(this@CommActivity, "Error State", Toast.LENGTH_SHORT).show()
        Log.d("CommActivity", "Error State")
    }

    companion object {
        const val TAG = "LoginActivity"
    }
}
