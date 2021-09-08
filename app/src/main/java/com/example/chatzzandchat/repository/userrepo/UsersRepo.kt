package com.example.chatzzandchat.repository.userrepo

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.example.chatzzandchat.models.UsersList
import com.example.chatzzandchat.models.RequestName
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit

class UsersRepo {
    val auth = FirebaseAuth.getInstance()
    lateinit var storedVerificationId: String

    fun sendingVerificationCode(
        phoneNumber: String,
        context: Context
    ) {

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                signInWithPhoneAuthCredential(credential, context, null)
                //         Toast.makeText(context, "U are Register", Toast.LENGTH_SHORT).show()
            }

            override fun onVerificationFailed(e: FirebaseException) {

                Log.w(ContentValues.TAG, "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(context, "Enter correct Otp", Toast.LENGTH_SHORT).show()
                } else if (e is FirebaseTooManyRequestsException) {
                    Toast.makeText(context, "Try After Some Time", Toast.LENGTH_SHORT).show()
                }

                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                // Log.d(TAG, "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId

            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(context as Activity)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

     fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        context: Context,
        completeListner: OnCompleteListener<AuthResult>?
    ) {
        val taskSignIn = auth.signInWithCredential(credential)
        completeListner?.let { taskSignIn.addOnCompleteListener(context as Activity, it) }
    }

    fun signInWithPhoneAuthCredential(otpString: String, context: Context, completeListner: OnCompleteListener<AuthResult>?) {
        val credential =
            PhoneAuthProvider.getCredential(storedVerificationId, otpString)
        signInWithPhoneAuthCredential(credential, context, completeListner)
    }

    fun sendUserNametoRealtime(userName: String) {
        val database = auth.uid?.let { Firebase.database.getReference(it) }
        val requestName = RequestName(userName, auth.uid)
        database?.setValue(requestName)

    }

    fun sendImageToStorage(
        context: Context,
        imagePathUri: Uri,
        imageUrlSuccessListener: OnSuccessListener<Uri>
    ) {
        val storageReference = FirebaseStorage.getInstance()
        val imageRef = auth.uid?.let {
            storageReference.getReference().child("Images").child(it).child("Profile Pic")
        }
        var bitmap: Bitmap? = null
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imagePathUri)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val byteArrayOutputStream = ByteArrayOutputStream()

        bitmap?.let { bitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream) }
        val data = byteArrayOutputStream.toByteArray()
        val uploadTask = imageRef?.let { imageRef.putBytes(data) }

        uploadTask!!.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener(imageUrlSuccessListener)

        }
    }

    fun sendDataToCloudFireStore( userName: String, imageUriToken: String) {
        val firebaseFireStore = FirebaseFirestore.getInstance()
        val documentReference = firebaseFireStore.collection("Users").document(auth.uid!!)
        val userData: HashMap<String, Any> = HashMap()
        userData.put("Name", userName)
        userData.put("ImgUri", imageUriToken)
        userData.put("Id", auth.uid!!)
        userData.put("Status", "Online")
        documentReference.set(userData)

    }

    fun allUserList(): FirestoreRecyclerOptions<UsersList> {
        val firebaseFireStore = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val query = firebaseFireStore.collection("Users").whereNotEqualTo("Id", auth.uid)
        val allUserName = FirestoreRecyclerOptions.Builder<UsersList>().setQuery(
            query,
            UsersList::class.java
        ).build()
        return allUserName
    }

}