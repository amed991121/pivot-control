package com.example.controlpivot.ui.screen.pivotcontrol

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.ColumnInfo
import com.example.controlpivot.R
import com.example.controlpivot.data.local.model.PivotControlEntity
import com.example.controlpivot.data.local.model.SectorControl
import com.example.controlpivot.ui.component.AddTagEvent
import com.example.controlpivot.ui.component.BottomSheetIrrigation
import com.example.controlpivot.ui.component.PivotSpinner
import com.example.controlpivot.ui.component.QuadrantIrrigation
import com.example.controlpivot.ui.theme.GreenHigh
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PivotControlScreen(
    state: ControlState,
    onEvent: (ControlEvent) -> Unit,
) {

    var selectedIdPivot by remember {
        mutableIntStateOf(state.selectedIdPivot)
    }

    val scrollState = rememberScrollState()
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var skipPartiallyExpanded by remember { mutableStateOf(true) }
    var edgeToEdgeEnabled by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )
    val scope = rememberCoroutineScope()
    var isRunning by remember { mutableStateOf(state.controls.isRunning) }
    var progress by remember { mutableFloatStateOf(state.controls.progress) }
    var motorVelocity by remember { mutableFloatStateOf(0.3f) }
    var clickedSector by remember { mutableIntStateOf(0) }
    var stateBomb by remember { mutableStateOf(state.controls.stateBomb) }
    var wayToPump by remember { mutableStateOf(state.controls.wayToPump) }
    var turnSense by remember { mutableStateOf(state.controls.turnSense) }
    var sectorStateList by remember {
        mutableStateOf(
            mutableListOf(
                state.controls.sectorList[0].irrigateState,
                state.controls.sectorList[1].irrigateState,
                state.controls.sectorList[2].irrigateState,
                state.controls.sectorList[3].irrigateState
            )
        )
    }
    var isSelectedControl by remember { mutableStateOf(false) }

    LaunchedEffect(isSelectedControl) {
        isRunning = state.controls.isRunning
        progress = state.controls.progress
        motorVelocity = 0.3f
        clickedSector = 0
        stateBomb = state.controls.stateBomb
        wayToPump = state.controls.wayToPump
        turnSense = state.controls.turnSense
        sectorStateList = mutableListOf(
            state.controls.sectorList[0].irrigateState,
            state.controls.sectorList[1].irrigateState,
            state.controls.sectorList[2].irrigateState,
            state.controls.sectorList[3].irrigateState
        )
        isSelectedControl = false
    }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(100)
            if (!turnSense) progress += 0.3f
            else progress -= 0.3f

        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(alpha = 0.3f))
            .padding(bottom = 50.dp)
    ) {
        if (openBottomSheet) {
            val windowInsets = if (edgeToEdgeEnabled)
                WindowInsets(0) else BottomSheetDefaults.windowInsets
            ModalBottomSheet(
                modifier = Modifier.height(500.dp),
                onDismissRequest = { openBottomSheet = false },
                sheetState = bottomSheetState,
                windowInsets = windowInsets,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                contentColor = Color.Black,
                containerColor = Color.White,
                tonalElevation = 8.dp,
            ) {
                BottomSheetIrrigation(
                    checkIrrigate = state.controls.sectorList[clickedSector - 1].irrigateState,
                    sector = clickedSector,
                    dosage = state.controls.sectorList[clickedSector - 1].dosage,
                    onEvent = { event ->
                        when (event) {
                            is AddTagEvent.Close -> scope.launch {
                                bottomSheetState.hide()
                            }.invokeOnCompletion {
                                if (bottomSheetState.isVisible) openBottomSheet = false
                            }

                            is AddTagEvent.Save -> {

                                if (state.idPivotList.isNotEmpty()) {
                                    onEvent(
                                        ControlEvent.SaveControls(
                                            controls = PivotControlEntity(
                                                id = state.controls.id,
                                                idPivot = state.controls.idPivot,
                                                progress = progress,
                                                isRunning = isRunning,
                                                stateBomb = stateBomb,
                                                wayToPump = wayToPump,
                                                turnSense = turnSense
                                            )
                                        )
                                    )
                                    onEvent(
                                        ControlEvent.SaveSectors(
                                            sectors = SectorControl(
                                                id = state.controls.sectorList[clickedSector - 1].id,
                                                sector_id = clickedSector,
                                                irrigateState = event.checkIrrigate,
                                                dosage = event.dosage,
                                                motorVelocity = 0,
                                                sector_control_id = state.controls.sectorList[clickedSector - 1].sector_control_id
                                            )
                                        )
                                    )
                                    sectorStateList[clickedSector - 1] = event.checkIrrigate
                                    if (event.checkIrrigate) {
                                        onEvent(ControlEvent.ShowMessage(R.string.check_irrigate))
                                        if (bottomSheetState.isVisible)
                                            openBottomSheet = false

                                    } else {
                                        onEvent(ControlEvent.ShowMessage(R.string.uncheck_irrigate))
                                        if (bottomSheetState.isVisible)
                                            openBottomSheet = false
                                    }
                                } else {
                                    onEvent(ControlEvent.ShowMessage(R.string.pivot_not_selected))
                                }
                            }

                        }
                    })
            }

        }
    }
    Column(
        modifier = Modifier
            .padding(bottom = 80.dp)
            .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        PivotSpinner(
            optionList = state.idPivotList,
            selectedOption = state.idPivotList.find {
                selectedIdPivot == it.idPivot
            }?.pivotName
                ?: "",
            onSelected = {
                selectedIdPivot = it
                state.selectedIdPivot = it
            },
            loading = {
                onEvent(
                    ControlEvent.SelectControlByIdPivot(
                        selectedIdPivot
                    )
                )
                isSelectedControl = true
            }
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Riego Sectorizado",
            fontSize = 25.sp,
        )

        QuadrantIrrigation(
            isRunning = isRunning,
            onClick = {
                clickedSector = it
                openBottomSheet = !openBottomSheet
            },
            pauseIrrigation = {
                if (state.networkStatus) {
                    if (stateBomb) {
                        isRunning = !isRunning
                        onEvent(
                            ControlEvent.SaveControls(
                                controls = PivotControlEntity(
                                    id = state.controls.id,
                                    idPivot = state.controls.idPivot,
                                    progress = progress,
                                    isRunning = isRunning,
                                    stateBomb = stateBomb,
                                    wayToPump = wayToPump,
                                    turnSense = turnSense
                                )
                            )
                        )
                    } else onEvent(ControlEvent.ShowMessage(R.string.bomb_off))
                } else onEvent(ControlEvent.ShowMessage(R.string.internet_error))

            },
            progress = progress,
            sectorStateList = sectorStateList as List<Boolean>
        )

        HorizontalDivider(
            modifier = Modifier
                .height(5.dp)
                .padding(horizontal = 15.dp),
            color = Color.Black
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Sistema de Bombeo",
                fontSize = 25.sp,
            )
            Spacer(modifier = Modifier.width(10.dp))

            Switch(
                checked = stateBomb,
                onCheckedChange = {
                    stateBomb = !stateBomb
                    if (state.idPivotList.isNotEmpty()) {
                        onEvent(
                            ControlEvent.SaveControls(
                                controls = PivotControlEntity(
                                    id = state.controls.id,
                                    idPivot = state.controls.idPivot,
                                    progress = progress,
                                    isRunning = isRunning,
                                    stateBomb = stateBomb,
                                    wayToPump = wayToPump,
                                    turnSense = turnSense
                                )
                            )
                        )
                    }
                })
        }
        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.wrapContentSize()
                ) {

                    Text(
                        text = "Manual",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(6.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))
                    Switch(
                        checked = wayToPump,
                        onCheckedChange = {
                            wayToPump = !wayToPump
                            if (state.idPivotList.isNotEmpty()) {
                                onEvent(
                                    ControlEvent.SaveControls(
                                        controls = PivotControlEntity(
                                            id = state.controls.id,
                                            idPivot = state.controls.idPivot,
                                            progress = progress,
                                            isRunning = isRunning,
                                            stateBomb = stateBomb,
                                            wayToPump = wayToPump,
                                            turnSense = turnSense
                                        )
                                    )
                                )
                            }
                        },
                        modifier = Modifier
                            .padding(8.dp),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = GreenHigh,
                            checkedIconColor = GreenHigh,
                            checkedTrackColor = Color.White,
                            checkedBorderColor = Color.DarkGray.copy(alpha = 0.6f),
                            uncheckedTrackColor = Color.White,
                            uncheckedBorderColor = Color.DarkGray.copy(alpha = 0.6f),
                            uncheckedThumbColor = GreenHigh,
                            uncheckedIconColor = GreenHigh,
                        ),
                        thumbContent = {
                            Box(
                                modifier = Modifier.background(
                                    color = Color.Transparent,
                                    shape = RoundedCornerShape(16.dp)
                                )
                            )
                        }
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Automático",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(6.dp)
                    )

                }

                Spacer(modifier = Modifier.height(17.dp))

                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.wrapContentSize()
                ) {

                    Text(
                        text = "Izquierda",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(6.dp)
                    )

                    Switch(
                        checked = turnSense,
                        onCheckedChange = {
                            turnSense = !turnSense
                            if (state.idPivotList.isNotEmpty()) {
                                onEvent(
                                    ControlEvent.SaveControls(
                                        controls = PivotControlEntity(
                                            id = state.controls.id,
                                            idPivot = state.controls.idPivot,
                                            progress = progress,
                                            isRunning = isRunning,
                                            stateBomb = stateBomb,
                                            wayToPump = wayToPump,
                                            turnSense = turnSense
                                        )
                                    )
                                )
                            }
                        },
                        modifier = Modifier
                            .padding(8.dp),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = GreenHigh,
                            checkedIconColor = GreenHigh,
                            checkedTrackColor = Color.White,
                            checkedBorderColor = Color.DarkGray.copy(alpha = 0.6f),
                            uncheckedTrackColor = Color.White,
                            uncheckedBorderColor = Color.DarkGray.copy(alpha = 0.6f),
                            uncheckedThumbColor = GreenHigh,
                            uncheckedIconColor = GreenHigh,
                        ),
                        thumbContent = {
                            Box(
                                modifier = Modifier.background(
                                    color = Color.Transparent,
                                    shape = RoundedCornerShape(16.dp)
                                )
                            )
                        }
                    )

                    Text(
                        text = "Derecha",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(6.dp)
                    )

                }

            }

        }
        Spacer(modifier = Modifier.height(90.dp))
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PivotControlPreview() {
    PivotControlScreen(state = ControlState(), onEvent = {})
}

