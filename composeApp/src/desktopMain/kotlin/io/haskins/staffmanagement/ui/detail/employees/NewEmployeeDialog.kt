package io.haskins.staffmanagement.ui.detail.employees

import androidx.compose.runtime.Composable


@Composable
fun newEmployeeDialog(value: String, setShowDialog: (Boolean) -> Unit, setValue: (String) -> Unit) {
//
//    val txtFieldError = remember { mutableStateOf("") }
//    val txtField = remember { mutableStateOf(value) }
//
//    Dialog(onDismissRequest = { setShowDialog(false) } ) {
//        Surface(
//            shape = RoundedCornerShape(16.dp),
//            color = Color.White
//        ) {
//            Box(
//                contentAlignment = Alignment.Center
//            ) {
//                Column(modifier = Modifier.padding(20.dp)) {
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(
//                            text = "Set value",
//                            style = TextStyle(
//                                fontSize = 24.sp,
//                                fontFamily = FontFamily.Default,
//                                fontWeight = FontWeight.Bold
//                            )
//                        )
//                        Icon(
//                            imageVector = Icons.Filled.Done,
//                            contentDescription = "",
//                            modifier = Modifier
//                                .width(30.dp)
//                                .height(30.dp)
//                                .clickable { setShowDialog(false) }
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.height(20.dp))
//
//                    TextField(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .border(
//                                BorderStroke(
//                                    width = 2.dp,
//                                    color = Color.Gray
//                                ),
//                                shape = RoundedCornerShape(50)
//                            ),
//                        leadingIcon = {
//                            Icon(
//                                imageVector = Icons.Filled.Call,
//                                contentDescription = "",
//                                modifier = Modifier
//                                    .width(20.dp)
//                                    .height(20.dp)
//                            )
//                        },
//                        placeholder = { Text(text = "Enter value") },
//                        value = txtField.value,
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                        onValueChange = {
//                            txtField.value = it.take(10)
//                        })
//
//                    Spacer(modifier = Modifier.height(20.dp))
//
//                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
//                        Button(
//                            onClick = {
//                                if (txtField.value.isEmpty()) {
//                                    txtFieldError.value = "Field can not be empty"
//                                    return@Button
//                                }
//                                setValue(txtField.value)
//                                setShowDialog(false)
//                            },
//                            shape = RoundedCornerShape(50.dp),
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(50.dp)
//                        ) {
//                            Text(text = "Done")
//                        }
//                    }
//                }
//            }
//        }
//    }
}