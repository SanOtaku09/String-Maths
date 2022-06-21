package string.maths.exception
import string.maths.metaData.DataType

internal class IllegalFormatException( receivedData : Any ) : Exception (
    """|
        |Expected : ${DataType.SupportedDataType.string}
        |Received : $receivedData
    |""".trimMargin()
)