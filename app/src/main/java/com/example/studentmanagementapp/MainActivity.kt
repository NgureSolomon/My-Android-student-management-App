package com.example.studentmanagementapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val students = mutableListOf<Student>()
    private lateinit var adapter: StudentAdapter
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etName = findViewById<EditText>(R.id.etName)
        val etCourse = findViewById<EditText>(R.id.etCourse)
        val etRegNumber = findViewById<EditText>(R.id.etRegNumber)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val rvStudents = findViewById<RecyclerView>(R.id.rvStudents)

        // Initialize Room database
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "student-db"
        ).build()

        // Setup RecyclerView
        adapter = StudentAdapter(students)
        rvStudents.layoutManager = LinearLayoutManager(this)
        rvStudents.adapter = adapter

        // Load existing students
        lifecycleScope.launch {
            val savedStudents = db.studentDao().getAllStudents()
            students.addAll(savedStudents)
            adapter.notifyDataSetChanged()
        }

        // Add new student
        btnSubmit.setOnClickListener {
            val name = etName.text.toString()
            val course = etCourse.text.toString()
            val regNumber = etRegNumber.text.toString()

            if(name.isEmpty() || course.isEmpty() || regNumber.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                val student = Student(name = name, course = course, regNumber = regNumber)
                lifecycleScope.launch {
                    db.studentDao().insert(student)
                    students.add(student)
                    adapter.notifyItemInserted(students.size - 1)
                }

                etName.text.clear()
                etCourse.text.clear()
                etRegNumber.text.clear()
                Toast.makeText(this, "Student Added", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
