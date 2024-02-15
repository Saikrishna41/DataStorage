package com.techfortyone.datastorage

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.techfortyone.datastorage.presentation.PostLists
import com.techfortyone.datastorage.presentation.viewmodel.MainViewModel
import com.techfortyone.datastorage.ui.theme.DataStorageTheme
import com.techfortyone.datastorage.utils.SharedPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var sharedPreferenceManager : SharedPreferenceManager

//        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
//            if (isGranted) {
//                Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show()
//            }
//        }


    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {

            DataStorageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel by viewModels<MainViewModel>()
                    val postData = viewModel.postData.collectAsState()
                    Column {
                        Button(onClick = { viewModel.incrementQuantity() }) {
                            Text(text = "click here")
                        }
                        PostLists(modifier = Modifier.fillMaxSize(), postListData = postData)
                    }

                    val quantity = viewModel.quantity.collectAsState()
                    sharedPreferenceManager.savedPreferences("counter", quantity.value)
                    val res = sharedPreferenceManager.getPreference("counter")
                    Log.d("TAGSS", "shared pref data: $res")
                }
            }
        }
    }

    private fun notPermissionGranted(permission: String) = ActivityCompat.checkSelfPermission(
        this,
        permission
    ) != PackageManager.PERMISSION_GRANTED
}

