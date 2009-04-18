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

package com.googlecode.furnace.analyse

import util.io.FilePath

trait AnalysisResult {
  def identifier: SequenceIdentifier
  def analysisOutput: FilePath
  def outputFormat: OutputFormat
}

sealed trait OutputFormat {
  def isHtml: Boolean
  def fileExtension: String
}

object OutputFormat {
  def text: OutputFormat = Text
  def html: OutputFormat = Html
}

private final case object Text extends OutputFormat {
  def isHtml = false
  def fileExtension = ".txt"
}

private final case object Html extends OutputFormat {
  def isHtml = true
  def fileExtension = ".html"
}
