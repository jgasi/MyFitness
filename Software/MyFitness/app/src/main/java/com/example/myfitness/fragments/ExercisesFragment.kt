package com.example.myfitness.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfitness.AddExerciseFragment
import com.example.myfitness.DataAccessObjects.ExerciseDAO
import com.example.myfitness.R
import com.example.myfitness.helpers.AddDoneExerciseDialogHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.myfitness.adapters.ExerciseRecyclerViewAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.withContext
import model.Exercise


class ExercisesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val db = FirebaseFirestore.getInstance()
    private var exercises = mutableListOf<Exercise>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_exercises, container, false)

        val button = v.findViewById<Button>(R.id.addExerciseButton)
        button.setOnClickListener {

            val button = v.findViewById<Button>(R.id.addExerciseButton)
            button.setOnClickListener {
                val frgmntAddExercise = AddExerciseFragment()
                val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.mainlayout, frgmntAddExercise)
                transaction.commit()
            }
        }

            val btnAddDoneExercise = v.findViewById<Button>(R.id.addDoneExercise)


            btnAddDoneExercise.setOnClickListener {
                val addDoneExerciseDialog = showDialog()
                val addDoneExerciseDialogHelper =
                    AddDoneExerciseDialogHelper(addDoneExerciseDialog, requireContext())

                val scope = CoroutineScope(Dispatchers.Main)
                scope.launch {
                    addDoneExerciseDialogHelper.load()
                }

            }

            recyclerView = v.findViewById(R.id.exerciseRecyclerView)
            recyclerView.layoutManager = LinearLayoutManager(context)
            loadExercises()
            return v
        }



        private fun showDialog(): AlertDialog {
            val addDoneExerciseDialog = LayoutInflater
                .from(context)
                .inflate(R.layout.done_exercise_input_dialog, null)

            return AlertDialog.Builder(context)
                .setView(addDoneExerciseDialog)
                .show()

        }


        private fun loadExercises() {
            val scope = CoroutineScope(Dispatchers.Main)
            scope.launch {
                exercises = ExerciseDAO.getAllExercises()
                exercises.forEach( {
                    println("TU JE")
                    println(it.name)
                })
                withContext(Dispatchers.Main) {
                    val adapter = ExerciseRecyclerViewAdapter(exercises)
                    recyclerView.adapter = adapter
                    println(exercises)
                }

            }
        }


        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            val exerciseRecyclerView = view.findViewById<RecyclerView>(R.id.exerciseRecyclerView)
            exerciseRecyclerView.layoutManager = LinearLayoutManager(context)

            val adapter = ExerciseRecyclerViewAdapter(exercises)
            exerciseRecyclerView.adapter = adapter
        }


    }

