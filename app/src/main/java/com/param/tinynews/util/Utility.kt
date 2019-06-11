package com.param.tinynews.util

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import java.util.*

class Utility(var first:Int, var second:String){

    fun printValues(){
        println("first= $first and second = $second");
    }

    fun  main(args: Array<String>){
       var utility : Utility = Utility(1, "one")
        utility.printValues()

    }
}



