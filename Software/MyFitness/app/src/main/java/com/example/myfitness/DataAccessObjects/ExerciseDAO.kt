package com.example.myfitness.DataAccessObjects

import android.R
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.firebase.firestore.FirebaseFirestore
import model.Exercise


object ExerciseDAO {

    fun addExercise(exercise: Exercise, db: FirebaseFirestore) {
        val exercisesRef = db.collection("exercises")
        exercisesRef.add(exercise)
            .addOnSuccessListener {
                Log.d("ExerciseDAO", "Vježba dodana!")
            }
            .addOnFailureListener {
                Log.e("ExerciseDAO", "Greška prilikom dodavanja vježbe!")
            }
    }

    fun fillSpinner(spinner: Spinner) {
        spinner.adapter = ArrayAdapter(
            spinner.context,
            R.layout.simple_spinner_item,
            BODY_PARTS
        )
    }

    fun getAllExercises(bodyPart: String, db: FirebaseFirestore, callback: (List<Exercise>) -> Unit) {
        val exercisesRef = db.collection("exercises")
        exercisesRef
            .whereEqualTo("bodyPart", bodyPart)
            .limit(5) //TODO dodati stranicenje
            .get()
            .addOnSuccessListener { querySnapshot ->
                val exercises = querySnapshot.toObjects(Exercise::class.java)
                callback(exercises)
            }
            .addOnFailureListener {
                Log.e("ExerciseDAO", "Neuspješno dodavanje vježbi")
            }
    }

    private val BODY_PARTS = listOf("Leđa", "Prsa", "Noge", "Ramena", "Bicepsi", "Tricepsi")
}
