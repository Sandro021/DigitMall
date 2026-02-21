package com.example.ui.theme.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.R
import com.example.ui.theme.Padding
import com.example.ui.theme.Radius


@Composable
fun IconTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String,
    @DrawableRes leadingIconRes: Int,
    contentDescription: String = labelText,
    placeholderText: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    visualTransformation: VisualTransformation? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Padding.padding20),
        label = { Text(labelText) },
        placeholder = {
            if (placeholderText != null) Text(placeholderText)
        },
        leadingIcon = {
            Icon(
                painter = painterResource(leadingIconRes),
                contentDescription = contentDescription
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        shape = Radius.radius16,
        visualTransformation = visualTransformation ?: VisualTransformation.None
    )
}



@Preview
@Composable
fun IconTextFieldPreview() {
    var email = ""
    IconTextField(
        value = email,
        onValueChange = { email = it },
        labelText = "Email",
        leadingIconRes = R.drawable.mail,
        placeholderText = "example@mail.com",
        keyboardType = KeyboardType.Email,
        imeAction = ImeAction.Next
    )

}