package com.target.targetcasestudy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.target.targetcasestudy.viewmodel.DealDetailUiState
import com.target.targetcasestudy.viewmodel.DealViewModel

@Composable
fun DealDetail(
    id: String,
    viewModel: DealViewModel,
    onNavigateBack: () -> Unit
) {
    val detailState by viewModel.selectedDealState.collectAsState()

    LaunchedEffect(id) {
        viewModel.loadDealDetails(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                backgroundColor = RedPrimary,
                contentColor = Color.White
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colors.background
        ) {
            when (val state = detailState) {
                is DealDetailUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = RedPrimary)
                    }
                }

                is DealDetailUiState.Success -> {
                    val deal = state.deal
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        DealImage(imageUrl = deal.imageUrl)

                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = deal.title,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkText
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            deal.regularPrice?.let {
                                it.displayString?.let { it1 ->
                                    Text(
                                        text = it1,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = RedPrimary
                                    )
                                }

                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            DealInfoSection(
                                title = "Availability",
                                content = deal.availability ?: ""
                            )
                            DealInfoSection(title = "Aisle", content = deal.aisle)
                            DealInfoSection(title = "Fulfillment", content = deal.fulfillment ?: "")

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Description",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkText
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = deal.description,
                                fontSize = 14.sp,
                                color = GrayText,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }

                is DealDetailUiState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.message,
                            fontSize = 16.sp,
                            color = GrayText,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DealImage(imageUrl: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .background(Color.White)
    ) {
        GlideImage(
            model = imageUrl,
            contentDescription = "Deal Image",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun DealInfoSection(title: String, content: String) {
    if (content.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = content,
                fontSize = 14.sp,
                color = GrayText
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(LightGray)
            )
        }
    }
}