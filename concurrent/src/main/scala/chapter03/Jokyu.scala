package com.github.trackiss
package chapter03

import java.text.SimpleDateFormat
import java.util.Date

object Jokyu {
  def format(date: Date): String = {
    val formatter = new SimpleDateFormat("yyyy'年'MM'月'dd'日'E'曜日'H'時'mm'分'")
    formatter.format(date)
  }
}
