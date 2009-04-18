/*
 * Copyright 2006-2008 Workingmouse
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

sealed trait FilePath

private final case class FilePath_(path: String) extends FilePath {
  override def toString = path
}

// TODO Handle null for passed params correctly.
object FilePath {
  import java.io.File

  implicit def filepath(filePath: String): FilePath = FilePath_(filePath)

  implicit def filepath(file: File): FilePath = file.getCanonicalPath

  implicit def filePathToString(filePath: FilePath): String = filePath match {
    case FilePath_(p) => p
    case null => null
  }

  implicit def stringToFile(filePath: String): File = filePath match {
    case null => null
    case f => filePathToFile(filepath(f))
  }

  implicit def filePathToFile(filePath: FilePath): File = filePath match {
    case FilePath_(filePath) => new File(filePath)
    case null => null
  }

  implicit def filePathToScalazFile(filePath: FilePath): scalaz.io.File = scalaz.io.File.file(filePath)
}
