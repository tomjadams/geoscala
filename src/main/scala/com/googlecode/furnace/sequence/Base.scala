/*
 * Copyright 2006-2008 Tom Adams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.furnace.sequence

sealed trait Base {
  def code: Char

  override def toString = code.toString
}

private case object A extends Base {
  override def code = 'A'
}

private case object C extends Base {
  override def code = 'C'
}

private case object G extends Base {
  override def code = 'G'
}

private case object T extends Base {
  override def code = 'T'
}

object Base {
  import Character._

  def bases = List(A, C, G, T)

  implicit def baseToChar(b: Base) = b.code

  implicit def charToBase(c: Char): Base = c.toUpperCase match {
    case 'A' => A
    case 'C' => C
    case 'G' => G
    case 'T' => T
    case code => error("Unknown base: '" + code + "'")
  }

  implicit def byteToBase(b: Byte): Base = b.toChar
}
