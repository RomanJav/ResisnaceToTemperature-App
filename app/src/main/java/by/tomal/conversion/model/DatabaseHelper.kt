package by.tomal.conversion.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.*

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        val DB_VERSION = 1
        val DB_NAME = "resistanceTemperatureDB.db"
    }
    val myDatabase: SQLiteDatabase
    val myContext: Context = context
    var needUpdate = false

    init {
        myDatabase = open()
    }

    override fun onCreate(db: SQLiteDatabase?) {
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (newVersion > oldVersion)
            needUpdate = true
    }
    private fun open(): SQLiteDatabase {
        val dbFile = myContext.getDatabasePath(DB_NAME)
        if (!dbFile.exists()){
            try {
                val checkDB = myContext.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE,null)
                checkDB.close()
                copyDatabase(dbFile)
            }catch (e:IOException){
                throw RuntimeException("Error opening db")
            }
        }
        return SQLiteDatabase.openDatabase(dbFile.path, null, SQLiteDatabase.OPEN_READWRITE)
    }

    private fun copyDatabase(dbFile: File) {
        val iss = myContext.assets.open(DB_NAME)
        val os = FileOutputStream(dbFile)

        val buffer = ByteArray(1024)
        while (iss.read(buffer) > 0) {
            os.write(buffer)
        }
        os.flush()
        os.close()
        iss.close()
    }

    override fun close() {
        myDatabase.close()
    }

}