package com.example.shacklehotelbuddy.search.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shacklehotelbuddy.R
import com.example.shacklehotelbuddy.search.ResultScreenViewModel
import com.example.shacklehotelbuddy.search.state.SearchResultViewState
import com.example.shacklehotelbuddy.ui.theme.ShackleHotelBuddyTheme

@Composable
fun ResultScreen(
    onBack: () -> Unit,
) {
    val viewModel = hiltViewModel<ResultScreenViewModel>()
    val state by viewModel.state.collectAsState()
    ResultScreen(
        viewState = state,
        onRetry = viewModel::retry,
        onBack = onBack
    )
}

@Composable
private fun ResultScreen(
    viewState: SearchResultViewState,
    onRetry: () -> Unit,
    onBack: () -> Unit,
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            ) {
                val (iconRef, titleRef) = createRefs()
                IconButton(modifier = Modifier.constrainAs(iconRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }, onClick = onBack) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "back button"
                    )
                }
                Text(
                    modifier = Modifier.constrainAs(titleRef) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    text = stringResource(id = R.string.title_search_results),
                    style = ShackleHotelBuddyTheme.typography.h5
                )
            }
            Box(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (viewState.results.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight()
                    ) {
                        itemsIndexed(items = viewState.results) { index, item ->
                            Spacer(modifier = Modifier.height(32.dp))
                            ResultItem(
                                imageUrl = item.imageUrl,
                                hotelName = item.name,
                                hotelRating = item.ratings,
                                location = item.neighborhood,
                                pricePerNight = item.pricePerNight
                            )
                        }
                    }
                }
                if (viewState.error != null) {
                    Column(
                        Modifier.padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = viewState.error.localizedMessage!!)
                        TextButton(onClick = onRetry) {
                            Text(text = stringResource(id = R.string.action_retry))
                        }
                    }
                }

                if (viewState.isLoading) {
                    Column(
                        Modifier.padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = stringResource(id = R.string.search_loading))
                        LinearProgressIndicator(
                            modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 8.dp)
                        )
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreviewLoading() {
    ShackleHotelBuddyTheme {
        ResultScreen(
            viewState = SearchResultViewState(isLoading = true),
            onRetry = {},
            onBack = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreviewError() {
    ShackleHotelBuddyTheme {
        ResultScreen(
            viewState = SearchResultViewState(error = Throwable("Some Error")),
            onRetry = {},
            onBack = {}
        )
    }
}