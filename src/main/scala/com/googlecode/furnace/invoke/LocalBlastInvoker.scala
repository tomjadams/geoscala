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

package com.googlecode.furnace.invoke

import analyse.SequenceIdentifier
import analyse.blast.BlastConfiguration._
import analyse.blast.CommandLineBlast._
import sequence.GeneSequence
import sequence.SequenceFile._
import util.io.FilePath

final class LocalBlastInvoker extends Invoker {
  def invoke(identifier: SequenceIdentifier, database: FilePath, sequence: GeneSequence) = {
    val input = sequence.write(identifier)
    val config = blastConfig(database, input)
    !blast(identifier, config)
  }
}
