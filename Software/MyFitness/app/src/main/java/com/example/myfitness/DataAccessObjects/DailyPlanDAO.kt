package com.example.myfitness.DataAccessObjects

import com.example.myfitness.entities.DoneExercise
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object DailyPlanDAO {
    fun add(exercises: List<DoneExercise>, username: String) : Boolean {
        val db = Firebase.firestore
        val batch = db.batch()
        try {
            for (exercise in exercises) {
                val exerciseMap = hashMapOf(
                    "exerciseName" to exercise.exerciseName,
                    "weight" to exercise.weight,
                    "sets" to exercise.sets,
                    "reps" to exercise.reps,
                    "date" to exercise.date,
                )
                val ref = db.collection("dailyPlan").document(username).collection("savedDailyPlan").document()
                batch.set(ref, exerciseMap)
            }
            batch.commit()
            return true
        } catch (e: Exception) {
            return false
        }
    }
}