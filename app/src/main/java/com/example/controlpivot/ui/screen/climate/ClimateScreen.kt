package com.example.controlpivot.ui.screen.climate

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.controlpivot.R
import com.example.controlpivot.ui.component.PivotSpinner

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ClimateScreen(
    state: ClimateState,
    onEvent: (ClimateEvent) -> Unit,
    pullRefreshState: PullRefreshState,
) {

    val value = state.currentClimate
    val items = listOf(
        value?.atmoPressure ?: "",
        value?.RH ?: "",
        value?.rainy ?: "",
        value?.solarRadiation ?: "",
        value?.temp ?: "",
        value?.temp ?: "",
        value?.windSpeed ?: "",
        value?.cropCoefficient ?: ""
    )

    val titles = listOf(
        stringResource(id = R.string.pressure),
        stringResource(id = R.string.rh),
        stringResource(id = R.string.rainy),
        stringResource(id = R.string.solar_radiation),
        stringResource(id = R.string.temp_min),
        stringResource(id = R.string.temp_max),
        stringResource(id = R.string.wind),
        stringResource(id = R.string.etc),
    )

    val suffix = listOf(
        stringResource(id = R.string.kpa),
        stringResource(id = R.string.percent),
        stringResource(id = R.string.rainy_unit),
        stringResource(id = R.string.solar_radiation_unit),
        stringResource(id = R.string.temp_unit),
        stringResource(id = R.string.temp_unit),
        stringResource(id = R.string.wind_unit),
        stringResource(id = R.string.rainy_unit),
    )

    val images = listOf(
        R.drawable.pressure,
        R.drawable.humidity,
        R.drawable.rainy,
        R.drawable.radiation,
        R.drawable.max_temp,
        R.drawable.low_temp,
        R.drawable.wind,
        R.drawable.plant,
    )

    var selectedIdPivot by remember {
        mutableIntStateOf(state.selectedIdPivot)
    }

    Scaffold(
        modifier = Modifier
            ,
    ) {

        Box(
            modifier = Modifier
                .pullRefresh(pullRefreshState)
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.background2
                ),
                contentDescription = "Background",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 75.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                PivotSpinner(
                    optionList = state.idPivotList,
                    selectedOption = state.idPivotList.find { selectedIdPivot == it.idPivot }?.pivotName
                        ?: "",
                    onSelected = {
                        selectedIdPivot = it
                        state.selectedIdPivot = it
                    },
                    loading = {
                        onEvent(
                            ClimateEvent.SelectClimateByIdPivot(selectedIdPivot)
                        )
                    }
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(5.dp)
                ) {

                    Text(
                        modifier = Modifier
                            .padding(5.dp),
                        text = "Última actualización: ",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        modifier = Modifier
                            .padding(5.dp),
                        text = state.currentClimate?.timestamp ?: "",
                        fontSize = 12.sp,
                    )
                }

                LazyVerticalGrid(
                    modifier = Modifier
                        .padding(20.dp),
                    columns = GridCells.Fixed(2),
                    content = {
                        items(items.size) { i ->
                            ClimateItem(
                                title = titles[i],
                                value = items[i],
                                suffix = suffix[i],
                                image = images[i]
                            )
                        }
                    },
                    //contentPadding = PaddingValues(horizontal = 5.dp, vertical = 5.dp)
                )
            }
            PullRefreshIndicator(
                refreshing = state.isLoading,
                state = pullRefreshState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun ClimateItem(
    title: String,
    value: Any?,
    suffix: String,
    image: Int,
) {
    ElevatedCard(

        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .size(height = 140.dp, width = 100.dp),
    ) {

        Box(
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Image(
                    painter = painterResource(
                        id = image
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(45.dp)
                        .padding(4.dp)
                )
                Row {
                    Text(
                        text = value.toString(),
                        modifier = Modifier,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Thin
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Text(
                        text = suffix,
                        modifier = Modifier,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Thin
                    )
                }
            }
        }
    }
}



