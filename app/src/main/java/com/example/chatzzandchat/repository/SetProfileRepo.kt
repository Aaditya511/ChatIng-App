package com.example.chatzzandchat.all_activites.repository

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.example.chatzzandchat.dataclasses.RealtimeName
import java.io.ByteArrayOutputStream

class SetProfileRepo {
    val auth = FirebaseAuth.getInstance()

    fun sendUserNametoRealtime(userName: String, context: Context) {
        val database = auth.uid?.let { Firebase.database.getReference(it) }
        val realtimeName = RealtimeName(userName, auth.uid)
        database?.setValue(realtimeName)
        Toast.makeText(context, "Name Is Register", Toast.LENGTH_SHORT).show()

    }

    fun sendImageToStorage(context: Context, imagePathUri: Uri, imageUrlSuccessListener: OnSuccessListener<Uri>) {
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
                Toast.makeText(context, "Pic Uploaded and u have link of it", Toast.LENGTH_SHORT).show()

        }
    }

    fun sendDataToCloudFireStore(context: Context,userName: String,imageUriToken:String){
        val firebaseFireStore = FirebaseFirestore.getInstance()
        val documentReference = firebaseFireStore.collection("Users").document(auth.uid!!)
        val userData:HashMap<String,Any> = HashMap()
        userData.put("Name",userName)
        userData.put("ImgUri",imageUriToken)
        userData.put("Id", auth.uid!!)
        userData.put("Status","Online")
        documentReference.set(userData)
            .addOnSuccessListener {
            Toast.makeText(context, "Data uploaded", Toast.LENGTH_SHORT).show()
        }
    }

}




