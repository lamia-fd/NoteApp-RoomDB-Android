package com.example.note_app
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    //  val userNote = ArrayList<String>()
    lateinit var ev1: TextView
    //lateinit var dbh :Helper
    lateinit var button: Button
     lateinit var noteList: List<NotesData>
    lateinit var myRv:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ev1 = findViewById(R.id.tv1)
        val layout = findViewById<ConstraintLayout>(R.id.layout)



         noteList= arrayListOf()
        NoteDataBase.getInstance(applicationContext)
         myRv = findViewById(R.id.rvMain)
        layout.setBackgroundResource(R.drawable.background)
        button = findViewById(R.id.button)

        //get all the note from the DB
        getAllNotes()


        myRv.adapter = recycler(this,noteList)
        myRv.layoutManager = LinearLayoutManager(this)

        button.setOnClickListener {
            if(ev1.text.isNotBlank()){
            addNote()
            getAllNotes()
            }else{
                Toast.makeText(applicationContext, "write a note", Toast.LENGTH_LONG).show()

            }

        }

    }

    fun getAllNotes(){
        CoroutineScope(IO).launch {
            noteList = NoteDataBase.getInstance(applicationContext).NoteDao().getAllNote()
        }
        Toast.makeText(applicationContext, "data is uploaded", Toast.LENGTH_LONG).show()

        /////update the notes to the recycler view
        myRv.adapter = recycler(this,noteList)
        myRv.layoutManager = LinearLayoutManager(this)
        myRv.adapter?.notifyDataSetChanged()
        myRv.scrollToPosition(noteList.size-1)


    }
    fun addNote(){
        var note=ev1.text.toString()
        var n=NotesData(0,note)
        CoroutineScope(IO).launch {
            NoteDataBase.getInstance(applicationContext).NoteDao().addNote(n)

        }

        Toast.makeText(applicationContext, "data is served", Toast.LENGTH_LONG).show()
        /////update the notes to the recycler view
        myRv.adapter = recycler(this,noteList)
        myRv.layoutManager = LinearLayoutManager(this)
        myRv.scrollToPosition(noteList.size-1)
/////////////////////



        myRv.adapter?.notifyDataSetChanged()
    }

    fun openDialog(id:Int){

        val dialogBuilder = AlertDialog.Builder(this)
        val updatedNote = EditText(this)
        dialogBuilder.setCancelable(false)
            .setPositiveButton("Save", DialogInterface.OnClickListener {
                    _, _ ->  NoteDataBase.getInstance(applicationContext).NoteDao().updateNote(id, updatedNote.text.toString())

                myRv.adapter = recycler(this,noteList)
                myRv.layoutManager = LinearLayoutManager(this)
                myRv.adapter?.notifyItemChanged(id)

            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(updatedNote)
        alert.show()

    }

    fun deleteNotes(id: Int){
        NoteDataBase.getInstance(applicationContext).NoteDao().deleteNote(id)


        myRv.adapter = recycler(this,noteList)
        myRv.layoutManager = LinearLayoutManager(this)
        myRv.adapter?.notifyItemRemoved(id)

    }




}