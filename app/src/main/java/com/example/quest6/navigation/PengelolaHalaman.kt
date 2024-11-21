package com.example.quest6.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quest6.ui.view.screen.MahasiswaFormView
import com.example.quest6.ui.view.screen.RencanaStudiView
import com.example.quest6.ui.view.screen.Result
import com.example.quest6.ui.view.screen.SplashScreen
import com.example.quest6.ui.view.viewmodel.MahasiswaViewModel
import com.example.quest6.ui.view.viewmodel.RencanaStudiViewModel

enum class Halaman {
    Splash,
    Mahasiswa,
    Matakuliah,
    Tampil
}

@Composable
fun MahasiswaApp(
    modifier: Modifier = Modifier,
    mahasiswaViewModel: MahasiswaViewModel = viewModel(),
    krsViewModel: RencanaStudiViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val mahasiswaUiState = mahasiswaViewModel.mahasiswaUiState.collectAsState().value

    NavHost(
        navController = navController,
        startDestination = Halaman.Splash.name,
        modifier = Modifier.padding()
    ) {
        composable(route = Halaman.Splash.name) {
            SplashScreen(onMulaiButton = {
                navController.navigate(
                    Halaman.Mahasiswa.name
                )
            })
        }
        composable(route = Halaman.Mahasiswa.name) {
            MahasiswaFormView(
                onSubmitButtonClicked = {
                    mahasiswaViewModel.saveDataSiswa(it)
                    navController.navigate(Halaman.Matakuliah.name)
                },
                onBackButtonClicked = { navController.popBackStack()
                }
            )
        }
        composable(route = Halaman.Matakuliah.name) {
            RencanaStudiView(
                mahasiswa = mahasiswaUiState,
                onSubmitButtonClicked = { krsViewModel.saveDataKRS(it) },
                onBackButtonClicked = { navController.popBackStack() },
                navController = navController
            )
        }
        composable(route = Halaman.Tampil.name) {
            Result (
                mahasiswa = mahasiswaUiState,
                rencanaStudi = krsViewModel.krsStateUi.collectAsState().value,
                onBackButtonClicked = { navController.popBackStack() }
            )
        }
    }
}