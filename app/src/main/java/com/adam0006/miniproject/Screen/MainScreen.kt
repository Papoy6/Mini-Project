package com.adam0006.miniproject.Screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.adam0006.miniproject.R
import com.adam0006.miniproject.navigation.Screen

// Fungsi Share menggunakan Intent Implisit [cite: 389]
private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    onHitung: (String) -> Unit
) {
    val context = LocalContext.current
    // Pakai rememberSaveable biar input gak ilang pas balik dari history
    var hargaInput by rememberSaveable { mutableStateOf("") }
    var tabunganInput by rememberSaveable { mutableStateOf("") }
    var hasilHari by rememberSaveable { mutableStateOf("0") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kalkulator Penghematan", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.History.route) }) {
                        Icon(imageVector = Icons.Outlined.History, contentDescription = "History")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_money), // Pastikan file ada di drawable
                contentDescription = "Logo",
                modifier = Modifier.size(120.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = hargaInput,
                onValueChange = { hargaInput = it },
                label = { Text("Harga Barang (Rp)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = tabunganInput,
                onValueChange = { tabunganInput = it },
                label = { Text("Nabung Harian (Rp)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val harga = hargaInput.toDoubleOrNull() ?: 0.0
                    val harian = tabunganInput.toDoubleOrNull() ?: 0.0
                    if (harian > 0) {
                        val hasil = kotlin.math.ceil(harga / harian).toInt()
                        hasilHari = hasil.toString()
                        onHitung("Target: Rp $hargaInput | Nabung: Rp $tabunganInput | $hasil Hari")
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Hitung Estimasi")
            }

            if (hasilHari != "0") {
                Spacer(modifier = Modifier.height(30.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Estimasi lunas dalam:")
                        Text(text = "$hasilHari Hari", style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Tombol Bagikan [cite: 425]
                Button(
                    onClick = {
                        val pesan = "Target Rp $hargaInput, nabung Rp $tabunganInput/hari. Lunas dalam $hasilHari hari!"
                        shareData(context, pesan)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Bagikan Hasil")
                }
            }
        }
    }
}