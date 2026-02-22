package com.example.data.auth

import com.example.data.CurrentUserProvider
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FirebaseAuthProvider @Inject constructor() : CurrentUserProvider {

    override val currentUserId: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid
}