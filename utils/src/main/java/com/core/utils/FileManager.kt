package com.core.utils

import android.content.Context
import timber.log.Timber
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileWriter
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject

class FileManager
    @Inject
    constructor(var context: Context) {
        fun writeFile(
            text: String,
            fileName: String,
        ) {
            val bufferedWriter =
                BufferedWriter(FileWriter(File(context.filesDir.toString() + File.separator.toString() + fileName)))
            bufferedWriter.write(text)
            bufferedWriter.close()
        }

        fun readFile(strFileName: String): String? {
            var line: String? = null
            try {
                val fileInputStream =
                    FileInputStream(
                        File(context.filesDir.toString() + File.separator.toString() + strFileName),
                    ) // set file path & name to read
                val inputStreamReader =
                    InputStreamReader(fileInputStream) // create input steam reader
                val bufferedReader = BufferedReader(inputStreamReader)
                val stringBuilder = StringBuilder()
                while (bufferedReader.readLine().also { line = it } != null) { // read line by line
                    stringBuilder.append(line + System.getProperty("line.separator")) // append the red text line by line
                }
                fileInputStream.close()
                line = stringBuilder.toString() // finally the whole date into an single string
                bufferedReader.close()
                Timber.e(line)
            } catch (ex: FileNotFoundException) {
                Timber.e(ex)
            } catch (ex: IOException) {
                Timber.e(ex)
            }
            return line
        }
    }
