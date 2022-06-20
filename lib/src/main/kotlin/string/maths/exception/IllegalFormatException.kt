package string.maths.exception
import string.maths.metaData.DataType

class IllegalFormatException( receivedData : Any ) : Exception (
    """|
        |Expected : ${DataType.SupportedDataType.string}
        |Received : $receivedData
    |""".trimMargin()
)