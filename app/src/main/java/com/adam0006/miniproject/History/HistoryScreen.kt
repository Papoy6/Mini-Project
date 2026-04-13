package com.adam0006.miniproject

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn // Tambahkan ini
import androidx.compose.foundation.lazy.items // Tambahkan ini
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class) // Perbaikan: hapus satu @
@Composable
fun HistoryScreen(navController: NavHostController, historyData: List<String>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Riwayat Perhitungan") },
                navigationIcon = {
                    // Sesuai Task 3.4 di modul, gunakan popBackStack
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (historyData.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Belum ada riwayat.")
            }
        } else {
            // LazyColumn untuk menampilkan list secara efisien
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                items(historyData) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = item,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}