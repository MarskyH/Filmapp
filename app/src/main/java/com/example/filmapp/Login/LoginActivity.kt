package com.example.filmapp.Login

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.filmapp.BuildConfig
import com.example.filmapp.Configuracoes.Constants
import com.example.filmapp.R
import com.example.filmapp.home.HomeActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.*
import org.json.JSONObject
import org.json.JSONTokener
import java.io.OutputStreamWriter
import java.net.URL
import java.util.concurrent.TimeUnit
import javax.net.ssl.HttpsURLConnection


class LoginActivity : AppCompatActivity() {

    companion object {
        const val RC_SIGN_IN = 100
    }

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var callbackManager: CallbackManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("731969549194-m34oh6ms65r828mun12clcc4lm2s2fjt.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        mAuth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = mAuth.currentUser
        if (user != null) {
            println("id idfer"+user.providerData[1].providerId)
        }
        updateUI(user)


        callbackManager = CallbackManager.Factory.create()
        val loginButton = findViewById<LoginButton>(R.id.login_button)
        loginButton.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile", "email"))
        }
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                Log.d("TAG", "Success Login")
                val credential = loginResult?.accessToken?.let {
                    FacebookAuthProvider.getCredential(
                        it.token)
                }
                println("id facebook")
                println(credential)
                if (credential != null) {
                    mAuth.signInWithCredential(credential)
                    GlobalScope.launch {
                        delay(2000)
                        getUserProfile(loginResult?.accessToken, loginResult?.accessToken?.userId)
                        callHome()
                    }




                }


            }

            override fun onCancel() {
                Toast.makeText(this@LoginActivity, "Login Cancelled", Toast.LENGTH_LONG).show()
            }

            override fun onError(exception: FacebookException) {
                Toast.makeText(this@LoginActivity, exception.message, Toast.LENGTH_LONG).show()
            }
        })

        if (isLoggedIn()) {

            FirebaseAuth.AuthStateListener { firebaseAuth ->



                callHome()

            }

        }else{


        }

//        login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult>{
//            override fun onSuccess(loginResult: LoginResult) {
//                Log.d(TAG, "facebook:onSuccess:$loginResult")
//                handleFacebookAccessToken(loginResult.accessToken)
//
//            }
//            override fun onCancel() {
//                Log.d(TAG, "facebook:onCancel")
//
//            }
//
//            override fun onError(error: FacebookException) {
//                Log.d(TAG, "facebook:onError", error)
//            }
//        })
        btnLoginAct.setOnClickListener {
            signIn()


        }




        btnRegisterAct.setOnClickListener {
            callCadastro()
        }



        imageView3.setOnClickListener {
            if (user == null) {
                signinFirebaseGoogle()
            }
        }

        recSenha.setOnClickListener {
            val intent = Intent(this, RecuperarSenhaActivity::class.java)
            startActivity(intent)

        }

        entrarSemSenha.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }


    }

    @SuppressLint("LongLogTag")
    fun getUserProfile(token: AccessToken?, userId: String?) {



        val parameters = Bundle()
        parameters.putString(
            "fields",
            "id, first_name, middle_name, last_name, name, picture, email"
        )
        GraphRequest(token,
            "/$userId/",
            parameters,
            HttpMethod.GET,
            GraphRequest.Callback { response ->
                val jsonObject = response.jsonObject

                // Facebook Access Token
                // You can see Access Token only in Debug mode.
                // You can't see it in Logcat using Log.d, Facebook did that to avoid leaking user's access token.
                if (BuildConfig.DEBUG) {
                    FacebookSdk.setIsDebugEnabled(true)
                    FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS)
                }

                // Facebook Id
                if (jsonObject.has("id")) {
                    val facebookId = jsonObject.getString("id")
                    Log.i("Facebook Id: ", facebookId.toString())
                } else {
                    Log.i("Facebook Id: ", "Not exists")
                }


                // Facebook First Name
                if (jsonObject.has("first_name")) {
                    val facebookFirstName = jsonObject.getString("first_name")
                    Log.i("Facebook First Name: ", facebookFirstName)
                } else {
                    Log.i("Facebook First Name: ", "Not exists")
                }


                // Facebook Middle Name
                if (jsonObject.has("middle_name")) {
                    val facebookMiddleName = jsonObject.getString("middle_name")
                    Log.i("Facebook Middle Name: ", facebookMiddleName)
                } else {
                    Log.i("Facebook Middle Name: ", "Not exists")
                }


                // Facebook Last Name
                if (jsonObject.has("last_name")) {
                    val facebookLastName = jsonObject.getString("last_name")
                    Log.i("Facebook Last Name: ", facebookLastName)
                } else {
                    Log.i("Facebook Last Name: ", "Not exists")
                }


                // Facebook Name
                if (jsonObject.has("name")) {
                    val facebookName = jsonObject.getString("name")
                    Log.i("Facebook Name: ", facebookName)
                } else {
                    Log.i("Facebook Name: ", "Not exists")
                }


                // Facebook Profile Pic URL
                if (jsonObject.has("picture")) {
                    val facebookPictureObject = jsonObject.getJSONObject("picture")
                    if (facebookPictureObject.has("data")) {
                        val facebookDataObject = facebookPictureObject.getJSONObject("data")
                        if (facebookDataObject.has("url")) {
                            val facebookProfilePicURL = facebookDataObject.getString("url")
                            Log.i("Facebook Profile Pic URL: ", facebookProfilePicURL)
                        }
                    }
                } else {
                    Log.i("Facebook Profile Pic URL: ", "Not exists")
                }

                // Facebook Email
                if (jsonObject.has("email")) {
                    val facebookEmail = jsonObject.getString("email")
                    Log.i("Facebook Email: ", facebookEmail)
                } else {
                    Log.i("Facebook Email: ", "Not exists")
                }




            }).executeAsync()

