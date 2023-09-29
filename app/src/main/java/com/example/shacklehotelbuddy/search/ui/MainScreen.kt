package com.example.shacklehotelbuddy.search.ui

import android.widget.NumberPicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shacklehotelbuddy.R
import com.example.shacklehotelbuddy.search.MainViewModel
import com.example.shacklehotelbuddy.search.state.SearchViewState
import com.example.shacklehotelbuddy.ui.theme.ShackleHotelBuddyTheme
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@Composable
fun MainScreen(
    onSearchClicked: (
        checkInDateInMillis: Long,
        checkOutDateInMillis: Long,
        adults: Int,
        children: Int,
    ) -> Unit,
) {
    val viewModel = hiltViewModel<MainViewModel>()
    val state by viewModel.state.collectAsState()
    MainScreen(
        viewState = state,
        onCheckInPicked = viewModel::onCheckInPicked,
        onCheckOutPicked = viewModel::onCheckOutPicked,
        onAdultChanged = viewModel::onAdultChanged,
        onChildrenChanged = viewModel::onChildrenChanged,
        onSearchClicked = {
            viewModel.onSearchClicked()
            with(viewModel.currentState()) {
                onSearchClicked(checkInDateInMillis, checkOutDateInMillis, adults, children)
            }
        }
    )
}

@Composable
private fun MainScreen(
    viewState: SearchViewState,
    onCheckInPicked: (Long?) -> Unit,
    onCheckOutPicked: (Long?) -> Unit,
    onAdultChanged: (Int) -> Unit,
    onChildrenChanged: (Int) -> Unit,
    onSearchClicked: () -> Unit,
) {
    var showCheckInPicker by remember {
        mutableStateOf(false)
    }
    var showCheckOutPicker by remember {
        mutableStateOf(false)
    }
    var showAdultPicker by remember {
        mutableStateOf(false)
    }
    var showChildrenPicker by remember {
        mutableStateOf(false)
    }

    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)

    val checkInPickerState = rememberDatePickerState(
        initialSelectedDateMillis = sdf.parse(viewState.checkInDate)?.time,
        initialDisplayMode = DisplayMode.Picker,
        selectableDates = object: SelectableDates {
            // block dates that before the current date
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                return utcTimeMillis >= calendar.timeInMillis
            }

            // block years that before the current year
            override fun isSelectableYear(year: Int): Boolean {
                val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                val currentYear = calendar[Calendar.YEAR]
                return year >= currentYear
            }
        }
    )

    val checkOutPickerState = rememberDatePickerState(
        initialSelectedDateMillis = sdf.parse(viewState.checkInDate)?.time,
        initialDisplayMode = DisplayMode.Picker,
        selectableDates = object: SelectableDates {
            // block dates that before the check in date
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return if (checkInPickerState.selectedDateMillis == null) {
                    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                    utcTimeMillis >= calendar.timeInMillis
                } else {
                    utcTimeMillis >= checkInPickerState.selectedDateMillis!!
                }
            }

            // block years that before the current year
            override fun isSelectableYear(year: Int): Boolean {
                val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                val currentYear = calendar[Calendar.YEAR]
                return year >= currentYear
            }
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillWidth
            ),
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                modifier = Modifier
                    .padding(top = 142.dp),
                text = stringResource(id = R.string.title_main_page),
                style = ShackleHotelBuddyTheme.typography.h3,
                color = ShackleHotelBuddyTheme.colors.white
            )

            Box(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .background(
                        color = ShackleHotelBuddyTheme.colors.white,
                        shape = RoundedCornerShape(16.dp)
                    )

            ) {
                Column {
                    SearchDataRow(
                        iconRes = R.drawable.event_upcoming,
                        titleRes = R.string.title_check_in_date,
                        data = viewState.checkInDate.toString(),
                        onClick = { showCheckInPicker = true }
                    )
                    HorizontalDivider()
                    SearchDataRow(
                        iconRes = R.drawable.event_available,
                        titleRes = R.string.title_check_out_date,
                        data = viewState.checkOutDate.toString(),
                        onClick = { showCheckOutPicker = true }

                    )
                    HorizontalDivider()
                    SearchDataRow(
                        iconRes = R.drawable.person,
                        titleRes = R.string.title_adults,
                        data = viewState.adults.toString(),
                        onClick = { showAdultPicker = true }
                    )
                    HorizontalDivider()
                    SearchDataRow(
                        iconRes = R.drawable.supervisor_account,
                        titleRes = R.string.title_children,
                        data = viewState.children.toString(),
                        onClick = { showChildrenPicker = true }
                    )
                }

            }

            if (viewState.recentSearch != null) {
                Text(
                    modifier = Modifier.padding(top = 32.dp),
                    text = stringResource(id = R.string.title_recent_searches),
                    style = ShackleHotelBuddyTheme.typography.h6,
                    color = ShackleHotelBuddyTheme.colors.white
                )
                Box(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(
                            color = ShackleHotelBuddyTheme.colors.white,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    ProvideTextStyle(
                        value = TextStyle(
                            fontSize = 12.sp,
                            color = ShackleHotelBuddyTheme.colors.grayText,
                        ),
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.manage_history),
                                contentDescription = "recent search icon"
                            )
                            VerticalDivider(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .height(48.dp)
                            )
                            Text(text = "${viewState.recentSearch.checkInDate} - ${viewState.recentSearch.checkOutDate}")
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "${
                                    pluralStringResource(
                                        id = R.plurals.adult,
                                        count = viewState.adults
                                    )
                                }, ${
                                    pluralStringResource(
                                        id = R.plurals.children,
                                        count = viewState.children
                                    )
                                }"
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier
                    .padding(bottom = 40.dp)
                    .fillMaxWidth()
                    .background(
                        color = colorResource(id = R.color.teal),
                        shape = RoundedCornerShape(20.dp)
                    )

                    .height(60.dp),
                colors = ButtonDefaults.textButtonColors(),
                onClick = onSearchClicked
            ) {
                Text(
                    fontSize = 18.sp,
                    color = ShackleHotelBuddyTheme.colors.white,
                    fontWeight = FontWeight(450),
                    text = stringResource(id = R.string.action_search)
                )
            }
        }
    }

    if (showCheckInPicker) {
        SimpleDatePicker(
            datePickerState = checkInPickerState,
            onDismissRequest = {
                showCheckInPicker = false
            },
            onConfirm = { date ->
                onCheckInPicked(date)
                showCheckInPicker = false
            },
            onDismiss = {
                showCheckInPicker = false
            }
        )
    }

    if (showCheckOutPicker) {
        SimpleDatePicker(
            datePickerState = checkOutPickerState,
            onDismissRequest = {
                showCheckOutPicker = false
            },
            onConfirm = { date ->
                onCheckOutPicked(date)
                showCheckOutPicker = false
            },
            onDismiss = {
                showCheckOutPicker = false
            }
        )
    }

    if (showAdultPicker) {
        SimpleNumberPicker(
            value = viewState.adults,
            onValueChange = onAdultChanged,
            onDismissRequest = { showAdultPicker = false }
        )
    }

    if (showChildrenPicker) {
        SimpleNumberPicker(
            value = viewState.children,
            onValueChange = onChildrenChanged,
            onDismissRequest = { showChildrenPicker = false }
        )
    }
}

