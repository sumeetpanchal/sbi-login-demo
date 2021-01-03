package sam.programming.sbilogin.utility

import android.view.KeyEvent
import sam.programming.sbilogin.model.KeyData

val keyCodePassword = 1234

fun getKeyBoardKeys(): List<KeyData> {

    val keyObjects = mutableListOf<KeyData>(
        KeyData(KeyEvent.KEYCODE_0, 0),
        KeyData(KeyEvent.KEYCODE_1, 1),
        KeyData(KeyEvent.KEYCODE_2, 2),
        KeyData(KeyEvent.KEYCODE_3, 3),
        KeyData(KeyEvent.KEYCODE_4, 4),
        KeyData(KeyEvent.KEYCODE_5, 5),
        KeyData(KeyEvent.KEYCODE_6, 6),
        KeyData(KeyEvent.KEYCODE_7, 7),
        KeyData(KeyEvent.KEYCODE_8, 8),
        KeyData(KeyEvent.KEYCODE_9, 9),
    )
    keyObjects.shuffle()

    keyObjects.add(3, KeyData(KeyEvent.KEYCODE_DEL, 67))
    keyObjects.add(11, KeyData(keyCodePassword, 1234))

    return keyObjects
}