//        FirebaseAuth.getInstance().createUserWithEmailAndPassword(facebookEmail, "123456")
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
////                    val firebaseUser: FirebaseUser = task.result?.user!!
//                } else {
//
//                    mAuth.signInWithEmailAndPassword(facebookEmail, "123456")
//                        .addOnCompleteListener(this) { task ->
//                            if (task.isSuccessful) {
//                                // Sign in success, update UI with the signed-in user's information
//                            }
//                        }
//                }
//            }
    }

    fun isLoggedIn(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired



        return isLoggedIn
    }


    fun callCadastro() {
        var intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }

    fun callHome() {
        var intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }


    private fun signinFirebaseGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != RC_SIGN_IN) {
            callbackManager.onActivityResult(requestCode, resultCode, data)
            super.onActivityResult(requestCode, resultCode, data)
        }
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
            try {
                val account = task.getResult(ApiException::class.java)!!
                println("firebaseAuthWithGoogle:" + account.id)
                println("alo");
                firebaseAuthWithGoogle(account.idToken!!)

            } catch (e: ApiException) {
                Toast.makeText(this, "Login falhou.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Constants.loginGoogle = true
                    // Sign in success, update UI with the signed-in user's information
                    println("signInWithCredential:success")
                    val user = mAuth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    println("signInWithCredential:failure" + task.exception)
                    updateUI(null)
                }

            }
    }

    fun updateUI(account: FirebaseUser?) {
        if (account != null) {
//            Toast.makeText(this, "U Signed In successfully", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, HomeActivity::class.java))
        } else {
//            Toast.makeText(this, "U Didnt signed in", Toast.LENGTH_LONG).show()
        }
    }


    fun signIn() {
        val email = edEmailLogin.text.toString()
        val password = edSenhaLogin.text.toString()
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }

            }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {

        try {
            val account = completedTask.getResult(
                ApiException::class.java
            )
            // Signed in successfully
            val googleId = account?.id ?: ""
            Log.i("Google ID", googleId)

            val googleFirstName = account?.givenName ?: ""
            Log.i("Google First Name", googleFirstName)
            Constants.nomeGoogle = googleFirstName

            val googleLastName = account?.familyName ?: ""
            Log.i("Google Last Name", googleLastName)

            val googleEmail = account?.email ?: ""
            Log.i("Google Email", googleEmail)
            Constants.emailGoogle = googleEmail

            val googleProfilePicURL = account?.photoUrl.toString()
            Log.i("Google Profile Pic URL", googleProfilePicURL)

            val googleIdToken = account?.idToken ?: ""
            Log.i("Google ID Token", googleIdToken)

        } catch (e: ApiException) {
            // Sign in was unsuccessful
            Log.e(
                "failed code=", e.statusCode.toString()
            )
        }
    }
}
//    private fun handleFacebookAccessToken(token: AccessToken) {
//
//        val credential = FacebookAuthProvider.getCredential(token.token)
//        mAuth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    val user = mAuth.currentUser
//                    println("deu certo");
//
////                    if (user != null) {
////                    }
//
////                    updateUI(user)
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
////                    updateUI(null)
//                }
//
//                // ...
//            }
//    }





