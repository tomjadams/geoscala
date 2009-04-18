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

package com.googlecode.furnace.util.io

import java.io.{ByteArrayOutputStream, File, FileInputStream}

object ReadableFile {
  implicit def fileToReadableFile(file: File): ReadableFile = ReadableFile(file)

  implicit def readableFileToFile(file: ReadableFile) = file match {
    case ReadableFile(f) => f
  }
}

final case class ReadableFile(file: File) {

  // Taken from: http://blog.lostlake.org/index.php?/archives/61-A-spell-checker-in-Scala.html
  def slurp: String = {
    val bos = new ByteArrayOutputStream
    val buffer = new Array[Byte](2048)
    val stream = new FileInputStream(file)

    def read {
      stream.read(buffer) match {
        case bytesRead if bytesRead < 0 =>
        case 0 => read
        case bytesRead => bos.write(buffer, 0, bytesRead); read
      }
    }

    read
    bos.toString("UTF-8")
  }
}
