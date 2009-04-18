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

package com.googlecode.furnace.spec

import java.io.File
import java.net.URL
import util.io.FilePath
import util.io.FilePath._
import scalaz.OptionW
import scalaz.OptionW._

object SpecificationHelper {
   def dataFile(path: String): FilePath = dataFileFromAntBuild(canonicalise(path))

  def dataFileFromAntBuild(path: String) = getResource(canonicalise(path)) | dataFileFromIntelliJ(path)

  def dataFileFromIntelliJ(path: String) = {
    val intellijPath = canonicalise("furnace" + canonicalise(path))
    getResource(intellijPath).err("No resource found at '" + path + "' or '" + intellijPath + "'")
  }

  def getResource(path: String) = SpecificationHelper.getClass.getResource(path)

  def canonicalise(path: String) = if (path.startsWith("/")) path else "/" + path

  private implicit def toFilePath(url: URL): FilePath = new File(url.toURI)

  private implicit def toOptionW[A](a: A): OptionW[A] = (a: Option[A])
}