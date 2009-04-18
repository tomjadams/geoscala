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

package com.googlecode.furnace.util.process

import CommandLineProcess._
import Process._
import instinct.expect.Expect._
import instinct.marker.annotate.Specification
import util.io.FilePath._

final class ACommandLineProcess {
  @Specification
  def canBuildCommandLinesWithoutArguments {
    val p = command("/opt/blast/bin/blastall")
    expect.that(p.commandLine).isEqualTo("/opt/blast/bin/blastall")
  }

  @Specification
  def canBuildCommandLinesWithArguments {
    val p = command("/opt/blast/bin/blastall").arg("-f").arg("-o").arg("-bar", "baz")
    expect.that(p.commandLine).isEqualTo("/opt/blast/bin/blastall -f -o -bar baz")
  }

  @Specification
  def canBuildCommandLinesWithArgumentsUsingApply {
    val p = command("/opt/blast/bin/blastall")("-f")("-o")("-bar", "baz")
    expect.that(p.commandLine).isEqualTo("/opt/blast/bin/blastall -f -o -bar baz")
  }

  @Specification
  def runsSimpleCommands {
    val ls = command("/bin/ls").arg("-l").arg("-a").arg("/")
    val output = ls.execute.output
    expect.that(output).containsString("tmp")
    expect.that(output).containsString("usr")
  }

  @Specification
  def runsSimpleCommandsInAWorkingDirectory {
    val ls = command("/bin/ls").arg("-l")
    val output = ls.executeInDir("/").output
    expect.that(output).containsString("tmp")
    expect.that(output).containsString("usr")
  }
}
