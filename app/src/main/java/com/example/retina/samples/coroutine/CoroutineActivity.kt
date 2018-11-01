package com.example.retina.samples.coroutine

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import br.com.luan2.lgutilsk.utils.createSnackProgress
import br.com.luan2.lgutilsk.utils.dismissSnackProgress
import com.example.retina.samples.R
import com.example.retina.samples.main.retrofit.ApiInterface
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import org.koin.android.ext.android.inject

class CoroutineActivity : AppCompatActivity() {

    val api: ApiInterface by inject()
    lateinit var snack: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)


        GlobalScope.launch(Dispatchers.Main) {
            requestApi()
        }

    }

    suspend fun requestApi() {

        snack = createSnackProgress("Buscando dados...")
        snack.show()

        val response = api.getPostDeferred().await()
        if (response.isSuccessful) {
            Log.i("Posts: ", response.body()?.joinToString(" | "))
        }

        snack.dismissSnackProgress(this@CoroutineActivity)

    }

}
