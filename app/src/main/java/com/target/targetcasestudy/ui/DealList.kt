package com.target.targetcasestudy.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.target.targetcasestudy.data.Deal
import com.target.targetcasestudy.viewmodel.DealViewModel
import com.target.targetcasestudy.viewmodel.DealsUiState

@Composable
fun DealList(
    viewModel: DealViewModel,
    onDealClick: (String) -> Unit
) {
    val dealsState by viewModel.dealsState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        when (val state = dealsState) {
            is DealsUiState.Loading -> {
                LoadingView()
            }
            is DealsUiState.Success -> {
                Log.i("upagyya", "DealListScreen ")
                DealsList(deals = state.deals, onDealClick = onDealClick)
            }
            is DealsUiState.Empty -> {
                EmptyView()
            }
            is DealsUiState.Error -> {
                ErrorView(message = state.message) {
                    viewModel.loadDeals()
                }
            }
        }
    }
}

@Composable
fun DealsList(
    deals: List<Deal>,
    onDealClick: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(deals) { deal ->
            DealListItem(deal = deal, onDealClick = onDealClick)
            Divider(color = Color.LightGray, thickness = 1.dp)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DealListItem(
    deal: Deal,
    onDealClick: (String) -> Unit
) {
    Log.i("upagyya", "DealList $deal")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onDealClick(deal.id.toString()) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            model = deal.imageUrl,
            contentDescription = deal.title,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = deal.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = DarkText,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            deal.regularPrice?.let {
                it.displayString?.let{ it1->
                    Text(
                        text = it1,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = RedPrimary
                    )
                }

            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = deal.aisle,
                fontSize = 14.sp,
                color = GrayText
            )
        }
    }
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = RedPrimary)
    }
}

@Composable
fun EmptyView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No deals available",
            fontSize = 18.sp,
            color = GrayText
        )
    }
}

@Composable
fun ErrorView(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Something went wrong",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = DarkText
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = message,
            fontSize = 16.sp,
            color = GrayText
        )

        Spacer(modifier = Modifier.height(16.dp))

        androidx.compose.material.Button(
            onClick = onRetry,
            colors = androidx.compose.material.ButtonDefaults.buttonColors(backgroundColor = RedPrimary)
        ) {
            Text(
                text = "Retry",
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}