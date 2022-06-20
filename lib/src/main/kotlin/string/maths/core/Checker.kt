package string.maths.core

import string.maths.exception.IllegalFormatException

object Checker {

    fun String.checkFormat() {
        if ( !this.matches( "\\+?-?\\.?\\d+.".toRegex() ) && !this.matches( "\\+?-?\\.?\\d+\\.?\\d+".toRegex() ) ) throw IllegalFormatException( "Basic Format Error : $this" )
        if ( this.matches( "-?\\+.+".toRegex() ) && this.matches( "\\+?-.+".toRegex() ) ) throw IllegalFormatException( "$this : A number can only contain one sign either positive or negative" )
        if (
            this.let {
                var count = 0
                for ( i in this ) if ( i == '.' ) count++
                count
            } > 1
        ) throw IllegalFormatException( "$this : A number cannot contain more than one dots" )
    }

    fun String.fixDotsPosition() : String {
        var processedString = this
        if ( this.matches( ".+\\.".toRegex() ) ) processedString = processedString.substring( 0 , processedString.length-1 )
        if ( this.matches( "\\+?-?\\..+".toRegex() ) ) processedString = "${processedString.substring( 0 , processedString.indexOf( "." ) )}0${processedString.substring( processedString.indexOf(".") )}"
        return processedString
    }

}