package com.selva.internetconnection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.selva.internetconnection.ui.theme.InternetConnectionTheme

class MainActivity : ComponentActivity() {

    @Suppress("UNCHECKED_CAST")
    private val viewModel by viewModels<ConnectivityViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ConnectivityViewModel(
                        connectivityObserver = AndroidConnectivityObserver(
                            context = applicationContext
                        )
                    ) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InternetConnectionTheme {
                val isConnected by viewModel.isConnected.collectAsState()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        ElevatedCard(
                            modifier = Modifier
                                .padding(10.dp)
                                .size(height = 40.dp, width = 200.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isConnected) Color.Green else Color.Red,
                                contentColor = Color.Black
                            )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (isConnected) "Internet Connected" else "No Internet Connection"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}