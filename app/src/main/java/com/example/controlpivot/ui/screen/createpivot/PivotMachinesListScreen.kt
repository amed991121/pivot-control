package com.example.controlpivot.ui.screen.createpivot

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.controlpivot.R
import com.example.controlpivot.ui.component.SearchBar
import com.example.controlpivot.ui.theme.GreenMedium

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PivotMachineListScreen(
    state: PivotMachineState,
    onEvent: (PivotMachineEvent) -> Unit,
    navEvent: (ScreensNavEvent) -> Unit,
    pullRefreshState: PullRefreshState,
) {
    val lazyListState = rememberLazyListState()

    Scaffold(
        modifier = Modifier,
        floatingActionButton = {
            AnimatedVisibility(
                lazyListState.canScrollForward || state.machines.isEmpty()
                        || (!lazyListState.canScrollForward && !lazyListState.canScrollBackward),
                enter = scaleIn(),
                exit = scaleOut(),
                modifier = Modifier
                    .padding(bottom = 75.dp)
                    .wrapContentSize()
            ) {
                FloatingActionButton(
                    onClick = {
                        onEvent(PivotMachineEvent.ResetDataMachine)
                        navEvent(ScreensNavEvent.ToNewPivotMachine)
                    },
                    shape = FloatingActionButtonDefaults.extendedFabShape,
                    containerColor = GreenMedium,
                    contentColor = Color.Black,
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add pivot machine"
                    )

                }
            }
        }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
                .padding(bottom = 75.dp),
        )
        {
            Column(
                modifier = Modifier
                    .padding(horizontal = 15.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color.White.copy(alpha = 0.2f))
                            .clickable { },

                        ) {
                        Icon(
                            painter = painterResource(id = R.drawable.task_list),
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                                .padding(2.dp),
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = stringResource(id = R.string.pivot_machines),
                        style = MaterialTheme.typography.h5.copy(fontFamily = FontFamily.SansSerif),
                        modifier = Modifier
                            .padding(start = 5.dp),
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                SearchBar(
                    hint = stringResource(id = R.string.search),
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Thin,
                        fontStyle = FontStyle.Italic
                    )
                ) {
                    onEvent(PivotMachineEvent.SearchMachine(it))
                }
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {

                    items(state.machines) { machine ->
                        var stateHeight by remember {
                            mutableStateOf(true)
                        }
                        val tooltipState = rememberTooltipState()
                        val scope = rememberCoroutineScope()

                        PivotMachineItem(
                            machine = machine,
                            onDeleteClick = {
                                onEvent(
                                    PivotMachineEvent.DeleteMachine(machine.id)
                                )
                            },
                            onClick = {
                                onEvent(
                                    PivotMachineEvent.SelectMachine(machine.id)
                                )
                                navEvent(ScreensNavEvent.ToNewPivotMachine)
                            },
                            heightBox = !stateHeight,
                            onExpanded = { stateHeight = !stateHeight },
                            tooltipState = tooltipState,
                            scope = scope
                        )

                    }

                }

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