@Composable
private fun SimpleDatePicker(
    datePickerState: DatePickerState,
    onDismissRequest: () -> Unit,
    onConfirm: (Long?) -> Unit,
    onDismiss: () -> Unit,
) {
    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = {
                onConfirm(datePickerState.selectedDateMillis)
            }) {
                Text(text = stringResource(id = R.string.action_confirm))
            }

        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.action_cancel))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
private fun SimpleNumberPicker(
    modifier: Modifier = Modifier,
    value: Int,
    min: Int = 0,
    max: Int = 100,
    onValueChange: (Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier.wrapContentHeight(),
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .requiredWidth(356.dp)
                .heightIn(max = 568.dp),
            shape = RoundedCornerShape(28.0.dp),
            tonalElevation = 6.dp,
        ) {
            AndroidView(
                modifier = Modifier.fillMaxWidth(),
                factory = { context ->
                    NumberPicker(context).apply {
                        setOnValueChangedListener { _, _, newVal ->
                            onValueChange(newVal)
                        }
                        minValue = min
                        maxValue = max
                        this.value = value
                    }
                },
                update = {}
            )
        }
    }
}

@Composable
private fun SearchDataRow(
    modifier: Modifier = Modifier,
    iconRes: Int,
    iconDescription: String? = null,
    titleRes: Int,
    data: String,
    onClick: () -> Unit,
) {
    ProvideTextStyle(
        value = TextStyle(
            color = ShackleHotelBuddyTheme.colors.grayText,
            fontSize = 14.sp,
            fontWeight = FontWeight(450),
            lineHeight = 20.sp
        )
    ) {
        ConstraintLayout(
            modifier = modifier
                .height(52.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val (imageRef, textRef, dividerRef, textFieldRef) = createRefs()
            val midGuide = createGuidelineFromStart(0.5f)
            Image(
                modifier = Modifier
                    .constrainAs(imageRef) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.fillToConstraints
                    }
                    .padding(start = 16.dp),
                painter = painterResource(id = iconRes),
                contentDescription = iconDescription
            )
            Text(
                modifier = Modifier
                    .constrainAs(textRef) {
                        start.linkTo(imageRef.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(start = 8.dp),
                text = stringResource(id = titleRes)
            )
            VerticalDivider(Modifier.constrainAs(dividerRef) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
            Text(
                modifier = Modifier
                    .constrainAs(textFieldRef) {
                        start.linkTo(midGuide)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(start = 16.dp)
                    .clickable(enabled = true, onClick = onClick),
                text = data
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    ShackleHotelBuddyTheme {
        MainScreen(
            viewState = SearchViewState(),
            onCheckInPicked = {},
            onCheckOutPicked = {},
            onAdultChanged = {},
            onChildrenChanged = {},
            onSearchClicked = {}
        )
    }
}