package com.github.trackiss
package chapter01

object Chukyu {
  final case class Switch(isOn: Boolean)

  def toggle(switch: Switch): Switch = Switch(!switch.isOn)
}
